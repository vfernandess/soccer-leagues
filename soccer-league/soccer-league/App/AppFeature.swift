import ComposableArchitecture

@Reducer
struct AppFeature {
    @ObservableState
    struct State: Equatable {
        var showSplash: Bool = true
        var matches: MatchesFeature.State = .init()
    }

    enum Action {
        case splashFinished
        case matches(MatchesFeature.Action)
    }

    var body: some ReducerOf<Self> {
        Scope(state: \.matches, action: \.matches) {
            MatchesFeature()
        }
        Reduce { state, action in
            switch action {
            case .splashFinished:
                state.showSplash = false
                return .none
            case .matches:
                return .none
            }
        }
    }
}
