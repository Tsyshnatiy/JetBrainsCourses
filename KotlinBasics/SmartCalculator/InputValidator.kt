package calculator

class InputValidator(private val next: IExpressionHandler?) : IExpressionHandler {
    private val multiplicationSequence = "(\\*{2,})".toRegex()
    private val divisionSequence = "(\\/{2,})".toRegex()
    private val divOrMulSequence = "$multiplicationSequence|$divisionSequence".toRegex()

    override fun handle(input: String): Boolean {
        if (divOrMulSequence.find(input) != null) {
            throw InvalidExpressionException("Expr has seq of * or \\")
        }

        return next?.handle(input) ?: false
    }
}