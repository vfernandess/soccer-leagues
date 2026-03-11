import ComposableArchitecture

@Reducer
struct MatchesFeature {
    private static let pageSize = 50
    private static let paginationThreshold = 15

    @ObservableState
    struct State: Equatable {

        var matches: [Match] = []
        var isLoading: Bool = true
        var isRefreshing: Bool = false
        var isLoadingMore: Bool = false
        var currentPage: Int = 1
        var hasMorePages: Bool = false
        var error: String?

        /// Navigation destination — selected match for detail view.
        @Presents var destination: MatchDetailFeature.State?

        enum ContentPhase: Equatable {
            case loading
            case error(String)
            case empty
            case loaded
        }

        var contentPhase: ContentPhase {
            if isLoading { return .loading }
            if let error { return .error(error) }
            if matches.isEmpty { return .empty }
            return .loaded
        }
    }

    enum Action {
        case onAppear
        case refresh
        case itemAppeared(index: Int)
        case loadNextPage
        case retry
        case matchTapped(Match)
        case matchesResponse(Result<PaginatedMatches, Error>)
        case paginationResponse(Result<PaginatedMatches, Error>)
        case destination(PresentationAction<MatchDetailFeature.Action>)
    }

    @Dependency(\.fetchMatchesInteractor) var fetchMatches

    var body: some ReducerOf<Self> {
        Reduce { state, action in
            switch action {

            // MARK: - Initial Load
            case .onAppear:
                state.isLoading = true
                state.error = nil
                return fetchPage(1)

            // MARK: - Retry after error
            case .retry:
                state.error = nil
                state.isLoading = true
                return fetchPage(1)

            // MARK: - Pull to Refresh
            case .refresh:
                guard !state.isRefreshing else { return .none }
                state.isRefreshing = true
                state.currentPage = 1
                state.hasMorePages = false
                return fetchPage(1)

            // MARK: - Pagination
            case let .itemAppeared(index):
                guard !state.matches.isEmpty,
                      index >= state.matches.count - Self.paginationThreshold else { return .none }
                return .send(.loadNextPage)

            case .loadNextPage:
                guard !state.isLoadingMore,
                      !state.isLoading,
                      state.hasMorePages else { return .none }
                state.isLoadingMore = true
                let nextPage = state.currentPage + 1
                return .run { [fetchMatches] send in
                    let result = await Result { try await fetchMatches.execute(nextPage, Self.pageSize) }
                    await send(.paginationResponse(result))
                }

            // MARK: - Responses
            case let .matchesResponse(.success(response)):
                state.isLoading = false
                state.isRefreshing = false
                state.currentPage = 1
                state.hasMorePages = response.hasNextPage
                state.matches = response.matches
                state.error = nil
                return .none

            case .matchesResponse(.failure):
                state.isLoading = false
                state.isRefreshing = false
                if state.matches.isEmpty {
                    state.error = "Something went wrong"
                }
                return .none

            case let .paginationResponse(.success(response)):
                state.isLoadingMore = false
                state.currentPage += 1
                state.hasMorePages = response.hasNextPage
                state.matches = FetchMatchesInteractor.deduplicated(state.matches + response.matches)
                return .none

            case .paginationResponse(.failure):
                state.isLoadingMore = false
                // hasMorePages remains true (computed), user can retry by scrolling
                return .none

            // MARK: - Navigation
            case let .matchTapped(match):
                state.destination = MatchDetailFeature.State(match: match)
                return .none

            case .destination:
                return .none
            }
        }
        .ifLet(\.$destination, action: \.destination) {
            MatchDetailFeature()
        }
    }

    // MARK: - Helpers

    private func fetchPage(_ page: Int) -> Effect<Action> {
        .run { [fetchMatches] send in
            let result = await Result { try await fetchMatches.execute(page, Self.pageSize) }
            await send(.matchesResponse(result))
        }
    }
}
