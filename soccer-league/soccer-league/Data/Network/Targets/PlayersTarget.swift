import Foundation
import Moya

enum PlayersTarget: TargetType {
    case players(teamId: Int)

    var baseURL: URL { URL(string: "https://api.pandascore.co")! }

    var path: String { "/csgo/players" }

    var method: Moya.Method { .get }

    var task: Task {
        switch self {
        case let .players(teamId):
            return .requestParameters(
                parameters: ["filter[team_id]": teamId],
                encoding: URLEncoding.default
            )
        }
    }

    var headers: [String: String]? {
        [
            "Content-Type": "application/json",
            "Authorization": "Bearer \(String(cString: get_api_token()))"
        ]
    }

    var sampleData: Data { Data() }
}
