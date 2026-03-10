import SwiftUI

extension Color {
    /// App background color for all screens.
    static let appBackground = Color(hex: "#161621")

    /// Background color for match cards and player rows.
    static let cardBackground = Color(hex: "#272639")

    /// "AGORA" live match badge background.
    static let liveBadge = Color(hex: "#F42A35")

    /// Scheduled match badge background (white 20% opacity).
    static let scheduledBadge = Color.white.opacity(0.2)

    /// "vs" text and secondary text (white 50% opacity).
    static let textSecondary = Color.white.opacity(0.5)

    /// Player real name text color.
    static let textSubtitle = Color(hex: "#6C6B7E")

    /// Placeholder color for team logos and player photos.
    static let placeholder = Color(hex: "#C4C4C4")
}
