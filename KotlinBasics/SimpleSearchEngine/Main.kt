package search

import java.io.File

enum class Mode {
    FIND,
    PRINT,
    EXIT
}

fun printMenu() {
    println()
    println("=== Menu ===")
    println("1. Find a person")
    println("2. Print all people")
    println("0. Exit")
    println()
}

fun readWorkMode(): Mode? {
    return when(readln().toInt()) {
        0 -> Mode.EXIT
        1 -> Mode.FIND
        2 -> Mode.PRINT
        else -> null
    }
}

fun readSearchStrategy() : PeopleSearchStrategy? {
    return when(readln().uppercase()) {
        "ALL" -> PeopleSearchStrategy.ALL
        "ANY" -> PeopleSearchStrategy.ANY
        "NONE" -> PeopleSearchStrategy.NONE
        else -> null
    }
}

fun main(args: Array<String>) {
    if (!args.contains("--data")) {
        println("Data argument was not provided")
        return
    }

    val lines = File(args.last()).readLines()
    val index = buildInvertedIndex(lines)

    while (true) {
        printMenu()
        val mode = readWorkMode()
        if (mode == null) {
            println("Incorrect option! Try again.")
            println()
            continue
        }

        when(mode) {
            Mode.PRINT -> lines.forEach { println(it) }
            Mode.FIND -> {
                println("Select a matching strategy: ALL, ANY, NONE")
                val strategy = readSearchStrategy()
                if (strategy == null) {
                    println("Wrong strategy")
                    continue
                }

                println("Enter a name or email to search all matching people.")
                val query = readln().lowercase()

                val finder = PeopleFinderFactory.create(strategy, index, lines.size)
                val matchedLinesNumbers = finder.find(query)
                if (matchedLinesNumbers.isEmpty()) {
                    println("No people found")
                    continue
                }

                println("${matchedLinesNumbers.size} persons found:")
                matchedLinesNumbers.forEach { println(lines[it]) }
            }
            Mode.EXIT -> break
        }
    }

    println("Bye!")
}
