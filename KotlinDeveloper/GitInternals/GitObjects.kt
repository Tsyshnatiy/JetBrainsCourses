package gitinternals

import gitinternals.blob.Blob
import gitinternals.commit.Commit
import gitinternals.tree.Tree

data class GitObjects(val trees: List<Tree>,
                        val commits: List<Commit>,
                        val blobs: List<Blob>)