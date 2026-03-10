/// Fetches the players for a team. Used by MatchDetailFeature.
struct FetchMatchDetailInteractor {
    var execute: @Sendable (Int) async throws -> [Player]
}
