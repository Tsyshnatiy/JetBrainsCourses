package gitinternals.commands.serializers

import gitinternals.commit.Commit
import java.time.format.DateTimeFormatter

class CommitSerializer(private val commit: Commit) {
    fun serialize(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val authorDateTime = formatter.format(commit.authorTimestamp.dateTime)
        val committerDateTime = formatter.format(commit.committerTimestamp.dateTime)
        val parents = commit.parents.joinToString(" | ")

        val author = StringBuilder()
        author.append("author: ", commit.author.name, " ")
        author.append(commit.author.email, " ")
        author.append("original ")
        author.append("timestamp: ", authorDateTime, " ")
        author.append(commit.authorTimestamp.timezone)

        val committer = StringBuilder()
        committer.append("committer: ", commit.committer.name, " ")
        committer.append(commit.committer.email, " ")
        committer.append("commit ")
        committer.append("timestamp: ", committerDateTime, " ")
        committer.append(commit.committerTimestamp.timezone)

        val result = StringBuilder()
        result.append("*COMMIT*", System.lineSeparator())
        result.append("tree: ", commit.treeHash, System.lineSeparator())
        if (parents.isNotEmpty()) {
            result.append("parents: ", parents, System.lineSeparator())
        }
        result.append(author, System.lineSeparator())
        result.append(committer, System.lineSeparator())
        result.append("commit message:", System.lineSeparator())
        result.append(commit.message)

        return result.toString()
    }
}