package seamcarving

import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val inFilename = args[args.indexOf("-in") + 1] // "E:\\small-seam.png"
    val outFilename = args[args.indexOf("-out") + 1]

    val img = ImageIO.read(File(inFilename))!!

    val energies = EnergyComputer(img).compute()
    if (energies.isEmpty() || energies[0].isEmpty()) {
        throw RuntimeException("Energies array cannot be empty for any real image")
    }

    val seam = SeamCalculator(energies).calculate()
    SeamDrawer(img).draw(seam)

    ImageIO.write(img, "png", File(outFilename))
}
