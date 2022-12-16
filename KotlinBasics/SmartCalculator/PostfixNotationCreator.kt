package calculator

import java.util.Stack

sealed class StackEntry {
    data class Operation(val op: calculator.Operation) : StackEntry()
    data class BracketNode(val bracket: Char) : StackEntry()
}

class PostfixNotationCreator(private val expression: String) {
    private val stack = Stack<StackEntry>()

    fun build() : List<SyntaxNode> {
        val result = mutableListOf<SyntaxNode>()

        val popAllOpFromStack = {
            while (!stack.empty()) {
                val entry = stack.pop()
                if (entry is StackEntry.BracketNode) {
                    throw InvalidExpressionException("Wrong brackets order")
                }
                val stackOperation = entry as StackEntry.Operation
                val op = SyntaxNode.OperationNode(stackOperation.op)
                result.add(op)
            }
        }

        val popUntilOpenBracket = {
            while (true) {
                if (stack.empty()) {
                    throw InvalidExpressionException("Wrong brackets order")
                }

                val entry = stack.pop()
                if (entry is StackEntry.BracketNode) {
                    break
                }

                val topOp = entry as StackEntry.Operation
                result.add(SyntaxNode.OperationNode(topOp.op))
            }
        }

        var i = 0
        while (i < expression.length) {
            if (expression[i] == '(') {
                stack.push(StackEntry.BracketNode('('))
                ++i
                continue
            }

            if (expression[i] == ')') {
                popUntilOpenBracket()
                ++i
                continue
            }

            if (SymbolRecognizer.isSign(expression[i])) {
                val opChar = expression[i]
                val isUnary = isUnaryOperator(i, expression)
                val op = SymbolRecognizer.getOperation(opChar, isUnary)

                handleIncomingOperator(result, op)

                ++i
                continue
            }

            val numberMatch = NumberPattern.find(expression, i)
            if (numberMatch != null
                && numberMatch.range.first == i
                && numberMatch.value.isNotEmpty()) {
                val numberNode = SyntaxNode.NumberNode(numberMatch.value.toBigInteger())
                result.add(numberNode)
                i = numberMatch.range.last + 1
                continue
            }

            val variableMatch = IdentifierPattern.find(expression, i)
            if (variableMatch != null
                && variableMatch.range.first == i
                && variableMatch.value.isNotEmpty()) {
                val varNode = SyntaxNode.VariableNode(variableMatch.value)
                result.add(varNode)

                i = variableMatch.range.last + 1
                continue
            }

            throw InvalidExpressionException("Invalid expression")
        }

        popAllOpFromStack()
        return result
    }

    private fun handleIncomingOperator(result: MutableList<SyntaxNode>, op: Operation) {
        if (stack.empty()) {
            stack.push(StackEntry.Operation(op))
            return
        }

        val incomingOpPriority = SymbolRecognizer.getOpPriority(op)

        do
        {
            val topEntry = stack.peek()
            if (topEntry is StackEntry.BracketNode) {
                break
            }
            val topOp = topEntry as StackEntry.Operation
            val topOpPriority = SymbolRecognizer.getOpPriority(topOp.op)

            if (incomingOpPriority > topOpPriority) {
                break
            }

            result.add(SyntaxNode.OperationNode(topOp.op))
            stack.pop()
        }
        while(stack.isNotEmpty())

        stack.push(StackEntry.Operation(op))
    }

    private fun isUnaryOperator(start: Int, s: String): Boolean {
        if (start == 0) {
            return true
        }

        return s[start - 1] == '('
    }
}