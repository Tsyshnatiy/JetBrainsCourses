
class Task {
    constructor(checked, text) {
        this.checked = checked
        this.text = text
    }
}

const taskStorage = []
const taskStorageKey = "taskStorage"

function onAddTask() {
    const taskTextField = document.getElementById("input-task")
    if (!taskTextField) {
        alert("taskTextField not found")
        return;
    }

    if (taskTextField.value === "") {
        alert("Task text is empty")
        return
    }

    const task = new Task(false, taskTextField.value)
    addTask(task)

    taskTextField.value = ""
}

function addTask(task) {
    if (!task) {
        return
    }

    const htmlTaskList = document.getElementById("task-list")
    if (!htmlTaskList) {
        alert("htmlTaskList not found")
        return;
    }

    const newItem = document.createElement("li")
    newItem.setAttribute("class", "task-list-item")

    const checkBox = document.createElement("input")
    checkBox.setAttribute("type", "checkbox")
    checkBox.setAttribute("onclick", "onTaskStateChange(this)")

    const spacing = document.createElement("div")
    spacing.setAttribute("class", "task-spacing")

    const span = document.createElement("span")
    span.setAttribute("class", "task")
    span.textContent = task.text

    if (task.checked) {
        span.style["text-decoration"] = "line-through"
        checkBox.checked = true
    }

    const button = document.createElement("button")
    button.setAttribute("class", "delete-btn")
    button.setAttribute("onclick", "onRemoveTask(this)")

    newItem.append(checkBox)
    newItem.append(span)
    newItem.append(spacing)
    newItem.append(button)

    htmlTaskList.append(newItem)
    taskStorage.push(task)

    localStorage.setItem(taskStorageKey, JSON.stringify(taskStorage))
}

function onTaskStateChange(sender) {
    const index = getTaskIndexBySender(sender)
    if (index === -1) {
        alert("Could not update taskStorage")
        return
    }
    taskStorage[index].checked = sender.checked
    localStorage.setItem(taskStorageKey, JSON.stringify(taskStorage))

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

function onRemoveTask(sender) {
    const index = getTaskIndexBySender(sender)
    if (index === -1) {
        alert("Could not update taskStorage")
        return
    }
    taskStorage[index] = undefined
    localStorage.setItem(taskStorageKey, JSON.stringify(taskStorage))

    const li = sender.closest(".task-list-item")
    const ul = li.closest("#task-list")

    ul.removeChild(li)
}

function getTaskIndexBySender(sender) {
    const li = sender.closest(".task-list-item")
    const ul = li.closest("#task-list")
    // -1 is text node of ul
    return [...ul.childNodes].findIndex(item => item === li) - 1
}

function beforeLoad() {
    const tasks = JSON.parse(localStorage.getItem(taskStorageKey)) || []
    for (const t of tasks) {
        addTask(t)
    }
}