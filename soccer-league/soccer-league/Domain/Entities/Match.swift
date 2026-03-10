import Foundation

struct Match: Equatable, Hashable, Identifiable, Sendable {
    let id: Int
    let name: String
    let status: MatchStatus
    let beginAt: Date?
    let teams: [Team]       // 0, 1, or 2 teams
    let league: League
    let serieName: String

    var team1: Team? { teams[safe: 0] }
    var team2: Team? { teams[safe: 1] }
}

// Safe subscript for arrays
extension Array {
    subscript(safe index: Int) -> Element? {
        indices.contains(index) ? self[index] : nil
    }
}
