import SwiftUI

struct MatchesEmptyView: View {
    var body: some View {
        Text("No matches found")
            .foregroundStyle(.white)
            .frame(maxWidth: .infinity)
    }
}

#Preview {
    MatchesEmptyView()
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.appBackground)
}
