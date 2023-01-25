package gitinternals

import java.lang.StringBuilder
import java.text.ParseException

class CommitMessageParser: IProcessor {
    override fun process(bytes:ByteArray): String {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        var emptyIndex = body.indexOfFirst { it.isEmpty() }
        if (emptyIndex == -1) {
            throw ParseException("Unable to find commit message", 0)
        }
        val result = StringBuilder()
        result.append("commit message:", System.lineSeparator())

        emptyIndex++
        var messageLine = body[emptyIndex]

        while(messageLine.isNotEmpty()) {
            result.append(messageLine, System.lineSeparator())
            emptyIndex++
            messageLine = body[emptyIndex]
        }

        return result.toString()
    }
}