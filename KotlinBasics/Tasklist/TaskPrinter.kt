package tasklist

import java.lang.StringBuilder

class TaskPrinter {
    fun print(list: List<Task>) {
        if (list.isEmpty()) {
            println("No tasks have been input")
            return
        }

        printHeader()
        list.forEachIndexed {index, task ->
            printTask(index, task)
        }
    }

    private fun printHeader() {
        val header = "| N  |    Date    | Time  | P | D |                   Task                     |"
        println(lineSeparator)
        println(header)
        println(lineSeparator)
    }

    private fun printTask(index: Int, task: Task) {
        val result = StringBuilder()
        val emptyFirstColumns = "|    |            |       |   |   |"
        for (i in task.lines.indices) {
            val splittedLine = splitTaskLine(task.lines[i])
            if (i == 0) {
                result.append(computeIndex(index))
                result.append(computeDate(task.date))
                result.append(computeTime(task.time))
                result.append(computePriority(task.priority))
                result.append(computeDueLabel(task.dueLabel))
            }
            else {
                result.append(emptyFirstColumns)
            }

            for (chunkIndex in splittedLine.indices) {
                val chunk = splittedLine[chunkIndex]
                if (chunkIndex != 0) {
                    result.append(emptyFirstColumns)
                }

                result.append(chunk)
                if (chunk.length < taskLineSpace) {
                    val spaces = taskLineSpace - chunk.length
                    result.append(" ".repeat(spaces))
                }
                result.append("|\n")
            }
        }
        print(result)
        println(lineSeparator)
    }

    private fun computeIndex(index: Int): String {
        val result = StringBuilder("| ")
        result.append(index + 1)
        result.append(if (index < 10) "  |" else " |")
        return result.toString()
    }

    private fun computeDate(date: String): String {
        return " $date |"
    }

    private fun computeTime(time: String): String {
        return " $time |"
    }

    private fun computePriority(priority: Priority): String {
        val color = when(priority) {
            Priority.LOW -> "\u001B[104m \u001B[0m"
            Priority.NORMAL -> "\u001B[102m \u001B[0m"
            Priority.HIGH -> "\u001B[103m \u001B[0m"
            Priority.CRITICAL -> "\u001B[101m \u001B[0m"
        }
        return " $color |"
    }

    private fun computeDueLabel(dueLabel: String): String {
        val color = when(dueLabel) {
            "I" -> "\u001B[102m \u001B[0m"
            "O" -> "\u001B[101m \u001B[0m"
            "T" -> "\u001B[103m \u001B[0m"
            else -> throw IllegalArgumentException("Invalid due label value")
        }

        return " $color |"
    }

    private fun splitTaskLine(line: String): List<String> {
        return line.chunked(taskLineSpace)
    }

    private val taskLineSpace = 44
    private val lineSeparator = "+----+------------+-------+---+---+--------------------------------------------+"
}