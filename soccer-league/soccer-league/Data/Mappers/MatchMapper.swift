import Foundation

protocol MatchMappable {
    func toDomain(_ dto: MatchDTO) -> Match
}

struct MatchMapper {
    static func toDomain(_ dto: MatchDTO) -> Match {
        Match(
            id: dto.id,
            name: dto.name,
            status: mapStatus(dto.status),
            beginAt: parseDate(dto.beginAt),
            teams: dto.opponents.map { TeamMapper.toDomain($0.opponent) },
            league: LeagueMapper.toDomain(dto.league),
            serieName: dto.serie.fullName
        )
    }

    private static func mapStatus(_ raw: String) -> MatchStatus {
        switch raw {
        case "running":  return .inProgress
        case "finished": return .ended
        default:         return .scheduled
        }
    }

    private static func parseDate(_ raw: String?) -> Date? {
        guard let raw else { return nil }
        let formatter = ISO8601DateFormatter()
        formatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        if let date = formatter.date(from: raw) { return date }
        // Retry without fractional seconds
        formatter.formatOptions = [.withInternetDateTime]
        return formatter.date(from: raw)
    }
}

extension MatchMapper: MatchMappable {
    func toDomain(_ dto: MatchDTO) -> Match { MatchMapper.toDomain(dto) }
}
