package tasklist

class TaskReader {
    fun readTask(): Task? {
        val priority = PriorityReader().read()
        val date = DateReader().read()
        val time = TimeReader().read()
        val dueLabel = DueLabelComputer().compute(date)
        val taskBody = TaskBodyReader().read() ?: return null

        val formattedDate = DateFormatter().format(date)
        return Task(priority, formattedDate, time, dueLabel, taskBody)
    }
}