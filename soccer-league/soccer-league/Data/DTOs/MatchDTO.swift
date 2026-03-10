struct MatchDTO: Codable {
    let id: Int
    let name: String
    let status: String
    let beginAt: String?      // decoded from "begin_at", ISO8601
    let opponents: [OpponentContainerDTO]
    let league: LeagueDTO
    let serie: SerieDTO
}
