import SwiftUI

struct MatchesLoadingView: View {
    var body: some View {
        ProgressView()
            .tint(.white)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
