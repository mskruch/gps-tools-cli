package pl.mskruch.gpstools.processors

import gpstools.data2.UnsupportedFormat
import gpstools.track.Track
import pl.mskruch.gpstools.Execution
import pl.mskruch.gpstools.ExecutionFailure
import java.io.IOException

class FileInput(private val fileName: String) : Input {

    override fun process(execution: Execution) {
        execution.track.add(readTrack())
    }

    fun readTrack(): Track {
        try {
            print("Reading $fileName... ")
            return Track.from(fileName)
        } catch (e: UnsupportedFormat) {
            throw ExecutionFailure("Unsupported file format: $fileName")
        } catch (e: IOException) {
            throw ExecutionFailure("Unable to read the file: $fileName")
        } finally {
            println("done.")
        }
    }
}