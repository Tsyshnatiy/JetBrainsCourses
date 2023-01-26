package gitinternals.commit

import java.text.ParseException
import java.time.LocalDateTime
import java.time.ZoneOffset

object PersonalityParser {
    data class Result(val name: String,
                      val email: String,
                      val datetime: LocalDateTime,
                      val timeZone: String)

    fun parse(line: String) : Result {
        val lines = line.split(' ')
        if (lines.size != 5) {
            throw ParseException("Author line has wrong format", 0)
        }
        val name = lines[1]
        // remove <>
        val email = lines[2].substring(1 until lines[2].length - 1)
        val timestamp = lines[3]
        val timezone = getNormalizedTimezone(lines[4])

        val offset = ZoneOffset.of(timezone)
        val datetime = LocalDateTime.ofEpochSecond(timestamp.toLong(), 0, offset)

        return Result(name, email, datetime, timezone)
    }

    private fun getNormalizedTimezone(timezone: String): String {
        val prefix = timezone.dropLast(2)
        val suffix = timezone.takeLast(2)
        return "$prefix:$suffix"
    }
}