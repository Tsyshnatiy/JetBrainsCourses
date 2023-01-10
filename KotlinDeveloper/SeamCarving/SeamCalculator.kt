package seamcarving

data class Pixel(val x: Int, val y: Int)

typealias Seam = Array<Pixel>

class SeamCalculator(private val energies: Energies) {

    fun calculate() : Seam {
        val h = energies.size
        val w = energies[0].size

        val arrows = Array(h) { Array(w) { Pixel(0, 0) } }

        val cumulativeEnergies = Array(h) {
            val filler = DoubleArray(w)
            filler.fill(Double.POSITIVE_INFINITY)
            filler
        }
        cumulativeEnergies[cumulativeEnergies.lastIndex] = energies.last().clone()

        for (row in cumulativeEnergies.lastIndex - 2 downTo 0) {
            for (col in cumulativeEnergies[row].indices) {
                var cumEnergyValue = cumulativeEnergies[row][col]
                var arrow = Pixel(0, 0)
                if (col > 0) {
                    val left = cumulativeEnergies[row + 1][col - 1]
                    cumEnergyValue = cumEnergyValue.coerceAtMost(left)
                    arrow = Pixel(row + 1, col - 1)
                    arrows[row][col] = arrow
                }

                if (col < w - 1) {
                    val right = cumulativeEnergies[row + 1][col + 1]
                    cumEnergyValue = cumEnergyValue.coerceAtMost(right)
                    arrow = Pixel(row + 1, col + 1)
                    arrows[row][col] = arrow
                }


                val central = cumulativeEnergies[row + 1][col]
                cumEnergyValue = cumEnergyValue.coerceAtMost(central)

                if (cumEnergyValue.compareTo(central) == 0) {
                    arrow = Pixel(row, col)
                }

                cumulativeEnergies[row][col] = cumEnergyValue
                arrows[row][col] = arrow
            }
        }

        return computeSeam(cumulativeEnergies, arrows)
    }

    private fun computeSeam(cumEnergies: Energies, arrows: Array<Array<Pixel>>) : Seam {
        val result = Array(cumEnergies.size) { Pixel(0, 0) }
        val firstLineMin = cumEnergies[0].minOf { it }
        val firstLineMinIndex = cumEnergies[0].indexOfFirst { it.compareTo(firstLineMin) == 0 }

        result[0] = Pixel(0, firstLineMinIndex)

        val h = arrows.size
        val w = arrows[0].size

        for (row in 1 until h) {
            for (col in 0 until w) {
                result[row] = arrows[row][col]
            }
        }

        return result
    }
}