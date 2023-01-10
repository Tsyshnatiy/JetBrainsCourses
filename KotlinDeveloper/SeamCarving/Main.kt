package seamcarving

import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val img = ImageIO.read(File(args[args.indexOf("-in") + 1]))!!

    val energies = EnergyComputer(img).compute()
    if (energies.isEmpty() || energies[0].isEmpty()) {
        throw RuntimeException("Energies array cannot be empty for any real image")
    }

    val seam = SeamCalculator(energies).calculate()
    SeamDrawer(img).draw(seam)

    ImageIO.write(img, "png", File(args[args.indexOf("-out") + 1]))
}
