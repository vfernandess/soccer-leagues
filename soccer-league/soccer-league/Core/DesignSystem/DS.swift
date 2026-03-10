import CoreFoundation

/// Central design token namespace.
/// All spacing, radius, icon sizes, and component dimensions live here.
/// Views must never use hardcoded values — always reference DS tokens.
enum DS {

    // MARK: - Spacing (4pt base grid)
    enum Size {
        static let s1: CGFloat = 4    // player row bg offset
        static let s2: CGFloat = 8    // badge padding, league gap, margins
        static let s3: CGFloat = 12   // card padding, player row spacing
        static let s4: CGFloat = 16   // text gap, league left padding
        static let s5: CGFloat = 20   // teams gap, section gap
        static let s6: CGFloat = 24   // screen horizontal padding
        static let s8: CGFloat = 32   // reserved
        static let s10: CGFloat = 40  // title line height
        static let s12: CGFloat = 48  // player photo size
        static let s15: CGFloat = 60  // team logo size
    }

    // MARK: - Corner Radius
    enum Radius {
        static let r2: CGFloat = 8    // player photo
        static let r3: CGFloat = 12   // player row
        static let r4: CGFloat = 16   // card, badge corner
    }

    // MARK: - Icon / Image Sizes
    enum Icon {
        static let i4: CGFloat = 16    // league logo
        static let i6: CGFloat = 24    // back arrow
        static let i12: CGFloat = 48   // player photo
        static let i15: CGFloat = 60   // team logo
        static let i28: CGFloat = 113  // fuze splash logo
    }

    // MARK: - Component-specific dimensions
    // Only fixed pixel values that genuinely must be fixed (not layout containers).
    enum Component {
        static let playerRowHeight: CGFloat = 54
        static let playerRowBgOffset: CGFloat = 6
        static let playerTextWidth: CGFloat = 90
    }
}
