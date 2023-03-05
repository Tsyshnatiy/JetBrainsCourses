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

    const checkBox = document.createElement("input")
    checkBox.setAttribute("type", "checkbox")

    const span = document.createElement("span")
    span.setAttribute("class", "task")
    span.textContent = taskTextField.value

    const button = document.createElement("button")
    button.setAttribute("class", "delete-btn")

    newItem.append(checkBox)
    newItem.append(span)
    newItem.append(button)

    taskList.append(newItem)

    taskTextField.value = ""
}