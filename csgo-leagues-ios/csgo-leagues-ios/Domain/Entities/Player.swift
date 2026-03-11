import Foundation

struct Player: Equatable, Hashable, Identifiable, Sendable {
    let id: Int
    let nickname: String
    let firstName: String?
    let lastName: String?
    let imageURL: URL?

    /// Returns "FirstName LastName", or nil if both are absent.
    var fullName: String? {
        let parts = [firstName, lastName].compactMap { $0 }.filter { !$0.isEmpty }
        return parts.isEmpty ? nil : parts.joined(separator: " ")
    }
}
