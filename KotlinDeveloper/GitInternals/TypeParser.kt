package gitinternals

import java.text.ParseException

enum class Type {
    COMMIT,
    TREE,
    BLOB
}

class TypeParser {
    data class Result(val type: Type, val parsed: String);

    fun process(body: List<String>): Result {
        val firstLine = body[0].lowercase()
        if (firstLine.contains("blob")) {
            return Result(Type.BLOB, "*BLOB*")
        }

        if (firstLine.contains("commit")) {
            return Result(Type.COMMIT, "*COMMIT*")
        }

        if (firstLine.contains("tree")) {
            return Result(Type.TREE, "*TREE*")
        }

        throw ParseException("Unsupported object type", 0)
    }
}