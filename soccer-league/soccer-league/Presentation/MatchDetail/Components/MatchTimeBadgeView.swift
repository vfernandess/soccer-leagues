import SwiftUI

struct MatchTimeBadgeView: View {
    let status: MatchStatus
    let beginAt: Date?

    var body: some View {
        Text(status.badgeLabel(beginAt: beginAt))
            .textStyle(.matchTime)
            .frame(maxWidth: .infinity)
    }
}

#Preview("In Progress") {
    MatchTimeBadgeView(status: .inProgress, beginAt: Date())
        .padding()
        .background(Color.appBackground)
}

#Preview("Scheduled") {
    MatchTimeBadgeView(status: .scheduled, beginAt: Date().addingTimeInterval(7200))
        .padding()
        .background(Color.appBackground)
}

#Preview("No Date") {
    MatchTimeBadgeView(status: .scheduled, beginAt: nil)
        .padding()
        .background(Color.appBackground)
}
