package gitinternals.commands

import gitinternals.GitObjects

class CommitTree(private val gitObjects: GitObjects) {
    fun execute(commitHash: String) {
        val commit = gitObjects.commits.objects[commitHash]
            ?: throw RuntimeException("No commit with hash $commitHash")

        val tree = gitObjects.trees.objects[commit.treeHash]
            ?: throw RuntimeException("No tree with hash ${commit.treeHash}")

        println(tree.entries.joinToString(System.lineSeparator()) { it.filename })
    }
}