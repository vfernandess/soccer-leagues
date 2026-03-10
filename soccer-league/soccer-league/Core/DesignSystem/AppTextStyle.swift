import SwiftUI

/// Text style presets. Apply with `.textStyle(.screenTitle)`.
enum AppTextStyle {
    case screenTitle    // 32 medium white — "Partidas" heading
    case navTitle       // 18 medium white — navigation bar title
    case badge          // 8 bold white — "AGORA" / date badge text
    case matchTime      // 12 bold white — match time in detail
    case teamName       // 10 regular white — team name below logo
    case vs             // 12 regular white 50% — "vs" separator
    case league         // 8 regular white — league + serie footer text
    case playerNickname // 14 bold white — player in-game name
    case playerName     // 12 regular #6C6B7E — player real name

    var font: Font {
        switch self {
        case .screenTitle:    return .system(size: 32, weight: .medium)
        case .navTitle:       return .system(size: 18, weight: .medium)
        case .badge:          return .system(size: 8, weight: .bold)
        case .matchTime:      return .system(size: 12, weight: .bold)
        case .teamName:       return .system(size: 10)
        case .vs:             return .system(size: 12)
        case .league:         return .system(size: 8)
        case .playerNickname: return .system(size: 14, weight: .bold)
        case .playerName:     return .system(size: 12)
        }
    }

    var color: Color {
        switch self {
        case .vs:         return .textSecondary
        case .playerName: return .textSubtitle
        default:          return .white
        }
    }
}

/// Applies font + foreground color from an AppTextStyle in one modifier.
struct TextStyleModifier: ViewModifier {
    let style: AppTextStyle

    func body(content: Content) -> some View {
        content
            .font(style.font)
            .foregroundStyle(style.color)
    }
}

extension View {
    func textStyle(_ style: AppTextStyle) -> some View {
        modifier(TextStyleModifier(style: style))
    }
}
