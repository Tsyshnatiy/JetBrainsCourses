function onTaskStateChange(sender) {
    const li = sender.closest(".task-list-item")
    const tasks = li.getElementsByClassName("task")
    if (!li) {
        alert("Could not find items list")
        return
    }

    if (!tasks || tasks.length != 1) {
        alert("Could not determine span")
        return
    }

    const span = tasks[0]
    if (sender.checked) {
        span.style["text-decoration"] = "line-through"
    }
    else {
        span.style.removeProperty("text-decoration")
    }
}