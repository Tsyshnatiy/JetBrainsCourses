package gitinternals

import java.io.File
import java.io.FileNotFoundException
import java.lang.StringBuilder
import java.text.ParseException

class ListBranches(private val pathToGit: String) {
    fun list(): String {
        val branches = getBranches()
        if (branches.isEmpty()) {
            throw ParseException("Could not parse branches files", 0)
        }
        val headBranchName = getHeadBranch()

        val result = StringBuilder()

        for (branch in branches) {
            if (branch == headBranchName) {
                result.append("* ")
            }
            else {
                result.append("  ")
            }

            result.append(branch, System.lineSeparator())
        }

        return result.toString()
    }

    private fun getBranches(): List<String> {
        val branchHeadsPath = pathToGit + File.separator +
                "refs" +  File.separator +
                "heads"
        val branchHeadsDir = File(branchHeadsPath)
        if (!branchHeadsDir.exists() || !branchHeadsDir.isDirectory) {
            throw FileNotFoundException("$branchHeadsPath not found")
        }

        return branchHeadsDir.list()?.sorted() ?: emptyList()
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