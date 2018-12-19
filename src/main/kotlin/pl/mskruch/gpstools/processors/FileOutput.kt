package pl.mskruch.gpstools.processors

import engine
import gpstools.data.GpsDataExporter
import gpstools.data.GpxExporter
import gpstools.data.TcxExporter
import gpstools.util.FileName
import pl.mskruch.gpstools.Execution
import pl.mskruch.gpstools.ExecutionFailure
import pl.mskruch.gpstools.InvalidOptions
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class FileOutput(private val fileName: String) : Input {
    override fun process(execution: Execution) {
        val exporter = when (execution.format) {
            "gpx" -> GpxExporter()
            "tcx" -> TcxExporter()
            else -> throw InvalidOptions("Unsupported format: ${engine.format}")
        }

        val defaultExtension = exporter.defaultExtension()
        val name = FileName(fileName, defaultExtension).value

        try {
            print("Saving file $name... ")
            FileOutputStream(name).use {
                exporter.add(execution.track)
                execution.waypoints.forEach { waypoint ->
                    exporter.addWaypoint(waypoint, waypoint.label)
                }
                exporter.store(it)
                print("done.")
            }
        } catch (e: FileNotFoundException) {
            throw ExecutionFailure("Unable to save the file: $name")
        } catch (e: IOException) {
            throw ExecutionFailure("Unable to save the file: $name")
        } finally {
            println()
        }
    }
}