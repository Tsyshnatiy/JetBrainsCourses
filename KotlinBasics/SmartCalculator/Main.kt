package calculator

fun main() {
    val varsStorage = VariableStorage()
    val arithmeticExprHandler = ArithmeticExpressionHandler(varsStorage)
    val variableExprHandler = VariableExpressionHandler(varsStorage, arithmeticExprHandler)
    val signSequenceNormalizer = SignSequenceNormalizer(variableExprHandler)
    val chain = InputValidator(signSequenceNormalizer)

    while (true) {
        val line = readln().trim().filter { !it.isWhitespace() }
        if (line == "/exit") {
            break
        }
        if (line == "/help") {
            println("The program calculates the sum of numbers")
            continue
        }

        if (line.startsWith("/")) {
            println("Unknown command")
            continue
        }

        if (line.isEmpty()) {
            continue
        }

        try {
            if (!chain.handle(line)) {
                println("Invalid expression")
            }
        }
        catch (e: IllegalArgumentException) {
            println(e.message)
        }
        catch (e: UnknownVariableException) {
            println("Unknown variable")
        }
        catch (e: InvalidExpressionException) {
            println("Invalid expression")
        }
        catch (e: InvalidIdentifierException) {
            println("Invalid identifier")
        }
        catch(e: InvalidAssignmentException) {
            println("Invalid assignment")
        }
    }

    println("Bye!")
}
