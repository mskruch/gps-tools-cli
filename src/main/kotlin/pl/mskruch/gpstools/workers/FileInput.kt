package pl.mskruch.gpstools.workers

import gpstools.data2.UnsupportedFormat
import gpstools.track.Track
import pl.mskruch.gpstools.Execution
import pl.mskruch.gpstools.ExecutionFailure
import java.io.IOException

class FileInput(fileName: String) : Input {
    private val fileName = fileName

    override fun process(execution: Execution) {
        try {
            print("Reading $fileName... ")
            readFile(execution)
            print("done.")
            return
        } catch (e: UnsupportedFormat) {
            throw ExecutionFailure("Unsupported file format: $fileName")
        } catch (e: IOException) {
            throw ExecutionFailure("Unable to read the file: $fileName")
        } finally {
            println()
        }
    }

    private fun readFile(execution: Execution) {
        val read = Track.from(fileName)
        execution.track.add(read)
    }
}