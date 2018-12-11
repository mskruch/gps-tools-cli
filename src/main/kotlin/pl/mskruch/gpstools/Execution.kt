package pl.mskruch.gpstools

import gpstools.Waypoint
import gpstools.track.Track
import pl.mskruch.gpstools.workers.Input
import pl.mskruch.gpstools.workers.Output
import pl.mskruch.gpstools.workers.Processor

class Execution() {
    internal var track = Track()
    internal var waypoints: MutableList<Waypoint> = mutableListOf()

    val outputs: MutableList<Output> = mutableListOf()
    val inputs: MutableList<Input> = mutableListOf()
    val processors: MutableList<Processor> = mutableListOf()

    var format = "gpx"
    var verbose = false

    fun run() {
        inputs.forEach {
            it.process(this)
        }

        println()

        processors.forEach {
            it.process(this)
        }

        outputs.forEach {
            it.process(this)
        }
    }
}