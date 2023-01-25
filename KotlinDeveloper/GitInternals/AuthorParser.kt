package gitinternals

import java.text.ParseException

class AuthorParser: IProcessor {
    override fun process(bytes: ByteArray): String {
        val body = String(bytes).lines()
        if (body.isEmpty()) {
            throw ParseException("Content of an object is damaged", 0)
        }

        val result = StringBuilder()

        val author = "author"
        val authorIndex = body.indexOfFirst { it.startsWith(author) }
        if (authorIndex == -1) {
            throw ParseException("Unable to find author", 0)
        }

        val authorLine = body[authorIndex]
        val parseResult = PersonalityParser.parse(authorLine)

        result.append("author: ")
        result.append(parseResult.name)
        result.append(" ")
        // email without <>
        result.append(parseResult.email)
        result.append(" ")
        result.append("original ")
        result.append("timestamp: ")
        result.append(parseResult.formattedDate)
        result.append(" ")
        result.append(parseResult.timezone)
        result.append(System.lineSeparator())
        return result.toString()
    }
}