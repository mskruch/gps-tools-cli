import gpstools.cmd.*
import org.apache.commons.cli.*
import pl.mskruch.gpstools.ParserFailure

val options = prepareOptions()
val engine = Engine()

fun main(args: Array<String>) {
    try {
        run(args)
    } catch (e: ParserFailure) {
        println("${e.cause?.message}\n")
        printUsage()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun run(args: Array<String>) {
    if (args.isEmpty()) {
        printUsage()
    } else {
        val line = parse(args, options)

        for (argument in line.args) {
            engine.add(ReadFileJob(argument))
        }

        engine.add(SummaryJob(line.hasOption("summary")))

        for (option in line.options) {
            when {
                "out" == option.opt -> engine.add(WriteFileJob(option.value))
                "gpx" == option.opt -> engine.format = "gpx"
                "tcx" == option.opt -> engine.format = "tcx"
                "lap" == option.opt -> {
                    val value = option.value
                    val index = Integer.valueOf(value) - 1
                    engine.add(RetainLapJob(index))
                }
                "name" == option.opt -> {
                    val value = option.value
                    engine.add(ChangeNameJob(value))
                }
                "rbd" == option.opt -> {
                    val value = option.value
                    val distanceInMeters = Integer.valueOf(value)
                    engine.add(ReduceByDistanceJob(distanceInMeters))
                }
                "onelap" == option.opt -> engine.add(OneLapJob())
                "sort" == option.opt -> engine.add(SortLapsJob())
                "daytag" == option.opt -> engine.add(DayTagJob())
                "reverse" == option.opt -> engine.add(ReverseJob())
                "staytag" == option.opt -> {
                    val value = option.value
                    val minutes = Integer.valueOf(value)
                    engine.add(StayTagJob(minutes * 60))
                }
                "split" == option.opt -> {
                    val value = option.value
                    val max = Integer.valueOf(value)
                    engine.add(WriteSplitFilesJob(max))
                }
            }
        }

        engine.execute()
    }
}

fun parse(args: Array<String>, options: Options): CommandLine {
    try {
        return DefaultParser().parse(options, args)
    } catch (e: Exception) {
        throw ParserFailure(e)
    }
}

private fun printUsage() {
    val helpFormatter = HelpFormatter()
    helpFormatter.printHelp("gps-tools-cli file [options]", options)
}

private fun prepareOptions(): Options {
    val options = Options()

    options.addOption(
        Option.builder("lap")
            .hasArg(true)
            .argName("number")
            .desc("retain only the selected lap")
            .build()
    )
    options.addOption("tcx", false, "saves as tcx file")
    options.addOption("gpx", false, "saves as gpx file")
    options.addOption("out", true, "specify output file")
    options.addOption("name", true, "change the track name")
    options.addOption("rbd", true, "reduce points by distance")
    options.addOption("summary", false, "verbose summary")
    options.addOption("onelap", false, "merge all the laps into one")
    options.addOption("sort", false, "sort laps by time")
    options.addOption(
        "daytag",
        false,
        "add a tag (waypoint) for each day started"
    )
    options.addOption(
        "staytag",
        true,
        "add a tag (waypoint) for each stay longer than arg minutes"
    )
    options.addOption("reverse", false, "reverse the track")
    options.addOption(
        "split",
        true,
        "split to separate files with maximum number of points in each one"
    )

    return options
}