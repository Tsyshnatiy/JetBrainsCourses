package gitinternals

import java.io.File

fun catFile(pathToGit: String) {
    println("Enter git object hash:")
    val hash = readln()
    //val hash = "109e8050b41bd10b81be0a51a5e67327f5609551"
    val firstTwoDigits = hash.substring(0 until 2)
    val last38Digits = hash.substring(2)

    val path = pathToGit + File.separator +
            "objects" + File.separator +
            firstTwoDigits + File.separator +
            last38Digits

    val blobProcessors = listOf(BlobBodyReader())

    val commitProcessors = listOf(CommitTreeParser(),
        ParentsParser(),
        AuthorParser(),
        CommitterParser(),
        CommitMessageParser())

    val treeProcessors = listOf(TreeObjectParser())

    val chain = ProcessingChain(TypeParser(),
        commitProcessors,
        blobProcessors,
        treeProcessors)

    println(chain.process(path))
}

fun main() {
    println("Enter .git directory location:")
    //val pathToGit = "C:\\Users\\Vlad\\IdeaProjects\\Git Internals\\Git Internals\\task\\test\\gitone"
    val pathToGit = readln()

    println("Enter command:")

    when(readln().lowercase()) {
        "list-branches" -> println(ListBranches(pathToGit).list())
        "cat-file" -> catFile(pathToGit)
        else -> throw IllegalArgumentException("Invalid command")
    }
}
