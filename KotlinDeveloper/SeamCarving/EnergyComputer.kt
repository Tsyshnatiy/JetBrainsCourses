package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow
import kotlin.math.sqrt

typealias Energies = Array<DoubleArray>

class EnergyComputer(private val img: BufferedImage) {
    fun compute() : Energies {
        val result = Array(img.height) { DoubleArray(img.width)}

        for (row in 0 until img.height) {
            for (col in 0 until img.width) {
                val colWithOffset = if (col == 0) 1 else if (col == img.width - 1) col - 1 else col
                val rowWithOffset = if (row == 0) 1 else if (row == img.height - 1) row - 1 else row

                val rowM1 = Color(img.getRGB(col, rowWithOffset - 1))
                val rowP1 = Color(img.getRGB(col, rowWithOffset + 1))

                val colM1 = Color(img.getRGB(colWithOffset - 1, row))
                val colP1 = Color(img.getRGB(colWithOffset + 1, row))

                val energy = sqrt(squaredGradient(rowP1, rowM1) + squaredGradient(colP1, colM1))

                result[row][col] = energy
            }
        }

        return result
    }

    private fun squaredGradient(rgb1: Color, rgb2: Color): Double {
        return (rgb1.red - rgb2.red).toDouble().pow(2.0) +
                (rgb1.green - rgb2.green).toDouble().pow(2.0) +
                (rgb1.blue - rgb2.blue).toDouble().pow(2.0)
    }
}