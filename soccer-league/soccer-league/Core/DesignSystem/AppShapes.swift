import SwiftUI

/// Predefined shape presets used throughout the app.
/// All shapes reference DS.Radius tokens.
enum AppShape {
    /// Match card background — rounded all corners.
    static let card = RoundedRectangle(cornerRadius: DS.Radius.r4)

    /// Player photo clip shape — rounded all corners.
    static let playerPhoto = RoundedRectangle(cornerRadius: DS.Radius.r2)

    /// Team 1 player row — rounded right side only (top-right, bottom-right).
    static let playerRowLeft = UnevenRoundedRectangle(
        topLeadingRadius: 0,
        bottomLeadingRadius: 0,
        bottomTrailingRadius: DS.Radius.r3,
        topTrailingRadius: DS.Radius.r3
    )

    /// Team 2 player row — rounded left side only (top-left, bottom-left).
    static let playerRowRight = UnevenRoundedRectangle(
        topLeadingRadius: DS.Radius.r3,
        bottomLeadingRadius: DS.Radius.r3,
        bottomTrailingRadius: 0,
        topTrailingRadius: 0
    )

    /// Status badge — rounded bottom-left and top-right corners.
    /// Top-right matches the card's corner radius so it sits flush with the card edge.
    static let badge = UnevenRoundedRectangle(
        topLeadingRadius: 0,
        bottomLeadingRadius: DS.Radius.r4,
        bottomTrailingRadius: 0,
        topTrailingRadius: DS.Radius.r4
    )
}
