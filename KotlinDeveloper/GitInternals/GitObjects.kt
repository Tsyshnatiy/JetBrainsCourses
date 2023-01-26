package gitinternals

import gitinternals.blob.Blob
import gitinternals.commit.Commit
import gitinternals.tree.Tree

data class HashedGitObjectsStorage<T>(val hashes: List<String>,
                                        val objects: HashMap<String, T>)

data class GitObjects(val trees: HashedGitObjectsStorage<Tree>,
                        val commits: HashedGitObjectsStorage<Commit>,
                        val blobs: HashedGitObjectsStorage<Blob>)

fun <T> toHashedStorage(gitObjects: List<T>): HashedGitObjectsStorage<T>
    where T : GitObject {
    val hashes = mutableListOf<String>()
    val objects = HashMap<String, T>()

    for (gitObj in gitObjects) {
        hashes.add(gitObj.getObjectHash())
        objects[gitObj.getObjectHash()] = gitObj
    }

    return HashedGitObjectsStorage(hashes, objects)
}