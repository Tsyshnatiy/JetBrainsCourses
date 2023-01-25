package gitinternals.commit

import java.text.ParseException

class ParentsParser {
    fun parse(bytes: ByteArray): List<String> {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        val result = mutableListOf<String>()

        val parent = "parent"
        for (l in body) {
            val parentPrefixIndex = l.indexOf(parent)
            if (parentPrefixIndex == -1) {
                continue
            }

            val parentHash = l.substring(parent.length + 1) // avoid space
            result.add(parentHash)
        }

        return result
    }
}