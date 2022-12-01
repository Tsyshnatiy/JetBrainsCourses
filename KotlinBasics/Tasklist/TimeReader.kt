package tasklist

import java.lang.IllegalArgumentException
import java.time.LocalTime

class TimeReader {
    private fun normalizeTime(time: String): String {
        val tokens = time.split(":")
        if (tokens.size != 2) {
            throw IllegalArgumentException("The input time is invalid")
        }

        var hh = tokens[0]
        var mm = tokens[1]

        if (hh.length == 1) {
            hh = "0$hh"
        }
        if (mm.length == 1) {
            mm = "0$mm"
        }

        return "$hh:$mm"
    }

    fun read(): String {
        var result = ""
        var validator: LocalTime? = null
        while (validator == null) {
            println("Input the time (hh:mm):")
            validator = try {
                result = normalizeTime(readln().trim())
                LocalTime.parse(result)
            } catch (e: Exception) {
                println("The input time is invalid")
                null
            }
        }

        return result
    }
}