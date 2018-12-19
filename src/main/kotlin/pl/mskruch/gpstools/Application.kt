package pl.mskruch.gpstools

import org.apache.commons.cli.*
import pl.mskruch.gpstools.processors.ChangeName
import pl.mskruch.gpstools.processors.FileInput
import pl.mskruch.gpstools.processors.FileOutput
import pl.mskruch.gpstools.processors.Summary

class OptionProcessor(val option: Option, val process: (Option) -> Unit)

class Application {
    private val execution = Execution()

    private val options = Options()
    private val optionsMapping: Map<String, OptionProcessor>

    constructor() {
        val list = listOf(
            OptionProcessor(Option("n", "name", true, "set the track name")) {
                execution.processors.add(ChangeName(it.value))
            },
            OptionProcessor(Option("o", "out", true, "specify output file")) {
                execution.processors.add(FileOutput(it.value))
            },
            OptionProcessor(Option("v", "verbose", false, "verbose summary")) {
                execution.verbose = true
            }
        )
        optionsMapping = list.map { it.option.opt to it }.toMap()
        list.forEach { options.addOption(it.option) }
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
            optionsMapping[it.opt]?.process?.invoke(it)
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