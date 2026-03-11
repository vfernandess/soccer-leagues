struct TeamDetailDTO: Codable {
    let id: Int
    let name: String
    let players: [PlayerDTO]?
}
