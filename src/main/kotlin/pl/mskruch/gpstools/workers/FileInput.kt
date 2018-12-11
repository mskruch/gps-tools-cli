package pl.mskruch.gpstools.workers

import pl.mskruch.gpstools.Execution

class FileInput(fileName: String) : Input {
    private val fileName = fileName

    override fun process(execution: Execution) {
        println("Reading file $fileName") // TODO
    }
}