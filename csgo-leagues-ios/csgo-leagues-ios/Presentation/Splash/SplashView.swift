import SwiftUI

struct SplashView: View {
    let onFinished: () -> Void
    @State private var opacity: Double = 1.0

    var body: some View {
        ZStack {
            Color.appBackground
                .ignoresSafeArea()

            Image("fuze_logo")
                .resizable()
                .scaledToFit()
                .frame(
                    width: DS.Icon.i28,
                    height: DS.Icon.i28
                )
                .opacity(opacity)
        }
        .ignoresSafeArea()
        .onAppear {
            withAnimation(
                .easeOut(duration: 0.4)
                .delay(2.0),
                { opacity = 0 },
                completion: onFinished
            )
        }
    }
}

#Preview {
    SplashView(onFinished: {})
}
