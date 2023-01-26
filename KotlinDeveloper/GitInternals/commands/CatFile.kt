package gitinternals.commands

import gitinternals.GitObjects
import gitinternals.commands.serializers.BlobSerializer
import gitinternals.commands.serializers.CommitSerializer
import gitinternals.commands.serializers.TreeSerializer

class CatFile(private val gitObjects: GitObjects) {
    fun execute(hash: String) {
        // TODO Parallel search
        for (tree in gitObjects.trees) {
            if (tree.treeHash == hash) {
                println(TreeSerializer(tree).serialize())
                return
            }
        }

        for (commit in gitObjects.commits) {
            if (commit.hash == hash) {
                println(CommitSerializer(commit).serialize())
                return
            }
        }

        for (blob in gitObjects.blobs) {
            if (blob.hash == hash) {
                println(BlobSerializer(blob).serialize())
                return
            }
        }
    }
}