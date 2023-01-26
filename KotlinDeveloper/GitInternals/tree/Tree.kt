package gitinternals.tree

data class TreeEntry(val filename: String,
                     val permissions: String,
                     val hash: String)

data class Tree(val treeHash: String, val entries: List<TreeEntry>)