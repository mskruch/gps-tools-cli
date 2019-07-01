package pl.mskruch.gpstools.processors

import gpstools.track.Lap
import pl.mskruch.gps.Position
import pl.mskruch.gps.Positions
import pl.mskruch.gpstools.Execution

class ChangeStart(private val pointString: String) : Processor {
    override fun process(execution: Execution) {
        val start = Position.from(pointString)

        val track = execution.track
        track.mergeLaps()

        val points = track.points
        val closestPointToStartIndex =
            points.mapIndexed { index, trackPoint ->
                Pair(index, Positions.distance(start, trackPoint))
            }.minBy { it.second }?.first

        track.laps.clear()
        val afterStart  = Lap()
        val beforeStart  = Lap()

        closestPointToStartIndex?.let {
            points.forEachIndexed { index, trackPoint ->
                if (index < it)
                    beforeStart.add(trackPoint)
                else
                    afterStart.add(trackPoint)
            }
        }
        track.laps.add(afterStart)
        track.laps.add(beforeStart)
    }
}