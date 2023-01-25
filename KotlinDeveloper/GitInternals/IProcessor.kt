package gitinternals

interface IProcessor {
    fun process(bytes: ByteArray): String
}