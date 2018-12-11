package pl.mskruch.gpstools.workers

import pl.mskruch.gpstools.Execution

interface Processor {
    fun process(execution: Execution)
}