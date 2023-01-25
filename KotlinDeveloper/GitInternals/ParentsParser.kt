package gitinternals

import java.lang.StringBuilder
import java.text.ParseException

class ParentsParser: IProcessor {
    override fun process(bytes: ByteArray): String {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        val result = StringBuilder()

        val parent = "parent"
        for (l in body) {
            val parentPrefixIndex = l.indexOf(parent)
            if (parentPrefixIndex == -1) {
                continue
            }

            if (result.isNotEmpty()) {
                result.append(" | ")
            }
            else {
                result.append("parents: ")
            }

            val parentHash = l.substring(parent.length + 1) // avoid space
            result.append(parentHash)
        }

        if (result.isNotEmpty()) {
            result.append(System.lineSeparator())
        }
        return result.toString()
    }
}