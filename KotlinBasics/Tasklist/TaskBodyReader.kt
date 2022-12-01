package tasklist

class TaskBodyReader {
    fun read(): List<String>? {
        println("Input a new task (enter a blank line to end):")
        val lines = mutableListOf<String>()
        while (true) {
            val line = readln().trim()
            if (line.isEmpty()) {
                break
            }

            lines.add(line)
        }

        if (lines.isEmpty()) {
            println("The task is blank")
            return null
        }

        return lines
    }
}