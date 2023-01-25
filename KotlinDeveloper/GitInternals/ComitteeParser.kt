package gitinternals

import java.text.ParseException

class CommitterParser: IProcessor {
    override fun process(bytes: ByteArray): String {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        val result = StringBuilder()

        val committer = "committer"
        val committerIndex = body.indexOfFirst { it.startsWith(committer) }
        if (committerIndex == -1) {
            throw ParseException("Unable to find committer", 0)
        }

        val committerLine = body[committerIndex]
        val parseResult = PersonalityParser.parse(committerLine)

        result.append("committer: ")
        result.append(parseResult.name)
        result.append(" ")
        // email without <>
        result.append(parseResult.email)
        result.append(" ")
        result.append("commit ")
        result.append("timestamp: ")
        result.append(parseResult.formattedDate)
        result.append(" ")
        result.append(parseResult.timezone)
        result.append(System.lineSeparator())
        return result.toString()
    }
}