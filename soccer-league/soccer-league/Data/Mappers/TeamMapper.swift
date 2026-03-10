import Foundation

struct TeamMapper {
    static func toDomain(_ dto: TeamDTO) -> Team {
        Team(
            id: dto.id,
            name: dto.name,
            imageURL: dto.imageUrl.flatMap { URL(string: $0)?.pandaScoreThumbnail }
        )
    }
}
