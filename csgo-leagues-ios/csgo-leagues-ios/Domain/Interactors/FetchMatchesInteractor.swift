/// Fetches a page of matches. Used by MatchesFeature.
struct FetchMatchesInteractor {
    var execute: @Sendable (Int, Int) async throws -> PaginatedMatches
}

extension FetchMatchesInteractor {
    // MARK: - Business rules

    static func deduplicated(_ matches: [Match]) -> [Match] {
        var seen = Set<Int>()
        return matches.filter { seen.insert($0.id).inserted }
    }
}
