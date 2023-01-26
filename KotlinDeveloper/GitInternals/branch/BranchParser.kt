package gitinternals.branch

import java.io.File
import java.io.FileNotFoundException
import java.text.ParseException

class BranchParser(private val pathToGit: String) {
    fun parse(): List<Branch> {
        val branchesAndHeads = getBranches()
        val currentBranch = getHeadBranch()
        return branchesAndHeads.map { Branch(it.first, it.second, it.first == currentBranch) }
    }

    private fun getBranches(): List<Pair<String, String>> {
        val branchHeadsPath = pathToGit + File.separator +
                "refs" + File.separator +
                "heads"
        val branchHeadsDir = File(branchHeadsPath)
        if (!branchHeadsDir.exists() || !branchHeadsDir.isDirectory) {
            throw FileNotFoundException("$branchHeadsPath not found")
        }

        val branchNames = branchHeadsDir.list()
        if (branchNames == null || branchNames.isEmpty()) {
            throw IllegalStateException("Git repo has no branches")
        }

        val sortedBranchNames = branchNames.sorted()
        val heads = sortedBranchNames.map {
            val branchFile = File(branchHeadsPath + File.separator + it)
            if (!branchFile.exists()) {
                throw IllegalStateException("Branch file $it does not exist")
            }

            branchFile.readText()
        }

        return sortedBranchNames.zip(heads)
    }

    private fun getHeadBranch(): String {
        val headPath = pathToGit + File.separator + "HEAD"

        val headFile = File(headPath)
        if (!headFile.exists() || !headFile.isFile) {
            throw FileNotFoundException("$headPath not found")
        }

        val headBranches = headFile.readLines()
        if (headBranches.size != 1) {
            throw ParseException("HEAD file format error", 0)
        }
        val headBranchParts = headBranches[0].split(' ')
        if (headBranchParts.size != 2) {
            throw ParseException("HEAD file format error", 0)
        }

        val branchPath = headBranchParts[1]

        val branchFile = File(pathToGit + File.separator + branchPath)
        if (!branchFile.exists() || !branchFile.isFile) {
            throw FileNotFoundException("$branchPath not found")
        }

        return branchFile.name
    }
}