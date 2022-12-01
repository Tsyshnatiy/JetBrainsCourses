package tasklist

class TaskEditor {
    fun editTask(list: List<Task>) {
        TaskPrinter().print(list)
        if (list.isEmpty()) {
            return
        }

        val indexReader = IndexReader(list.size)
        val index = indexReader.read()

        when(readFieldName()) {
            "priority" -> {
                val priority = PriorityReader().read()
                list[index].priority = priority
            }

            "date" -> {
                val date = DateReader().read()
                val formattedDate = DateFormatter().format(date)
                val dueLabel = DueLabelComputer().compute(date)
                val task = list[index]
                task.date = formattedDate
                task.dueLabel = dueLabel
            }

            "time" -> {
                val time = TimeReader().read()
                list[index].time = time
            }

            "task" -> {
                val taskBody = TaskBodyReader().read() ?: return
                list[index].lines = taskBody
            }
        }

        println("The task is changed")
    }

    private fun readFieldName(): String {
        var result: String? = null
        while (result == null) {
            println("Input a field to edit (priority, date, time, task):")
            val input = readln().trim().lowercase()
            result = when(input) {
                "priority" -> "priority"
                "date" -> "date"
                "time" -> "time"
                "task" -> "task"
                else -> {
                    println("Invalid field")
                    null
                }
            }
        }

        return result
    }
}