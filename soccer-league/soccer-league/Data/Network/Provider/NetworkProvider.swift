import Foundation
import Moya

/// Shared MoyaProvider factory with JSON decoder.
enum NetworkProvider {
    static func make<T: TargetType>() -> MoyaProvider<T> {
        #if DEBUG
        MoyaProvider<T>(plugins: [NetworkLoggerPlugin(configuration: .init(logOptions: .verbose))])
        #else
        MoyaProvider<T>()
        #endif
    }
}

extension JSONDecoder {
    /// Decoder configured for PandaScore API (snake_case → camelCase, ISO8601 dates).
    static let apiDecoder: JSONDecoder = {
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        return decoder
    }()
}

/// Async bridge for MoyaProvider callback-based request.
extension MoyaProvider {
    func asyncRequest(_ target: Target) async throws -> Response {
        try await withCheckedThrowingContinuation { continuation in
            request(target) { result in
                switch result {
                case .success(let response):
                    continuation.resume(returning: response)
                case .failure(let error):
                    continuation.resume(throwing: error)
                }
            }
        }
    }
}
