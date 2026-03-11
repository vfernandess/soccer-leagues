import Foundation
import ComposableArchitecture

// MARK: - MatchRepository

private enum MatchRepositoryKey: DependencyKey {
    static let liveValue: any MatchRepositoryProtocol = MatchRepository()
    static let testValue: any MatchRepositoryProtocol = EmptyMatchRepository()
}

extension DependencyValues {
    var matchRepository: any MatchRepositoryProtocol {
        get { self[MatchRepositoryKey.self] }
        set { self[MatchRepositoryKey.self] = newValue }
    }
}

private struct EmptyMatchRepository: MatchRepositoryProtocol {
    func fetchMatches(page: Int, perPage: Int) async throws -> PaginatedMatches {
        PaginatedMatches(matches: [], hasNextPage: false)
    }

    func fetchMatch(id: Int) async throws -> Match {
        throw URLError(.notConnectedToInternet)
    }
}

// MARK: - PlayerRepository

private enum PlayerRepositoryKey: DependencyKey {
    static let liveValue: any PlayerRepositoryProtocol = PlayerRepository()
    static let testValue: any PlayerRepositoryProtocol = EmptyPlayerRepository()
}

extension DependencyValues {
    var playerRepository: any PlayerRepositoryProtocol {
        get { self[PlayerRepositoryKey.self] }
        set { self[PlayerRepositoryKey.self] = newValue }
    }
}

private struct EmptyPlayerRepository: PlayerRepositoryProtocol {
    func fetchPlayers(teamId: Int) async throws -> [Player] { [] }
}

// MARK: - FetchMatchesInteractor

extension FetchMatchesInteractor: DependencyKey {
    static var liveValue: FetchMatchesInteractor {
        @Dependency(\.matchRepository) var repository
        return FetchMatchesInteractor { page, perPage in
            let result = try await repository.fetchMatches(page: page, perPage: perPage)
            return PaginatedMatches(matches: Self.deduplicated(result.matches), hasNextPage: result.hasNextPage)
        }
    }

    static let testValue = FetchMatchesInteractor(
        execute: { _, _ in PaginatedMatches(matches: [], hasNextPage: false) }
    )
}

extension DependencyValues {
    var fetchMatchesInteractor: FetchMatchesInteractor {
        get { self[FetchMatchesInteractor.self] }
        set { self[FetchMatchesInteractor.self] = newValue }
    }
}

// MARK: - FetchMatchDetailInteractor

extension FetchMatchDetailInteractor: DependencyKey {
    static var liveValue: FetchMatchDetailInteractor {
        @Dependency(\.playerRepository) var repository
        return FetchMatchDetailInteractor { teamId in
            try await repository.fetchPlayers(teamId: teamId)
        }
    }

    static let testValue = FetchMatchDetailInteractor(
        execute: { _ in [] }
    )
}

extension DependencyValues {
    var fetchMatchDetailInteractor: FetchMatchDetailInteractor {
        get { self[FetchMatchDetailInteractor.self] }
        set { self[FetchMatchDetailInteractor.self] = newValue }
    }
}
