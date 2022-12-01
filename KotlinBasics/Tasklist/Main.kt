package tasklist

fun main() {
    val jsonSerializer = JsonSerializer()
    val taskList = jsonSerializer.load().toMutableList()
    val taskReader = TaskReader()
    val taskPrinter = TaskPrinter()
    val taskDeleter = TaskDeleter()
    val taskEditor = TaskEditor()

    while(true) {
        println("Input an action (add, print, edit, delete, end):")
        when(readln().trim().lowercase()) {
            "add" -> {
                val task = taskReader.readTask()
                if (task != null) {
                    taskList.add(task)
                }
            }

            "print" -> {
                taskPrinter.print(taskList)
            }

            "delete" -> {
                taskDeleter.deleteTask(taskList)
            }

            "edit" -> {
                taskEditor.editTask(taskList)
            }

            "end" -> {
                println("Tasklist exiting!")
                break
            }
            else -> println("The input action is invalid")
        }
    }

    jsonSerializer.save(taskList)
}


