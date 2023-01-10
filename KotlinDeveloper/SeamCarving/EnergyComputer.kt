package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow
import kotlin.math.sqrt

typealias Energies = Array<DoubleArray>

class EnergyComputer(private val img: BufferedImage) {
    fun compute() : Energies {
        val result = Array(img.height) { DoubleArray(img.width)}

        for (imgY in 0 until img.height) {
            for (imgX in 0 until img.width) {
                val xWithOffset = if (imgX == 0) 1 else if (imgX == img.width - 1) imgX - 1 else imgX
                val yWithOffset = if (imgY == 0) 1 else if (imgY == img.height - 1) imgY - 1 else imgY

                val xM1 = Color(img.getRGB(xWithOffset - 1, imgY))
                val xP1 = Color(img.getRGB(xWithOffset + 1, imgY))

                val yM1 = Color(img.getRGB(imgX, yWithOffset - 1))
                val yP1 = Color(img.getRGB(imgX, yWithOffset + 1))

                val energy = sqrt(squaredGradient(xP1, xM1) + squaredGradient(yP1, yM1))

                val arrayX = imgY
                val arrayY = imgX

                result[arrayX][arrayY] = energy
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