package search

class NoneFinder(private val linesCount: Int, private val index: PeopleInvertedIndex) : IPeopleFinder {
    override fun find(query: String): Set<Int> {
        val anyFinder = AnyFinder(index)
        val anyFound = anyFinder.find(query)

        val result = mutableSetOf<Int>()
        (0 until linesCount).forEach {
            if (!anyFound.contains(it)) {
                result.add(it)
            }
        }

        return result
    }
}