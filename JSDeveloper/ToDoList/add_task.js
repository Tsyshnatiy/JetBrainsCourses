function onAddTask() {
    const taskList = document.getElementById("task-list")
    const taskTextField = document.getElementById("input-task")
    if (!taskList || !taskTextField) {
        alert("taskList or taskTextField not found")
        return;
    }

    if (taskTextField.value === "") {
        alert("Task text is empty")
        return
    }

    const newItem = document.createElement("li")
    newItem.setAttribute("class", "task-list-item")

    const checkBox = document.createElement("input")
    checkBox.setAttribute("type", "checkbox")

    const spacing = document.createElement("div")
    spacing.setAttribute("class", "task-spacing")

    const span = document.createElement("span")
    span.setAttribute("class", "task")
    span.textContent = taskTextField.value

    const button = document.createElement("button")
    button.setAttribute("class", "delete-btn")
    button.setAttribute("onclick", "onRemoveTask(this)")

    newItem.append(checkBox)
    newItem.append(span)
    newItem.append(spacing)
    newItem.append(button)

    taskList.append(newItem)

    taskTextField.value = ""
}