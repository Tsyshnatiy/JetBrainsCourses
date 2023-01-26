package gitinternals

import gitinternals.branch.BranchParser
import gitinternals.commands.CatFile
import gitinternals.commands.CommitTree
import gitinternals.commands.ListBranches
import gitinternals.commands.Log

fun toGitObjects(parsed: ObjectReader.ParsedGitObjects): GitObjects {
    return GitObjects(toHashedStorage(parsed.trees),
                        toHashedStorage(parsed.commits),
                        toHashedStorage(parsed.blobs))
                }

fun main() {
    println("Enter .git directory location:")
    val pathToGit = readln()

    val branches = BranchParser(pathToGit).parse()
    val objects = toGitObjects(ObjectReader(pathToGit).read())
    println("Enter command:")

    when(readln().lowercase()) {
        "list-branches" -> ListBranches().list(branches)
        "cat-file" -> {
            println("Enter git object hash:")
            val hash = readln()
            CatFile(objects).execute(hash)
        }
        "log" -> {
            println("Enter branch name:")
            val branchName = readln()
            Log(branches, objects, branchName).execute()
        }
        "commit-tree" -> {
            println("Enter commit-hash:")
            val commitHash = readln()
            CommitTree(objects).execute(commitHash)
        }
        else -> throw IllegalArgumentException("Invalid command")
    }
}
