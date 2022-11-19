package search

typealias PeopleInvertedIndex = Map<String, Set<Int>>

fun buildInvertedIndex(people: List<String>) : PeopleInvertedIndex {
    val result = mutableMapOf<String, MutableSet<Int>>()

    for (i in people.indices) {
        val row = people[i]
        val words = row.split(' ')

        words.forEach {
            val word = it.lowercase()
            if (result.containsKey(word)) {
                result[word]?.add(i)
            }
            else {
                result[word] = mutableSetOf(i)
            }
        }
    }

    return result
}