import SwiftUI

struct MatchPlayersSectionView: View {
    let team1Players: [Player]
    let team2Players: [Player]

    var body: some View {
        HStack(alignment: .top, spacing: DS.Size.s3) {
            // Left column: Team 1 — text right-aligned, photo on right
            VStack(spacing: DS.Size.s3) {
                ForEach(team1Players) { player in
                    PlayerRowView(player: player, side: .left)
                }

                if team1Players.isEmpty {
                    Text("Desculpe não conseguimos achar os membros dos time.")
                        .frame(maxWidth: .infinity)
                        .padding(.horizontal, DS.Component.playerRowBgOffset)
                }
            }
            .frame(maxWidth: .infinity)

            // Right column: Team 2 — photo on left, text left-aligned
            VStack(spacing: DS.Size.s3) {
                ForEach(team2Players) { player in
                    PlayerRowView(player: player, side: .right)
                }

                if team2Players.isEmpty {
                    Text("Desculpe não conseguimos achar os membros dos time.")
                        .frame(maxWidth: .infinity)
                        .padding(.horizontal, DS.Component.playerRowBgOffset)
                }
            }
            .frame(maxWidth: .infinity)
        }
    }
}

#Preview("Loaded") {
    MatchPlayersSectionView(
        team1Players: Player.previewTeam1,
        team2Players: Player.previewTeam2
    )
    .background(Color.appBackground)
}
