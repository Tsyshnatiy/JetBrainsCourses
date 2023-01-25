package gitinternals.blob

class BlobParser {
    fun parse(body: ByteArray): Blob {
        return Blob(String(body))
    }
}