import Foundation
import Moya

struct MatchRepository: MatchRepositoryProtocol {
    private let provider: MoyaProvider<MatchesTarget>
    private let mapper: any MatchMappable

    init(
        provider: MoyaProvider<MatchesTarget> = NetworkProvider.make(),
        mapper: any MatchMappable = MatchMapper()
    ) {
        self.provider = provider
        self.mapper = mapper
    }

    func fetchMatch(id: Int) async throws -> Match {
        let response = try await provider.asyncRequest(.detail(id: id))
        let validated = try response.filterSuccessfulStatusCodes()
        let dto = try JSONDecoder.apiDecoder.decode(MatchDTO.self, from: validated.data)
        return mapper.toDomain(dto)
    }

    func fetchMatches(page: Int, perPage: Int) async throws -> PaginatedMatches {
        let response = try await provider.asyncRequest(.list(page: page, perPage: perPage))
        let validated = try response.filterSuccessfulStatusCodes()
        let dtos = try JSONDecoder.apiDecoder.decode([MatchDTO].self, from: validated.data)

        let link = validated.response?.value(forHTTPHeaderField: "Link") ?? ""
        let hasNextPage = link.contains("rel=\"next\"")

        return PaginatedMatches(matches: dtos.map(mapper.toDomain), hasNextPage: hasNextPage)
    }
}
