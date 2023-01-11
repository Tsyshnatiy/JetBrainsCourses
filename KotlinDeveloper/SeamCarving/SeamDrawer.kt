package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage

class SeamDrawer(private val img: BufferedImage) {
    fun draw(seam: Seam) {
        for (p in seam) {
            img.setRGB(p.col, p.row, Color.RED.rgb)
        }
    }
}