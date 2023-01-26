package gitinternals.commands

import gitinternals.branch.Branch
import java.lang.StringBuilder

class ListBranches {
    fun list(branches: List<Branch>) {
        val result = StringBuilder()

        for (branch in branches) {
            if (branch.isCurrent) {
                result.append("* ")
            }
            else {
                result.append("  ")
            }

            result.append(branch.name, System.lineSeparator())
        }

        println(result)
    }
}