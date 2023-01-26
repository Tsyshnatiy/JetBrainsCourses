package gitinternals

import gitinternals.commit.Commit
import gitinternals.tree.Tree
import gitinternals.blob.Blob
import gitinternals.blob.BlobParser
import gitinternals.commit.CommitParser
import gitinternals.tree.TreeObjectParser
import java.io.File
import java.io.FileInputStream
import java.text.ParseException
import java.util.zip.InflaterInputStream

class ObjectReader(private val pathToGit: String) {
    fun read(): GitObjects {
        val blobs = mutableListOf<Blob>()
        val commits = mutableListOf<Commit>()
        val trees = mutableListOf<Tree>()

        val objectsDirPath = pathToGit + File.separator + "objects"
        val objectsDir = File(objectsDirPath)
        if (!objectsDir.exists() || !objectsDir.isDirectory) {
            throw IllegalStateException("Git repo does not contain objects")
        }

        val objectDirNames = objectsDir.list()
        if (objectDirNames == null || objectDirNames.isEmpty()) {
            throw IllegalStateException("Git repo does not contain objects")
        }

        // TODO Make parallel for
        for (objName in objectDirNames) {
            val singleObjectPath = objectsDirPath + File.separator + objName
            val singleObjectDir = File(objectsDirPath + File.separator + objName)
            val hashFiles = singleObjectDir.list()
            if (hashFiles == null || hashFiles.size != 1) {
                throw IllegalStateException("Could not parse object at $singleObjectDir")
            }

            val hashFile = hashFiles[0]
            val fis = FileInputStream(singleObjectPath + File.separator + hashFile)
            val iis = InflaterInputStream(fis)
            val bytes = iis.readAllBytes()

            fis.close()
            iis.close()

            val headerBytes = getHeader(bytes)
            val bodyBytes = getBody(bytes)

            val hash = objName + hashFile

            when(ObjectTypeParser().parse(headerBytes)) {
                Type.BLOB -> blobs.add(BlobParser().parse(bodyBytes, hash))
                Type.COMMIT -> commits.add(CommitParser().parse(bodyBytes, hash))
                Type.TREE -> trees.add(TreeObjectParser().parse(bodyBytes, hash))
            }
        }

        return GitObjects(trees, commits, blobs)
    }

    private fun getHeader(bytes: ByteArray) : ByteArray {
        val indexOfZeroByte = bytes.indexOf(0.toByte())
        if (indexOfZeroByte == -1) {
            throw ParseException("Unable to find type and length", 0)
        }
        return bytes.copyOfRange(0, indexOfZeroByte)
    }

    private fun getBody(bytes: ByteArray) : ByteArray {
        val indexOfZeroByte = bytes.indexOf(0.toByte())
        if (indexOfZeroByte == -1) {
            throw ParseException("Unable to find type and length", 0)
        }
        return bytes.copyOfRange(indexOfZeroByte + 1, bytes.size)
    }
}