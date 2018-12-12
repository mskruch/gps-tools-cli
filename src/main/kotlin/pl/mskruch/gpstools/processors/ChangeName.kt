package pl.mskruch.gpstools.processors

import pl.mskruch.gpstools.Execution

class ChangeName(val name: String) : Processor {
    override fun process(execution: Execution) {
        execution.track.name = name
    }
}