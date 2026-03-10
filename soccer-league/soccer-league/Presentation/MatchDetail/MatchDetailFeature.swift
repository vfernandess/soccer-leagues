import ComposableArchitecture

@Reducer
struct MatchDetailFeature {
    struct TeamPlayersState: Equatable {
        var players: [Player] = []

        var isEmpty: Bool { players.isEmpty }
    }

    @ObservableState
    struct State: Equatable {
        let match: Match
        var team1: TeamPlayersState = .init()
        var team2: TeamPlayersState = .init()
        var isLoading: Bool = false
        var error: String?

        enum ContentPhase: Equatable {
            case loading
            case error(String)
            case loaded
        }

        var contentPhase: ContentPhase {
            if isLoading { return .loading }
            if let error { return .error(error) }
            return .loaded
        }
    }

    enum Action {
        case backTapped
        case initialize
        case retry
        case playersLoaded(Result<(team1: [Player], team2: [Player]), any Error>)
    }

    @Dependency(\.fetchMatchDetailInteractor) var fetchDetail
    @Dependency(\.dismiss) var dismiss

    var body: some ReducerOf<Self> {
        Reduce { state, action in
            switch action {

            case .backTapped:
                return .run { [dismiss] _ in await dismiss() }

            case .initialize:
                guard state.team1.isEmpty,
                      state.team2.isEmpty,
                      state.error == nil,
                      !state.isLoading else { return .none }
                state.isLoading = true
                return loadPlayers(match: state.match)

            case .retry:
                state.error = nil
                state.isLoading = true
                return loadPlayers(match: state.match)

            case let .playersLoaded(.success(players)):
                state.isLoading = false
                state.team1.players = players.team1
                state.team2.players = players.team2
                return .none

            case .playersLoaded(.failure):
                state.isLoading = false
                state.error = "Something went wrong"
                return .none
            }
        }
    }

    // MARK: - Helpers

    private func loadPlayers(match: Match) -> Effect<Action> {
        .run { [fetchDetail] send in
            do {
                async let r1 = Self.fetchTeam(id: match.team1?.id, interactor: fetchDetail)
                async let r2 = Self.fetchTeam(id: match.team2?.id, interactor: fetchDetail)
                let (result1, result2) = await (r1, r2)
                await send(.playersLoaded(.success((
                    team1: try result1.get(),
                    team2: try result2.get()
                ))))
            } catch {
                await send(.playersLoaded(.failure(error)))
            }
        }
    }

    private static func fetchTeam(id: Int?, interactor: FetchMatchDetailInteractor) async -> Result<[Player], any Error> {
        guard let id else { return .success([]) }
        return await Result { try await interactor.execute(id) }
    }
}
