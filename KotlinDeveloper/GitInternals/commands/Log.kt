package gitinternals.commands

import gitinternals.GitObjects
import gitinternals.branch.Branch
import gitinternals.commit.Commit
import java.lang.StringBuilder
import java.time.format.DateTimeFormatter

class Log(private val branches: List<Branch>,
            private val gitObjects: GitObjects,
            private val targetBranchName: String) {
    fun execute() {
        val targetBranch = branches.find { it.name == targetBranchName }
            ?: throw IllegalArgumentException("Could not find branch $targetBranchName")

        val commitsChain = getReversedCommitsChain(targetBranch.head)
        val result = StringBuilder()
        for(commit in commitsChain) {
            result.append(log(commit, true), System.lineSeparator())

            if (commit.parents.size == 2) {
                val foreignCommitHash = commit.parents[1]
                val foreignCommit = gitObjects.commits.objects[foreignCommitHash]
                result.append(log(foreignCommit!!, false), System.lineSeparator())
            }
        }

        println(result)
    }

    private fun getReversedCommitsChain(head: String) : List<Commit> {
        val result = mutableListOf<Commit>()
        var commit = gitObjects.commits.objects[head]
        while (commit != null) {
            result.add(commit)

            val nextHash = if (commit.parents.isEmpty()) null else commit.parents.first()
            if (nextHash == null) {
                break
            }

            commit = gitObjects.commits.objects[nextHash]
        }

        return result
    }

    private fun log(commit: Commit, fromCurrentBranch: Boolean): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val committerDateTime = formatter.format(commit.committerTimestamp.dateTime)

        val result = StringBuilder()

        result.append("Commit: ", commit.hash)
        if (!fromCurrentBranch) {
            result.append(" ", "(merged)")
        }
        result.append(System.lineSeparator())
        result.append(commit.committer.name, " ", commit.committer.email, " ")
        result.append("commit ", "timestamp: ", committerDateTime, " ", commit.committerTimestamp.timezone)
        result.append(System.lineSeparator())
        result.append(commit.message)
        return result.toString()
    }
}