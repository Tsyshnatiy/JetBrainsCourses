package gitinternals.commands.serializers

import gitinternals.tree.Tree

class TreeSerializer(private val tree: Tree) {
    fun serialize(): String {
        val result = StringBuilder()
        result.append("*TREE*", System.lineSeparator())

        for (entry in tree.entries) {
            result.append(entry.permissions, " ", entry.hash, " ", entry.filename, System.lineSeparator())
        }
        return result.toString()
    }
}