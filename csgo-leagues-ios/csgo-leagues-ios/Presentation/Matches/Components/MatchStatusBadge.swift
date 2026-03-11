import SwiftUI

struct MatchStatusBadge: View {
    let status: MatchStatus
    let beginAt: Date?

    private var badgeText: String {
        status.badgeLabel(beginAt: beginAt)
    }

    private var badgeColor: Color {
        status == .inProgress ? .liveBadge : .scheduledBadge
    }

    var body: some View {
        Text(badgeText)
            .textStyle(.badge)
            .padding(.horizontal, DS.Size.s2)
            .padding(.vertical, DS.Size.s2)
            .background(badgeColor)
            .clipShape(AppShape.badge)
    }
}

#Preview("In Progress") {
    MatchStatusBadge(status: .inProgress, beginAt: nil)
        .padding()
        .background(Color.appBackground)
}

#Preview("Scheduled") {
    MatchStatusBadge(status: .scheduled, beginAt: Date().addingTimeInterval(7200))
        .padding()
        .background(Color.appBackground)
}

#Preview("No Date") {
    MatchStatusBadge(status: .scheduled, beginAt: nil)
        .padding()
        .background(Color.appBackground)
}

