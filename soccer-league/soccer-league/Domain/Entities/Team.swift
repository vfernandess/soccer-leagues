import Foundation

struct Team: Equatable, Hashable, Sendable {
    let id: Int
    let name: String
    let imageURL: URL?
}
