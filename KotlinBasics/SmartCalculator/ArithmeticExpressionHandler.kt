package calculator

class ArithmeticExpressionHandler(private val variableStorage: VariableStorage) : IExpressionHandler {

    override fun handle(input: String): Boolean {
        val postfixNotationCreator = PostfixNotationCreator(input)
        val postfixNotation = postfixNotationCreator.build()
        val postfixNotationComputer = PostfixNotationComputer(variableStorage, postfixNotation)

        println(postfixNotationComputer.compute())
        return true
    }
}