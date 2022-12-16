package calculator

object SymbolRecognizer {
    private val signs = listOf('+', '-', '/', '*')

    fun isSign(c: Char): Boolean {
        return signs.contains(c)
    }

    fun getOperation(c: Char, isUnary: Boolean) : Operation {
        if (isUnary) {
            return when(c) {
                '+' -> Operation.UNARY_PLUS
                '-' -> Operation.UNARY_MINUS
                else -> throw IllegalArgumentException("Char is not operation")
            }
        }

        return when(c) {
            '+' -> Operation.BINARY_PLUS
            '-' -> Operation.BINARY_MINUS
            '/' -> Operation.DIVISION
            '*' -> Operation.MULTIPLICATION
            else -> throw IllegalArgumentException("Char is not operation")
        }
    }

    fun getOpPriority(op: Operation): Int {
        return when(op) {
            Operation.UNARY_PLUS -> 1
            Operation.BINARY_PLUS -> 1
            Operation.UNARY_MINUS -> 1
            Operation.BINARY_MINUS -> 1
            Operation.DIVISION -> 2
            Operation.MULTIPLICATION -> 2
        }
    }

    fun getSigns() : List<Char> {
        return signs
    }
}