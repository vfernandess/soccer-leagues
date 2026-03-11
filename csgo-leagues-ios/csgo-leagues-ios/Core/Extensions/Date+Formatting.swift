import Foundation

extension Date {
    /// Formats a match date/time for display in match badges.
    /// - "Hoje, HH:mm" if today
    /// - "EEE, HH:mm" if within this calendar week (localized short weekday, capitalized)
    /// - "dd.MM HH:mm" otherwise
    func matchBadgeText() -> String {
        let calendar = Calendar.current
        let now = Date()
        let timeString = formatted(date: .omitted, time: .shortened)

        if calendar.isDateInToday(self) {
            return "Hoje, \(timeString)"
        }

        // Check if within current week (same week of year, same year)
        let nowComponents = calendar.dateComponents([.yearForWeekOfYear, .weekOfYear], from: now)
        let selfComponents = calendar.dateComponents([.yearForWeekOfYear, .weekOfYear], from: self)
        if nowComponents.yearForWeekOfYear == selfComponents.yearForWeekOfYear &&
           nowComponents.weekOfYear == selfComponents.weekOfYear {
            let weekday = formatted(.dateTime.weekday(.abbreviated))
            let capitalized = weekday.prefix(1).uppercased() + weekday.dropFirst()
            return "\(capitalized), \(timeString)"
        }

        let dayMonth = formatted(.dateTime.day().month(.twoDigits))
        // dayMonth is typically "DD/MM" — normalize to "DD.MM"
        let normalized = dayMonth.replacingOccurrences(of: "/", with: ".")
        return "\(normalized) \(timeString)"
    }
}
