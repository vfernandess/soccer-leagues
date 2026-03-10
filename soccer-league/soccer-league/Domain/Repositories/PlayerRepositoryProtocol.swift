// No imports — pure Swift protocol
protocol PlayerRepositoryProtocol: Sendable {
    func fetchPlayers(teamId: Int) async throws -> [Player]
}
