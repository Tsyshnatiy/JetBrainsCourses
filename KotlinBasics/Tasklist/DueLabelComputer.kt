package tasklist

import kotlinx.datetime.*

class DueLabelComputer {
    fun compute(date: LocalDate): String {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val numberOfDays = currentDate.daysUntil(date)
        return when (Integer.min(1, Integer.max(-1, numberOfDays))) {
                +1 -> "I"
                -1 -> "O"
                else -> "T"
        }
    }
}