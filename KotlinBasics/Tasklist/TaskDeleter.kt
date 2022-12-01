package tasklist

class TaskDeleter {
    fun deleteTask(list: MutableList<Task>) {
        TaskPrinter().print(list)
        if (list.isEmpty()) {
            return
        }

        val indexReader = IndexReader(list.size)
        val indexToDelete = indexReader.read()
        list.removeAt(indexToDelete)
        println("The task is deleted")
    }
}