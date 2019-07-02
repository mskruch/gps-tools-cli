package pl.mskruch.gpstools.processors

import gpstools.track.Lap
import javafx.geometry.Pos
import pl.mskruch.gps.Position
import pl.mskruch.gps.Positions
import pl.mskruch.gpstools.Execution

class Patch(private val fileName: String) : Processor {
    override fun process(execution: Execution) {
        val patch = FileInput(fileName).readTrack()
        patch.mergeLaps()

        val first = patch.firstPoint
        val last = patch.lastPoint


        val track = execution.track
        track.mergeLaps()

        val cutFrom = track.points.minBy { Positions.distance(it, first) }
        val cutTo = track.points.minBy { Positions.distance(it, last) }

        val points = track.points

        track.laps.clear()

        var lap = Lap()
        points.forEach {
            lap.add(it)
            if (it == cutFrom) {
                track.laps.add(lap)
                track.laps.add(patch.laps[0])
                lap = Lap() /* ignored */
            }
            if (it == cutTo) {
                lap = Lap()
                track.laps.add(lap)
            }
        }
    }
}