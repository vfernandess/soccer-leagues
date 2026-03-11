import SwiftUI
import Kingfisher

struct MatchCardView: View {
    let match: Match

    var body: some View {
        ZStack(alignment: .topTrailing) {
            VStack(spacing: 0) {
                // Teams container
                HStack(spacing: DS.Size.s5) {
                    TeamView(team: match.team1)
                    Text("vs")
                        .textStyle(.vs)
                    TeamView(team: match.team2)
                }
                .frame(maxWidth: .infinity)
                .padding(DS.Size.s3)

                // Separator line
                Rectangle()
                    .fill(Color.white.opacity(0.05))
                    .frame(height: 1)
                    .frame(maxWidth: .infinity)

                // League footer
                HStack(spacing: DS.Size.s2) {
                    if let logoURL = match.league.imageURL {
                        KFImage(logoURL)
                            .resizable()
                            .scaledToFit()
                            .frame(width: DS.Icon.i4, height: DS.Icon.i4)
                    }
                    Text("\(match.league.name) \u{2022} \(match.serieName)")
                        .textStyle(.league)
                        .lineLimit(1)
                    Spacer()
                }
                .padding(.leading, DS.Size.s4)
                .padding(.trailing, DS.Size.s2)
                .padding(.vertical, DS.Size.s2)
            }
            .frame(maxWidth: .infinity)
            .background(Color.cardBackground)
            .clipShape(AppShape.card)

            // Status badge — top-right, rounded bottom-left + top-right (card corner)
            MatchStatusBadge(status: match.status, beginAt: match.beginAt)
        }
    }
}

#Preview("In Progress") {
    MatchCardView(match: .previewLive)
        .padding()
        .background(Color.appBackground)
}

#Preview("Scheduled") {
    MatchCardView(match: .previewScheduled)
        .padding()
        .background(Color.appBackground)
}

#Preview("Ended") {
    MatchCardView(match: .previewEnded)
        .padding()
        .background(Color.appBackground)
}

#Preview("No Teams") {
    MatchCardView(match: .previewNoTeams)
        .padding()
        .background(Color.appBackground)
}
