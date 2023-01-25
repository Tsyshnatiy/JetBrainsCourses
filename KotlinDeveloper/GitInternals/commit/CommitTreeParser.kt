package gitinternals.commit

import java.text.ParseException

class CommitTreeParser {
    fun parse(bytes: ByteArray): String {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        val treeLineIndex = body.indexOfFirst { it.startsWith("tree") }
        if (treeLineIndex == -1) {
            throw ParseException("Unable to find tree line", 0)
        }

        val parts = body[treeLineIndex].split(' ')
        if (parts.size != 2) {
            throw ParseException("Tree line has wrong format", 0)
        }

        return parts[1]
    }
}