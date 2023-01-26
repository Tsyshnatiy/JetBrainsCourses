package gitinternals

import java.text.ParseException

enum class Type {
    COMMIT,
    TREE,
    BLOB
}

class ObjectTypeParser {
    fun parse(bytes: ByteArray): Type {
        val body = String(bytes)

        val line = body.lowercase()
        if (line.contains("blob")) {
            return Type.BLOB
        }

        if (line.contains("commit")) {
            return Type.COMMIT
        }

        if (line.contains("tree")) {
            return Type.TREE
        }

        throw ParseException("Unsupported object type", 0)
    }
}