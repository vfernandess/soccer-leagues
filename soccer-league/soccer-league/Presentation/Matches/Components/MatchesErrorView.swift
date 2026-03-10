import SwiftUI

struct MatchesErrorView: View {
    let message: String
    let onRetry: () -> Void

    var body: some View {
        VStack(spacing: DS.Size.s4) {
            Text(message)
                .foregroundStyle(.white)
                .multilineTextAlignment(.center)
            Button {
                onRetry()
            } label: {
                Text("Try Again")
                    .fontWeight(.bold)
                    .foregroundStyle(.white)
                    .padding(.horizontal, DS.Size.s4)
                    .padding(.vertical, DS.Size.s2)
                    .overlay(
                        RoundedRectangle(cornerRadius: DS.Radius.r2)
                            .stroke(Color.white, lineWidth: 1)
                    )
            }
        }
        .padding(.horizontal, DS.Size.s6)
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
