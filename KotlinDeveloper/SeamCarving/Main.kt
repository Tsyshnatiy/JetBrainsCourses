package seamcarving

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun BufferedImage.getTransposed(): BufferedImage {
    val transposeImage = BufferedImage(this.height, this.width, this.type)
    repeat(transposeImage.width) { w ->
        repeat(transposeImage.height) { h ->
            transposeImage.setRGB(transposeImage.width - 1 - w,
                transposeImage.height - 1 - h,
                this.getRGB(h, w))
        }
    }
    return transposeImage
}

fun main(args: Array<String>) {
    val inFilename = args[args.indexOf("-in") + 1]
    val outFilename = args[args.indexOf("-out") + 1]

    val img = ImageIO.read(File(inFilename))!!
    val transposed = img.getTransposed()

    val energies = EnergyComputer(transposed).compute()
    if (energies.isEmpty() || energies[0].isEmpty()) {
        throw RuntimeException("Energies array cannot be empty for any real image")
    }

    val seam = VerticalSeamCalculator(energies).calculate()
    SeamDrawer(transposed).draw(seam)

    val result = transposed.getTransposed()
    ImageIO.write(result, "png", File(outFilename))
}
