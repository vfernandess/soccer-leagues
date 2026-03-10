import SwiftUI
import ComposableArchitecture

struct AppView: View {
    let store: StoreOf<AppFeature>

    var body: some View {
        if store.showSplash {
            SplashView { store.send(.splashFinished) }
        } else {
            MatchesView(store: store.scope(state: \.matches, action: \.matches))
        }
    }
}
