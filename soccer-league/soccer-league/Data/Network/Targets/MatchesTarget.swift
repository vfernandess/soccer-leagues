import Foundation
import Moya

enum MatchesTarget: TargetType {
    case list(page: Int, perPage: Int)
    case detail(id: Int)

    var baseURL: URL { URL(string: "https://api.pandascore.co")! }

    var path: String {
        switch self {
        case .list: return "/csgo/matches"
        case .detail(let id): return "/csgo/matches/\(id)"
        }
    }

    var method: Moya.Method { .get }

    var task: Task {
        switch self {
        case let .list(page, perPage):
            return .requestParameters(
                parameters: [
                    "page[number]": page,
                    "page[size]": perPage,
                    "sort": "-status,begin_at",
                    "filter[status]": "finished,not_started,running",
                ],
                encoding: URLEncoding.queryString
            )
        case .detail:
            return .requestParameters(parameters: [:], encoding: URLEncoding.queryString)
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
