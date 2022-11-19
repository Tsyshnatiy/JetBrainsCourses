package search

object PeopleFinderFactory {
    fun create(strategy: PeopleSearchStrategy,
               index: PeopleInvertedIndex,
               linesCount: Int): IPeopleFinder {
        return when(strategy) {
            PeopleSearchStrategy.ALL -> AllFinder(index)
            PeopleSearchStrategy.ANY -> AnyFinder(index)
            PeopleSearchStrategy.NONE -> NoneFinder(linesCount, index)
        }
    }
}