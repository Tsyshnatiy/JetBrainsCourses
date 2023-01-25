package gitinternals.commit

import java.time.LocalDateTime

data class Commit(val author: Author,
                  val timestamp: LocalDateTime,
                  val message: String,
                  val tree: String,
                  val parents: List<String>)