import SwiftUI
import ComposableArchitecture

struct MatchesView: View {
    @Bindable var store: StoreOf<MatchesFeature>

    var body: some View {
        NavigationStack {
            MatchesContentView(store: store)
                .navigationBarHidden(true)
                .navigationDestination(
                    item: $store.scope(state: \.destination, action: \.destination)
                ) { detailStore in
                    MatchDetailView(store: detailStore)
                }
        }
        .task { store.send(.onAppear) }
    }
}

private struct MatchesContentView: View {
    @Bindable var store: StoreOf<MatchesFeature>

    var body: some View {
        ZStack {
            Color.appBackground.ignoresSafeArea()

            VStack(alignment: .leading, spacing: 0) {
                Text("Partidas")
                    .textStyle(.screenTitle)
                    .padding(.horizontal, DS.Size.s6)
                    .padding(.top, DS.Size.s6)
                    .padding(.bottom, DS.Size.s4)

                switch store.contentPhase {
                case .loading:
                    MatchesLoadingView()

                case .error(let message):
                    MatchesErrorView(
                        message: message,
                        onRetry: { store.send(.retry) }
                    )

                case .empty:
                    Spacer()
                    MatchesEmptyView()
                    Spacer()

                case .loaded:
                    MatchesListView(
                        matches: store.matches,
                        isLoadingMore: store.isLoadingMore,
                        onTap: { store.send(.matchTapped($0)) },
                        onItemAppeared: { store.send(.itemAppeared(index: $0)) },
                        onRefresh: { await store.send(.refresh).finish() }
                    )
                }
            }
        }
    }
}

#Preview("Loading") {
    MatchesContentView(
        store: Store(initialState: MatchesFeature.State(isLoading: true)) {
            MatchesFeature()
        }
    )
}

#Preview("Loaded") {
    MatchesContentView(
        store: Store(initialState: MatchesFeature.State(
            matches: [.previewLive, .previewScheduled, .previewEnded])) {
            MatchesFeature()
        }
    )
}

#Preview("Empty") {
    MatchesContentView(
        store: Store(initialState: MatchesFeature.State()) {
            MatchesFeature()
        }
    )
}

#Preview("Error") {
    MatchesContentView(
        store: Store(initialState: MatchesFeature.State(error: "Something went wrong")) {
            MatchesFeature()
        }
    )
}
