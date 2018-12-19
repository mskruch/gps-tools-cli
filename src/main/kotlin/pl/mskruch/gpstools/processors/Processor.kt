package pl.mskruch.gpstools.processors

import pl.mskruch.gpstools.Execution

interface Processor {
    fun process(execution: Execution)
}

interface Input : Processor

interface Output : Processor