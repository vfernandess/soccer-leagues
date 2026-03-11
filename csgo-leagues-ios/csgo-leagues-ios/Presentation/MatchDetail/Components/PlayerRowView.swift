import SwiftUI
import Kingfisher

enum PlayerRowSide {
    case left   // Team 1: text right-aligned, photo on right
    case right  // Team 2: photo on left, text left-aligned
}

struct PlayerRowView: View {
    let player: Player
    let side: PlayerRowSide

    var body: some View {
        ZStack {
            // Background — offset down by s1 so photo overflows above
            (side == .left ? AppShape.playerRowLeft : AppShape.playerRowRight)
                .fill(Color.cardBackground)
                .frame(maxWidth: .infinity)
                .frame(height: DS.Component.playerRowHeight)
                .offset(y: DS.Component.playerRowBgOffset)

            // Content row
            HStack(spacing: DS.Size.s4) {
                if side == .left {
                    playerText(alignment: .trailing)
                    playerPhoto
                } else {
                    playerPhoto
                    playerText(alignment: .leading)
                }
            }
            .padding(.horizontal, DS.Size.s2)
        }
        .frame(height: DS.Component.playerRowHeight + DS.Component.playerRowBgOffset)
    }

    private func playerText(alignment: HorizontalAlignment) -> some View {
        VStack(alignment: alignment, spacing: DS.Size.s1) {
            Text(player.nickname)
                .textStyle(.playerNickname)
                .lineLimit(1)

            if let fullName = player.fullName {
                Text(fullName)
                    .textStyle(.playerName)
                    .lineLimit(1)
            }
        }
        .frame(width: DS.Component.playerTextWidth, alignment: alignment == .leading ? .leading : .trailing)
    }

    private var playerPhoto: some View {
        Group {
            if let url = player.imageURL {
                KFImage(url)
                    .placeholder { photoPlaceholder }
                    .resizable()
                    .scaledToFill()
                    .frame(width: DS.Icon.i12, height: DS.Icon.i12)
                    .clipShape(AppShape.playerPhoto)
            } else {
                photoPlaceholder
            }
        }
    }

    private var photoPlaceholder: some View {
        AppShape.playerPhoto
            .fill(Color.placeholder)
            .frame(width: DS.Icon.i12, height: DS.Icon.i12)
    }
}

#Preview("Left — with name") {
    PlayerRowView(player: .preview1, side: .left)
        .background(Color.appBackground)
}

#Preview("Right — with name") {
    PlayerRowView(player: .preview2, side: .right)
        .background(Color.appBackground)
}

#Preview("Left — no name") {
    PlayerRowView(player: .previewNoName, side: .left)
        .background(Color.appBackground)
}
