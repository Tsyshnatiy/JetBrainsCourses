package tasklist
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

class JsonSerializer {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val filename = "tasklist.json"
    private val listTask = Types.newParameterizedType(List::class.java, Task::class.java)
    private val jsonAdapter = moshi.adapter<List<Task>>(listTask)

    fun save(tasks: List<Task>) {
        val result = jsonAdapter.toJson(tasks)

        val file = File(filename)
        file.writeText(result)
    }

    fun load(): List<Task> {
        val file = File(filename)
        if (!file.exists()) {
            return listOf()
        }

        val json = file.readText()
        return jsonAdapter.fromJson(json) ?: return listOf()
    }
}