package gitinternals.commit

import java.io.File
import java.io.FileInputStream
import java.text.ParseException
import java.util.zip.InflaterInputStream

class CommitParser(private val pathToGit: String) {
    fun parse(hash: String) : Commit {
        val firstTwoDigits = hash.substring(0 until 2)
        val last38Digits = hash.substring(2)

        val path = pathToGit + File.separator +
                "objects" + File.separator +
                firstTwoDigits + File.separator +
                last38Digits

        val fis = FileInputStream(path)
        val iis = InflaterInputStream(fis)
        val bytes = iis.readAllBytes()

        val indexOfZeroByte = bytes.indexOf(0.toByte())
        if (indexOfZeroByte == -1) {
            throw ParseException("Unable to find type and length", 0)
        }
        val bodyBytes = bytes.copyOfRange(indexOfZeroByte + 1, bytes.size)

        val treeHash = CommitTreeParser().parse(bodyBytes)
        val parents = ParentsParser().parse(bodyBytes)
        val message = CommitMessageParser().parse(bodyBytes)

        val authorLine = findAuthorLine(bodyBytes)
        val personality = PersonalityParser.parse(authorLine)

        iis.close()
        fis.close()

        val author = Author(personality.name, personality.email)

        return Commit(author, personality.datetime, message, treeHash, parents)
    }

    private fun findAuthorLine(bytes: ByteArray): String {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        val author = "author"
        val authorIndex = body.indexOfFirst { it.startsWith(author) }
        if (authorIndex == -1) {
            throw ParseException("Unable to find author", 0)
        }

        return body[authorIndex]
    }
}