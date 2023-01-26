package gitinternals.blob

import gitinternals.GitObject

data class Blob(val body: String, val hash: String) : GitObject {
    override fun getObjectHash(): String {
        return hash
    }
}