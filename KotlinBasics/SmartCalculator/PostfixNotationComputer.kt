package calculator

import java.math.BigInteger
import java.util.Stack

class PostfixNotationComputer(private val varStorage: VariableStorage,
                              private val notation: List<SyntaxNode>) {

    private val stack = Stack<BigInteger>()

    fun compute() : BigInteger {
        for (e in notation) {
            if (e is SyntaxNode.NumberNode) {
                stack.push(e.n)
                continue
            }

            if (e is SyntaxNode.VariableNode) {
                val varValue = varStorage[e.name] ?: throw UnknownVariableException("Unknown variable")
                stack.push(varValue)
                continue
            }

            if (e is SyntaxNode.OperationNode) {
                if (e.isUnary()) {
                    handleUnaryOperation(e)
                }
                else {
                    handleBinaryOperation(e)
                }
            }
        }

        if (stack.size != 1) {
            throw InvalidExpressionException("Stack has not 1 element")
        }
        return stack.pop()
    }

    private fun handleUnaryOperation(node: SyntaxNode.OperationNode) {
        if (stack.size < 1) {
            throw InvalidExpressionException("Stack has less than 1 elements")
        }
        val op = stack.pop()
        val result = applyUnaryOperation(op, node.op)
        stack.push(result)
    }

    private fun handleBinaryOperation(node: SyntaxNode.OperationNode) {
        if (stack.size < 2) {
            throw InvalidExpressionException("Stack has less than 2 elements")
        }
        val op1 = stack.pop()
        val op2 = stack.pop()

        val result = applyBinaryOperation(op1, op2, node.op)
        stack.push(result)
    }

    private fun applyUnaryOperation(a: BigInteger, op: Operation) : BigInteger {
        return when(op) {
            Operation.UNARY_MINUS -> -a
            Operation.UNARY_PLUS -> a
            else -> throw IllegalArgumentException("Invalid unary operation $op")
        }
    }

    private fun applyBinaryOperation(a: BigInteger, b: BigInteger, op: Operation): BigInteger {
        return when(op) {
            Operation.BINARY_PLUS -> a + b
            Operation.BINARY_MINUS -> b - a
            Operation.MULTIPLICATION -> a * b
            Operation.DIVISION -> b / a
            else -> throw IllegalArgumentException("Invalid binary operation $op")
        }
    }
}