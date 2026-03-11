import Foundation

enum MatchStatus: String, Equatable, Hashable, Sendable {
    case inProgress
    case scheduled
    case ended

    func badgeLabel(beginAt: Date?) -> String {
        switch self {
        case .inProgress: return "AGORA"
        case .scheduled, .ended:
            guard let date = beginAt else { return "A definir" }
            return date.matchBadgeText()
        }
    }
}

