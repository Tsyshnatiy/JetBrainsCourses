package calculator

import java.math.BigInteger

typealias VariableStorage = HashMap<String, BigInteger>

class VariableExpressionHandler(private val variableStorage: VariableStorage,
                                private val next: IExpressionHandler?) : IExpressionHandler{

    override fun handle(input: String): Boolean {
        if (input.indexOf('=') == -1) {
            return next?.handle(input) ?: false
        }

        checkInput(input)

        if (isNumberToVariableAssignment(input)) {
            handleNumberToVariableAssignment(input)
            return true
        }

        if (isVariableToVariableAssignment(input)) {
            handleVariableToVariableAssignment(input)
            return true
        }

        return next?.handle(input) ?: false
    }

    private fun checkInput(input: String) {
        val parts = input.split('=')
        if (parts.size == 1) {
            val isOK = checkVariable(parts[0])
            if (isOK) {
                return
            }

            throw InvalidIdentifierException("${parts[0]} is invalid identifier")
        }

        if (parts.size != 2) {
            throw InvalidExpressionException("$input is invalid expression")
        }

        val lhs = parts[0]
        val rhs = parts[1]

        if (!checkVariable(lhs)) {
            throw InvalidIdentifierException("$lhs is invalid identifier")
        }

        if (!checkVariable(rhs) && !checkNumber(rhs)) {
            throw InvalidAssignmentException("$input is invalid identifier")
        }
    }

    private fun checkVariable(input: String) : Boolean {
        return IdentifierPattern.matchEntire(input) != null
    }

    private fun checkNumber(input: String): Boolean {
        return NumberPattern.matchEntire(input) != null
    }


    private fun isNumberToVariableAssignment(input: String): Boolean {
        val pattern = "($IdentifierPattern)=($NumberPattern)".toRegex()
        return pattern.matchEntire(input) != null
    }

    private fun isVariableToVariableAssignment(input: String): Boolean {
        val pattern = "($IdentifierPattern)=($IdentifierPattern)".toRegex()
        return pattern.matchEntire(input) != null
    }

    private fun handleNumberToVariableAssignment(input: String) {
        val parts = input.split('=')
        if (parts.size != 2) {
            throw IllegalArgumentException("Number to variable split had more than 2 parts")
        }
        val variable = parts[0]
        val number = parts[1].toBigInteger()

        variableStorage[variable] = number
    }

    private fun handleVariableToVariableAssignment(input: String) {
        val parts = input.split('=')
        if (parts.size != 2) {
            throw IllegalArgumentException("Number to variable split had more than 2 parts")
        }
        val lhs = parts[0]
        val number = variableStorage[parts[1]]
        if (number == null) {
            println("Unknown variable")
            return
        }

        variableStorage[lhs] = number
    }
}