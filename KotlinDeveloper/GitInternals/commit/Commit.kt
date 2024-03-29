package gitinternals.commit

import gitinternals.GitObject
import java.time.LocalDateTime

data class Timestamp(val dateTime: LocalDateTime,
                     val timezone: String)

data class Commit(val author: Author,
                  val committer: Author,
                  val authorTimestamp: Timestamp,
                  val committerTimestamp: Timestamp,
                  val message: String,
                  val treeHash: String,
                  val hash: String,
                  val parents: List<String>) : GitObject {
    override fun getObjectHash(): String {
        return hash
    }
}