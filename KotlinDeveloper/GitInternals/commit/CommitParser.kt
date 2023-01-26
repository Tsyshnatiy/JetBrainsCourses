package gitinternals.commit

import java.text.ParseException

class CommitParser {
    fun parse(bodyBytes: ByteArray, hash: String) : Commit {
        val treeHash = CommitTreeParser().parse(bodyBytes)
        val parents = ParentsParser().parse(bodyBytes)
        val message = CommitMessageParser().parse(bodyBytes)

        val authorLine = findLine("author", bodyBytes)
        val committerLine = findLine("committer", bodyBytes)
        val authorPersonality = PersonalityParser.parse(authorLine)
        val committerPersonality = PersonalityParser.parse(committerLine)

        val author = Author(authorPersonality.name, authorPersonality.email)
        val committer = Author(committerPersonality.name, committerPersonality.email)

        val authorTimestamp = Timestamp(authorPersonality.datetime, authorPersonality.timeZone)
        val committerTimestamp = Timestamp(committerPersonality.datetime, committerPersonality.timeZone)

        return Commit(author, committer, authorTimestamp, committerTimestamp,
                      message, treeHash, hash, parents)
    }

    private fun findLine(prefix: String, bytes: ByteArray): String {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        val index = body.indexOfFirst { it.startsWith(prefix) }
        if (index == -1) {
            throw ParseException("Unable to find author", 0)
        }

        return body[index]
    }
}