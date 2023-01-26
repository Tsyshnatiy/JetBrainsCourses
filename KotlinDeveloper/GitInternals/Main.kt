package gitinternals

import gitinternals.branch.BranchParser
import gitinternals.commands.CatFile
import gitinternals.commands.ListBranches

fun main() {
    println("Enter .git directory location:")
    val pathToGit = "C:\\Users\\Vlad\\IdeaProjects\\Git Internals\\Git Internals\\task\\test\\gitone"
    //val pathToGit = readln()

    val branches = BranchParser(pathToGit).parse()
    val objects = ObjectReader(pathToGit).read()

    println("Enter command:")

    when(readln().lowercase()) {
        "list-branches" -> ListBranches().list(branches)
        "cat-file" -> {
            println("Enter git object hash:")
            //val hash = readln()
            val hash = "109e8050b41bd10b81be0a51a5e67327f5609551"
            CatFile(objects).execute(hash)
        }
        else -> throw IllegalArgumentException("Invalid command")
    }
}
