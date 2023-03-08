function onRemoveTask(sender) {
    const li = sender.closest(".task-list-item")
    const ul = li.closest("#task-list")

    ul.removeChild(li)
}