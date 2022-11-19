package search

enum class PeopleSearchStrategy {
    ALL,
    ANY,
    NONE
}

interface IPeopleFinder {
    fun find(query: String): Set<Int>
}