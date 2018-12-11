package pl.mskruch.gpstools.workers

import pl.mskruch.gpstools.Execution

class Summary : Output {
    override fun process(execution: Execution) {
        println("Summary, verbose: ${execution.verbose}") // TODO
    }
}