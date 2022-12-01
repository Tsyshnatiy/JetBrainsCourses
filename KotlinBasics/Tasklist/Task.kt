package tasklist

data class Task(
    var priority: Priority,
    var date: String,
    var time: String,
    var dueLabel: String,
    var lines: List<String>)