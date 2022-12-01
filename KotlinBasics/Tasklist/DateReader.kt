package tasklist

import kotlinx.datetime.*
import java.lang.IllegalArgumentException

class DateReader {
    fun read(): LocalDate {
        var taskDate: LocalDate? = null
        while (taskDate == null) {
            println("Input the date (yyyy-mm-dd):")
            taskDate = try {
                val normalized = normalizeDate(readln().trim())
                LocalDate.parse(normalized)
            } catch (e: Exception) {
                println("The input date is invalid")
                null
            }
        }

        return taskDate
    }

    private fun normalizeDate(date: String): String {
        val tokens = date.split("-")
        if (tokens.size != 3) {
            throw IllegalArgumentException("The input date is invalid")
        }

        val year = tokens[0]
        var month = tokens[1]
        var day = tokens[2]

        if (month.length == 1) {
            month = "0$month"
        }
        if (day.length == 1) {
            day = "0$day"
        }

        return "$year-$month-$day"
    }
}