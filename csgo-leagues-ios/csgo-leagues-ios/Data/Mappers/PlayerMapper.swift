import Foundation

protocol PlayerMappable {
    func toDomain(_ dto: PlayerDTO) -> Player
}

struct PlayerMapper {
    static func toDomain(_ dto: PlayerDTO) -> Player {
        Player(
            id: dto.id,
            nickname: dto.name ?? "Unknown",
            firstName: dto.firstName,
            lastName: dto.lastName,
            imageURL: dto.imageUrl.flatMap { URL(string: $0)?.pandaScoreThumbnail }
        )
    }
}

extension PlayerMapper: PlayerMappable {
    func toDomain(_ dto: PlayerDTO) -> Player { PlayerMapper.toDomain(dto) }
}
