package gitinternals.blob

class BlobParser {
    fun parse(body: ByteArray, hash: String): Blob {
        return Blob(String(body), hash)
    }
}