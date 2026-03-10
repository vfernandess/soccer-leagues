import Foundation
import Moya

struct PlayerRepository: PlayerRepositoryProtocol {
    private let provider: MoyaProvider<PlayersTarget>
    private let mapper: any PlayerMappable

    init(
        provider: MoyaProvider<PlayersTarget> = NetworkProvider.make(),
        mapper: any PlayerMappable = PlayerMapper()
    ) {
        self.provider = provider
        self.mapper = mapper
    }

    func fetchPlayers(teamId: Int) async throws -> [Player] {
        let response = try await provider.asyncRequest(.players(teamId: teamId))
        let validated = try response.filterSuccessfulStatusCodes()
        let dtos = try JSONDecoder.apiDecoder.decode([PlayerDTO].self, from: validated.data)
        return dtos.map(mapper.toDomain)
    }
}
