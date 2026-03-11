import SwiftUI
import ComposableArchitecture

struct MatchDetailView: View {
    @Bindable var store: StoreOf<MatchDetailFeature>

    var body: some View {
        MatchDetailContentView(store: store)
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button {
                    store.send(.backTapped)
                } label: {
                    Image(systemName: "chevron.left")
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundStyle(.white)
                        .frame(width: DS.Icon.i6, height: DS.Icon.i6)
                }
            }
            ToolbarItem(placement: .principal) {
                Text("\(store.match.league.name) - \(store.match.serieName)")
                    .textStyle(.navTitle)
                    .lineLimit(1)
            }
        }
        .toolbarBackground(Color.appBackground, for: .navigationBar)
        .task { store.send(.initialize) }
    }
}

private struct MatchDetailContentView: View {
    @Bindable var store: StoreOf<MatchDetailFeature>

    var body: some View {
        ZStack {
            Color.appBackground.ignoresSafeArea()

            switch store.contentPhase {
            case .loading:
                ProgressView()
                    .tint(.white)
                    .frame(maxWidth: .infinity, maxHeight: .infinity)

            case .error(let message):
                MatchesErrorView(message: message, onRetry: { store.send(.retry) })
                    .frame(maxWidth: .infinity, maxHeight: .infinity)

            case .loaded:
                ScrollView {
                    VStack(spacing: DS.Size.s5) {
                        MatchTeamsSectionView(
                            team1: store.match.team1,
                            team2: store.match.team2
                        )
                        MatchTimeBadgeView(
                            status: store.match.status,
                            beginAt: store.match.beginAt
                        )
                        MatchPlayersSectionView(
                            team1Players: store.team1.players,
                            team2Players: store.team2.players
                        )
                    }
                    .padding(.top, DS.Size.s5)
                    .padding(.bottom, DS.Size.s6)
                }
            }
        }
    }
}

#Preview("Loading") {
    MatchDetailContentView(
        store: Store(initialState: {
            var s = MatchDetailFeature.State(match: .previewScheduled)
            s.isLoading = true
            return s
        }()) { MatchDetailFeature() }
    )
}

#Preview("Loaded") {
    MatchDetailContentView(
        store: Store(initialState: {
            var s = MatchDetailFeature.State(match: .previewScheduled)
            s.team1.players = Player.previewTeam1
            s.team2.players = Player.previewTeam2
            return s
        }()) { MatchDetailFeature() }
    )
}

#Preview("Error") {
    MatchDetailContentView(
        store: Store(initialState: {
            var s = MatchDetailFeature.State(match: .previewScheduled)
            s.error = "Something went wrong"
            return s
        }()) { MatchDetailFeature() }
    )
}
