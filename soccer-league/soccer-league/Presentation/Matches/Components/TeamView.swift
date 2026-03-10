import SwiftUI
import Kingfisher

struct TeamView: View {
    let team: Team?

    var body: some View {
        VStack(spacing: DS.Size.s2) {
            // Logo
            if let url = team?.imageURL {
                KFImage(url)
                    .placeholder { logoPlaceholder }
                    .resizable()
                    .scaledToFit()
                    .frame(width: DS.Icon.i15, height: DS.Icon.i15)
            } else {
                logoPlaceholder
            }

            // Team name
            Text(team?.name ?? "TBD")
                .textStyle(.teamName)
                .multilineTextAlignment(.center)
                .lineLimit(2)
                .frame(width: DS.Icon.i15)
        }
    }

    private var logoPlaceholder: some View {
        Circle()
            .fill(Color.placeholder)
            .frame(width: DS.Icon.i15, height: DS.Icon.i15)
    }
}

#Preview("With Team") {
    TeamView(team: .preview1)
        .padding()
        .background(Color.appBackground)
}

#Preview("TBD") {
    TeamView(team: nil)
        .padding()
        .background(Color.appBackground)
}
