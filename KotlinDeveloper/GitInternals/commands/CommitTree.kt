package gitinternals.commands

import gitinternals.GitObjects
import gitinternals.tree.TreeEntry
import java.io.File

class CommitTree(private val gitObjects: GitObjects) {
    fun execute(commitHash: String) {
        val commit = gitObjects.commits.objects[commitHash]
            ?: throw RuntimeException("No commit with hash $commitHash")

        val tree = gitObjects.trees.objects[commit.treeHash]
            ?: throw RuntimeException("No tree with hash ${commit.treeHash}")

        val fileTree = collectFiles("", tree.entries)
        println(fileTree.joinToString(System.lineSeparator()))
    }

    private fun collectFiles(thisDirectory: String, entries: List<TreeEntry>) : List<String> {
        val isDirectory = { permissions: String -> permissions == "40000" }

        val result = mutableListOf<String>()

        for (entry in entries) {
            val path = if (thisDirectory.isEmpty())
                                entry.filename
                            else
                                thisDirectory + File.separator + entry.filename

            if (isDirectory(entry.permissions)) {
                val childTree = this.gitObjects.trees.objects[entry.hash]

                val dirTree = collectFiles(path, childTree!!.entries)
                result.addAll(dirTree)

                continue
            }

            result.add(path)
        }

        return result
    }
}