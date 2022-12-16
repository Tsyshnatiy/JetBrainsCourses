package calculator

import java.math.BigInteger

enum class Operation {
    UNARY_PLUS,
    UNARY_MINUS,
    BINARY_PLUS,
    BINARY_MINUS,
    DIVISION,
    MULTIPLICATION;

    override fun toString(): String {
        return when(this) {
            UNARY_PLUS -> "+"
            UNARY_MINUS -> "-"
            BINARY_PLUS -> "+"
            BINARY_MINUS -> "-"
            DIVISION -> "/"
            MULTIPLICATION -> "*"
        }
    }
}

val IdentifierPattern = "[a-zA-Z]+".toRegex()
val SignPattern = "[-+*/]".toRegex()
val NumberPattern = "$SignPattern?\\d+".toRegex()

sealed class SyntaxNode {
    data class NumberNode(val n: BigInteger) : SyntaxNode()
    data class VariableNode(val name: String) : SyntaxNode()
    data class OperationNode(val op: Operation) : SyntaxNode() {
        fun isUnary() : Boolean {
            return op == Operation.UNARY_PLUS || op == Operation.UNARY_MINUS
        }
    }
}