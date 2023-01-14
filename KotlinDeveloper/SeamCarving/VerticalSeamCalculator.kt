package seamcarving

data class Cell(val row: Int, val col: Int)

typealias Seam = HashSet<Cell>

class VerticalSeamCalculator(private val energies: Energies) {

    fun calculate() : Seam {
        val h = energies.size
        val w = energies[0].size

        val arrows = Array(h) { Array(w) { Cell(0, 0) } }
        for (j in arrows[0].indices) {
            arrows[0][j] = Cell(0, j)
        }

        val cumulativeEnergies = Array(h) { DoubleArray(w) }
        cumulativeEnergies[0] = energies.first()

        for (row in 1 until h) {
            for (col in 0 until w) {
                var cumEnergyValue = Double.POSITIVE_INFINITY

                val candidates = arrayOf(Cell(row - 1, col),
                                                    Cell(row - 1, col - 1),
                                                    Cell(row - 1, col + 1))

                for (c in candidates) {
                    if (c.col < 0 || c.col >= w) {
                        continue
                    }

                    val potentialCumEnergy = cumulativeEnergies[c.row][c.col] + energies[row][col]
                    if (cumEnergyValue > potentialCumEnergy) {
                        cumEnergyValue = potentialCumEnergy
                        arrows[row][col] = Cell(c.row, c.col)
                    }
                }

                cumulativeEnergies[row][col] = cumEnergyValue
            }
        }

        return computeSeam(cumulativeEnergies, arrows)
    }

    private fun computeSeam(cumEnergies: Energies, arrows: Array<Array<Cell>>) : Seam {
        val result = HashSet<Cell>()
        val lastLineMin = cumEnergies.last().reduce { a, b -> a.coerceAtMost(b) }
        val lastLineMinIndex = cumEnergies.last().indexOfFirst { it.compareTo(lastLineMin) == 0 }

        val h = cumEnergies.size
        result.add(Cell(h - 1, lastLineMinIndex))
        var lastArrow = result.last()

        for (row in h - 2 downTo 0) {
            val p = lastArrow

            val nextArrow = arrows[p.row][p.col]
            result.add(nextArrow)
            lastArrow = nextArrow
        }

        return result
    }
}