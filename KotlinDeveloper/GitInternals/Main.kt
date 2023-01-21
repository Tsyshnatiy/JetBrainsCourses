package gitinternals

import java.io.File
import java.io.FileInputStream
import java.text.ParseException
import java.util.zip.InflaterInputStream

fun main() {
    println("Enter .git directory location:")
    //val pathToGit = "C:\\Users\\Vlad\\IdeaProjects\\Git Internals\\Git Internals\\task\\test\\gitone"//readln()
    val pathToGit = readln()

    println("Enter git object hash:")
    val hash = readln()

    val firstTwoDigits = hash.substring(0 until 2)
    val last38Digits = hash.substring(2)

    val path = pathToGit + File.separator +
                    "objects" + File.separator +
                    firstTwoDigits + File.separator +
                    last38Digits

    val fis = FileInputStream(path)
    val iis = InflaterInputStream(fis)
    val bytes = iis.readAllBytes()

    val zeroByte: Byte = 0
    val firstZeroByteIndex = bytes.indexOfFirst { it == zeroByte }
    if (firstZeroByteIndex == -1) {
        throw ParseException("Could not find zero byte in the object header", 0)
    }

    val usefulBytes = bytes.copyOfRange(0, firstZeroByteIndex)
    val typeAndLength = String(usefulBytes).split(' ')
    if (typeAndLength.size != 2) {
        throw RuntimeException("object type was not parsed successfully")
    }

    val type = typeAndLength[0]
    val length = typeAndLength[1]

    println("type:$type length:$length")
}
