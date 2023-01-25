package gitinternals

import java.lang.StringBuilder
import java.text.ParseException

fun ByteArray.indexOf(b: Byte, start: Int): Int {
    for (i in start until this.size) {
        if (this[i] == b) {
            return i
        }
    }

    return -1
}

class TreeObjectParser: IProcessor {
    override fun process(bytes: ByteArray): String {
        val result = StringBuilder()

        var index = 0
        while (index < bytes.size) {
            val permissionsBytes = getPermissions(index, bytes)
            val permissions = String(permissionsBytes)

            index += permissionsBytes.size + 1 // +1 - skip space

            val filenameBytes = getFilename(index, bytes)
            val filename = String(filenameBytes)

            index += filenameBytes.size + 1 // +1 - skip zero byte itself

            val sha120Bytes = bytes.copyOfRange(index, index + 20)
            index += sha120Bytes.size

            result.append(permissions, " ", filename)
        }
        return result.toString()
    }

    private fun getPermissions(start: Int, bytes: ByteArray): ByteArray {
        val space = ' '.code.toByte()
        val nextSpaceIndex = bytes.indexOf(space, start)
        if (nextSpaceIndex == -1) {
            throw ParseException("Unable to detect permissions substring", start)
        }

        return bytes.copyOfRange(start, nextSpaceIndex)
    }

    private fun getFilename(start: Int, bytes: ByteArray): ByteArray {
        val zeroByte = 0.toByte()
        val nextZeroByte = bytes.indexOf(zeroByte, start)
        if (nextZeroByte == -1) {
            throw ParseException("Unable to detect filename substring", start)
        }

        return bytes.copyOfRange(start, nextZeroByte)
    }
}