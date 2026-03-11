import SwiftUI

struct MatchesListView: View {
    let matches: [Match]
    let isLoadingMore: Bool
    let onTap: (Match) -> Void
    let onItemAppeared: (Int) -> Void
    let onRefresh: () async -> Void

    var body: some View {
        ScrollView {
            LazyVStack(spacing: DS.Size.s4) {
                ForEach(Array(matches.enumerated()), id: \.element.id) { index, match in
                    MatchCardView(match: match)
                        .onTapGesture { onTap(match) }
                        .onAppear { onItemAppeared(index) }
                }

                if isLoadingMore {
                    ProgressView()
                        .tint(.white)
                        .padding(DS.Size.s4)
                }
            }
            .padding(.horizontal, DS.Size.s6)
            .padding(.bottom, DS.Size.s6)
        }
        .refreshable {
            await onRefresh()
        }
    }
}
