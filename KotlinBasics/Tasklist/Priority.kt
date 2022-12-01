package tasklist

enum class Priority {
    CRITICAL,
    HIGH,
    NORMAL,
    LOW;

    override fun toString(): String {
        return when(this) {
            CRITICAL -> "C"
            HIGH -> "H"
            NORMAL -> "N"
            LOW -> "L"
        }
    }
}