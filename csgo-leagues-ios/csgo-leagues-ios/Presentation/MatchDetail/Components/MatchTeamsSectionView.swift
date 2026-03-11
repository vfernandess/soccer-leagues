import SwiftUI

struct MatchTeamsSectionView: View {
    let team1: Team?
    let team2: Team?

    var body: some View {
        HStack(spacing: DS.Size.s5) {
            TeamView(team: team1)
            Text("vs")
                .textStyle(.vs)
            TeamView(team: team2)
        }
        .frame(maxWidth: .infinity)
    }
}

#Preview("Both Teams") {
    MatchTeamsSectionView(team1: .preview1, team2: .preview2)
        .padding()
        .background(Color.appBackground)
}

#Preview("One Team") {
    MatchTeamsSectionView(team1: .preview1, team2: nil)
        .padding()
        .background(Color.appBackground)
}
