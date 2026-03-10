import Foundation

extension URL {
    /// Returns a PandaScore CDN thumbnail URL (200×200) by inserting the
    /// `thumb_` prefix before the last path component (filename).
    /// Returns `self` unchanged if filename is empty or already prefixed.
    var pandaScoreThumbnail: URL {
        let filename = lastPathComponent
        guard !filename.isEmpty, !filename.hasPrefix("thumb_") else { return self }
        return deletingLastPathComponent().appendingPathComponent("thumb_\(filename)")
    }
}
