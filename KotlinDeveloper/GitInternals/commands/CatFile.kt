package gitinternals.commands

import gitinternals.GitObjects
import gitinternals.commands.serializers.BlobSerializer
import gitinternals.commands.serializers.CommitSerializer
import gitinternals.commands.serializers.TreeSerializer

class CatFile(private val gitObjects: GitObjects) {
    fun execute(hash: String) {
        // TODO Parallel search
        for (treeHash in gitObjects.trees.hashes) {
            if (treeHash == hash) {
                val tree = gitObjects.trees.objects[treeHash]
                println(TreeSerializer(tree!!).serialize())
                return
            }
        }

        for (commitHash in gitObjects.commits.hashes) {
            if (commitHash == hash) {
                val commit = gitObjects.commits.objects[commitHash]
                println(CommitSerializer(commit!!).serialize())
                return
            }
        }

        for (blobHash in gitObjects.blobs.hashes) {
            if (blobHash == hash) {
                val blob = gitObjects.blobs.objects[blobHash]
                println(BlobSerializer(blob!!).serialize())
                return
            }
        }
    }
}