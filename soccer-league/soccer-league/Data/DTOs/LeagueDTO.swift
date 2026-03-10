import Foundation

struct LeagueDTO: Codable {
    let id: Int
    let name: String
    let imageUrl: String?  // decoded from "image_url"
}
