package gitinternals

import java.io.FileInputStream
import java.text.ParseException
import java.util.zip.InflaterInputStream

class ProcessingChain(private val typeParser: TypeParser,
                      private val commitProcessors: List<IProcessor>,
                      private val blobProcessors: List<IProcessor>,
                      private val treeProcessors: List<IProcessor>) {
    fun process(path: String): String {
        val result = StringBuilder()

        val fis = FileInputStream(path)
        val iis = InflaterInputStream(fis)
        val bytes = iis.readAllBytes()

        val indexOfZeroByte = bytes.indexOf(0.toByte())
        if (indexOfZeroByte == -1) {
            throw ParseException("Unable to find type and length", 0)
        }
        val headerBytes = bytes.copyOfRange(0, indexOfZeroByte)
        val bodyBytes = bytes.copyOfRange(indexOfZeroByte + 1, bytes.size)

        val type = typeParser.process(listOf(String(headerBytes)))
        result.append(type.parsed)
        result.append(System.lineSeparator())

        val processors = when(type.type) {
            Type.BLOB -> blobProcessors
            Type.COMMIT -> commitProcessors
            Type.TREE -> treeProcessors
        }

        for (p in processors) {
            val s = p.process(bodyBytes)
            result.append(s)
        }

        return result.toString()
    }
}