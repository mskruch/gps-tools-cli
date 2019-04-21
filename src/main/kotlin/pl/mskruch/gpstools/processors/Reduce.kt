package pl.mskruch.gpstools.processors

import pl.mskruch.gpstools.Execution

class Reduce(private val epsilon: Double = 3.4) : Processor {
    override fun process(execution: Execution) {
        val track = execution.track

        track.summary().clear()

        track.laps.forEach { lap ->
            lap.simplify(epsilon)
        }
    }
}