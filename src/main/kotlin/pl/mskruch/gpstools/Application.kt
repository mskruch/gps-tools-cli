package pl.mskruch.gpstools

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import pl.mskruch.gpstools.options.CliOption
import pl.mskruch.gpstools.options.Verbose
import pl.mskruch.gpstools.workers.FileInput
import pl.mskruch.gpstools.workers.Summary

class Application {
    private val cliOptionsMap: Map<String, CliOption>
    private val options = Options()
    private val execution = Execution()

    constructor() {
        val cliOptions = listOf<CliOption>(
            Verbose()
        )

        cliOptionsMap = cliOptions.map { it.option.opt to it }.toMap()
        cliOptions.forEach { options.addOption(it.option) }
    }

    fun execute(args: Array<String>) {
        val line = DefaultParser().parse(options, args)

        includeArguments(line)
        includeOptions(line)
        includeSummary()

        execution.run()
    }

    private fun includeArguments(line: CommandLine) {
        if (line.args.isEmpty()) {
            throw InvalidOptions("No file specified.")
        }
        line.args.forEach {
            execution.inputs.add(FileInput(it))
        }
    }

    private fun includeOptions(line: CommandLine) {
        line.options.forEach {
            cliOptionsMap[it.opt]?.apply(execution);
        }
    }

    private fun includeSummary() {
        execution.outputs.add(Summary())
    }

    fun showUsage() {
        val helpFormatter = HelpFormatter()
        helpFormatter.printHelp("gps-tools-cli file [options]", options)
    }
}