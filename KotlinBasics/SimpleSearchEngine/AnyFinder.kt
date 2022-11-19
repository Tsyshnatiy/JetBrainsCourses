package search

class AnyFinder(private val index: PeopleInvertedIndex) : IPeopleFinder {
    override fun find(query: String): Set<Int> {
        val queryWords = query.split(' ').map { it.lowercase() }
        if (queryWords.isEmpty()) {
            return emptySet()
        }

        val result = mutableSetOf<Int>()

        for (queryWord in queryWords) {
            if (!index.containsKey(queryWord)) {
                continue
            }

            val lineNumbers = index[queryWord] ?: emptySet()
            result.addAll(lineNumbers)
        }

        return result
    }
}