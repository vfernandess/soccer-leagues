import Foundation

struct TeamDTO: Codable {
    let id: Int
    let name: String
    let imageUrl: String?  // decoded from "image_url"
}
