package pl.mskruch.gpstools.processors

import gpstools.Points
import pl.mskruch.gpstools.Execution

class ReduceByDistance(private val distanceInMeters: Int) : Processor {
    override fun process(execution: Execution) {
        val track = execution.track

        track.summary().clear()

        var previous = track.firstPoint

        track.laps.forEach { lap ->
            val iterator = lap.points.iterator()
            while (iterator.hasNext()) {
                val point = iterator.next()

                val first = point == track.firstPoint
                val last = point == track.lastPoint
                val inRange = Points.distance(previous, point) < distanceInMeters

                if (first || last || !inRange) {
                    previous = point
                } else {
                    iterator.remove()
                }
            }
        }
    }
}