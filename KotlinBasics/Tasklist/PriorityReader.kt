package tasklist

class PriorityReader {
    fun read(): Priority {
        var result: Priority? = null
        while (result == null) {
            println("Input the task priority (C, H, N, L):")
            val priorityString = readln().uppercase()
            result = when (priorityString) {
                "C" -> Priority.CRITICAL
                "H" -> Priority.HIGH
                "N" -> Priority.NORMAL
                "L" -> Priority.LOW
                else -> continue
            }
        }

        return result
    }
}