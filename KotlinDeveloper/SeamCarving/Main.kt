package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun BufferedImage.getTransposed(): BufferedImage {
    val transposeImage = BufferedImage(this.height, this.width, this.type)
    repeat(transposeImage.width) { w ->
        repeat(transposeImage.height) { h ->
            transposeImage.setRGB(w, h, this.getRGB(h, w))
        }
    }
    return transposeImage
}

fun findVerticalSeam(image: BufferedImage): Seam {
    val energies = EnergyComputer(image).compute()
    if (energies.isEmpty() || energies[0].isEmpty()) {
        throw RuntimeException("Energies array cannot be empty for any real image")
    }

    return VerticalSeamCalculator(energies).calculate()
}

fun findHorizontalSeam(image: BufferedImage): Seam {
    val transposed = image.getTransposed()
    return findVerticalSeam(transposed)
}

fun removeVerticalSeam(seam: Seam, image: BufferedImage): BufferedImage{
    if (seam.isEmpty()) {
        return image
    }

    val result = BufferedImage(image.width - 1, image.height, image.type)
    var cursor = Cell(0, 0)
    for (h in 0 until image.height) {
        for (w in 0 until image.width) {
            val imageCell = Cell(h, w)
            if (seam.contains(imageCell)) {
                continue
            }

            val rgb = Color(image.getRGB(w, h)).rgb
            result.setRGB(cursor.col, cursor.row, rgb)
            cursor = Cell(cursor.row, cursor.col + 1)
        }

        cursor = Cell(cursor.row + 1, 0)
    }

    return result
}

fun removeHorizontalSeam(seam: Seam, image: BufferedImage): BufferedImage{
    if (seam.isEmpty()) {
        return image
    }

    val result = BufferedImage(image.width, image.height - 1, image.type)
    var cursor = Cell(0, 0)
    for (w in 0 until image.width) {
        for (h in 0 until image.height) {
            val imageCell = Cell(w, h) // hor seam is transposed
            if (seam.contains(imageCell)) {
                continue
            }

            val rgb = Color(image.getRGB(w, h)).rgb
            result.setRGB(cursor.col, cursor.row, rgb)
            cursor = Cell(cursor.row + 1, cursor.col)
        }

        cursor = Cell(0, cursor.col + 1)
    }

    return result
}

fun main(args: Array<String>) {
    val inFilename = args[args.indexOf("-in") + 1]
    val outFilename = args[args.indexOf("-out") + 1]
    val vertSeamsToRemove = args[args.indexOf("-width") + 1].toInt()
    val horSeamsToRemove = args[args.indexOf("-height") + 1].toInt()

    var img = ImageIO.read(File(inFilename))!!

    repeat(vertSeamsToRemove) {
        val vertSeam = findVerticalSeam(img)
        img = removeVerticalSeam(vertSeam, img)
    }

    repeat(horSeamsToRemove) {
        val horSeamTransposed = findHorizontalSeam(img)
        img = removeHorizontalSeam(horSeamTransposed, img)
    }

    ImageIO.write(img, "png", File(outFilename))
}
