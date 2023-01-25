package gitinternals

class BlobBodyReader: IProcessor {
    override fun process(body: ByteArray): String {
        return String(body)
    }
}