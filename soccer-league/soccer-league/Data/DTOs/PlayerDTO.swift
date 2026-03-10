import Foundation

struct PlayerDTO: Codable {
    let id: Int
    let name: String?          // nickname / in-game name ("name" in API)
    let firstName: String?     // decoded from "first_name"
    let lastName: String?      // decoded from "last_name"
    let imageUrl: String?      // decoded from "image_url"
}
