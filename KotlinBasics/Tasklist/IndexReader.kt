package tasklist

class IndexReader(private val maxIndex: Int) {
    fun read() : Int {
        var result: Int? = null
        while (result == null) {
            println("Input the task number (1-$maxIndex):")
            val inputNumber = readln().toIntOrNull()
            if (inputNumber == null || inputNumber < 1 || inputNumber > maxIndex) {
                println("Invalid task number")
                continue
            }

            result = inputNumber - 1
        }

        return result
    }
}