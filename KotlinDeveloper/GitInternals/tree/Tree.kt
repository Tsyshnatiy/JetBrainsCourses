package gitinternals.tree

import gitinternals.GitObject

data class TreeEntry(val filename: String,
                     val permissions: String,
                     val hash: String)

data class Tree(val treeHash: String, val entries: List<TreeEntry>) : GitObject {
    override fun getObjectHash(): String {
        return treeHash
    }
}