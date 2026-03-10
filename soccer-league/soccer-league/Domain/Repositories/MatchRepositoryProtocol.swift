// No imports — pure Swift protocol
protocol MatchRepositoryProtocol: Sendable {
    func fetchMatches(page: Int, perPage: Int) async throws -> PaginatedMatches
    func fetchMatch(id: Int) async throws -> Match
}
