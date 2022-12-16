package calculator

import java.lang.StringBuilder

class SignSequenceNormalizer(private val next: IExpressionHandler?) : IExpressionHandler {
    override fun handle(input: String): Boolean {
        val normalized = normalize(input)

        return next?.handle(normalized) ?: false
    }

    private fun normalize(input: String): String {
        val result = StringBuilder()
        var i = 0
        while (i < input.length) {
            val nextSignSeq = getNextSignSequence(i, input)
            if (nextSignSeq == null) {
                result.appendRange(input, i, input.length)
                return result.toString()
            }

            // Append everything between current index and next sign seq
            val start = nextSignSeq.first
            result.appendRange(input, i, start)
            // Append processed sign seq
            val symbol = processSignSequence(nextSignSeq, input)
            result.append(symbol)

            i = nextSignSeq.second // set i to seq end
        }

        return result.toString()
    }

    private fun processSignSequence(seq: Pair<Int, Int>, input: String) : Char {
        val start = seq.first
        val end = seq.second

        var result = input[start]
        for (i in start + 1 until end) {
            val sign = input[i]
            if (result == '+' && sign == '+') {
                continue
            }
            if ((result == '+' && sign == '-')
                || (result == '-' && sign == '+')) {
                result = '-'
                continue
            }
            if (result == '-' && sign == '-') {
                result = '+'
                continue
            }
        }
        return result
    }

    private fun getNextSignSequence(start: Int, input: String) : Pair<Int, Int>? {
        val seqStart = getNextSign(start, input) ?: return null

        var i: Int = seqStart
        while (i < input.length && SymbolRecognizer.isSign(input[i])) {
            ++i
        }
        val seqEnd = i

        return Pair(seqStart, seqEnd)
    }

    private fun getNextSign(start: Int, input: String) : Int? {
        val signsAsString = SymbolRecognizer.getSigns().map { it.toString() }
        val match = input.findAnyOf(signsAsString, start) ?: return null
        return match.first
    }
}