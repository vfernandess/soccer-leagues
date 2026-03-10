import Foundation

struct LeagueMapper {
    static func toDomain(_ dto: LeagueDTO) -> League {
        League(
            id: dto.id,
            name: dto.name,
            imageURL: dto.imageUrl.flatMap { URL(string: $0)?.pandaScoreThumbnail }
        )
    }
}
