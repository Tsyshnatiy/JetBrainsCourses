package gitinternals.commands.serializers

import gitinternals.blob.Blob
import java.lang.StringBuilder

class BlobSerializer(private val blob: Blob) {
    fun serialize(): String {
        val result = StringBuilder()
        result.append("*BLOB*", System.lineSeparator(), blob.body)
        return result.toString()
    }
}