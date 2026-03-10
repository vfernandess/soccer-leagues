#if DEBUG
import Foundation

// MARK: - League

extension League {
    static let preview = League(id: 1, name: "ESL Pro League", imageURL: nil)
}

// MARK: - Team

extension Team {
    static let preview1 = Team(id: 1, name: "Natus Vincere", imageURL: nil)
    static let preview2 = Team(id: 2, name: "Astralis", imageURL: nil)
}

// MARK: - Player

extension Player {
    static let preview1 = Player(id: 1, nickname: "s1mple", firstName: "Oleksandr", lastName: "Kostyliev", imageURL: nil)
    static let preview2 = Player(id: 2, nickname: "device", firstName: "Nicolai", lastName: "Reedtz", imageURL: nil)
    static let preview3 = Player(id: 3, nickname: "ZywOo", firstName: "Mathieu", lastName: "Herbaut", imageURL: nil)
    static let preview4 = Player(id: 4, nickname: "NiKo", firstName: "Nikola", lastName: "Kovač", imageURL: nil)
    static let previewNoName = Player(id: 5, nickname: "Ghost", firstName: nil, lastName: nil, imageURL: nil)

    static let previewTeam1: [Player] = [.preview1, .preview3, .previewNoName]
    static let previewTeam2: [Player] = [.preview2, .preview4]
}

// MARK: - Match

extension Match {
    static let previewLive = Match(
        id: 1, name: "NaVi vs Astralis",
        status: .inProgress, beginAt: Date(),
        teams: [.preview1, .preview2],
        league: .preview, serieName: "Season 15"
    )

    static let previewScheduled = Match(
        id: 2, name: "NaVi vs Astralis",
        status: .scheduled, beginAt: Date().addingTimeInterval(7200),
        teams: [.preview1, .preview2],
        league: .preview, serieName: "Season 15"
    )

    static let previewEnded = Match(
        id: 3, name: "NaVi vs Astralis",
        status: .ended, beginAt: Date().addingTimeInterval(-86400),
        teams: [.preview1, .preview2],
        league: .preview, serieName: "Season 15"
    )

    static let previewNoTeams = Match(
        id: 4, name: "TBD",
        status: .scheduled, beginAt: nil,
        teams: [],
        league: .preview, serieName: "Season 15"
    )
}
#endif
