package search

class AllFinder(private val index: PeopleInvertedIndex) : IPeopleFinder {
    override fun find(query: String): Set<Int> {
        val queryWords = query.split(' ').map { it.lowercase() }
        if (queryWords.isEmpty()) {
            return emptySet()
        }

        var result = index[queryWords[0]] ?: emptySet()

        for (i in 1 until queryWords.size) {
            if (result.isEmpty()) {
                return emptySet()
            }

            val queryWord = queryWords[i]
            if (!index.containsKey(queryWord)) {
                return emptySet()
            }

            val wordLines = index[queryWord] ?: return emptySet()
            result = result.intersect(wordLines)
        }

        return result
    }
}