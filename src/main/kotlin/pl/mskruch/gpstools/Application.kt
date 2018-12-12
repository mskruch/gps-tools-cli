package pl.mskruch.gpstools

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import pl.mskruch.gpstools.options.ChangeNameOption
import pl.mskruch.gpstools.options.OptionDefinition
import pl.mskruch.gpstools.options.VerboseOption
import pl.mskruch.gpstools.processors.FileInput
import pl.mskruch.gpstools.processors.Summary

class Application {
    private val optionDefinitionsMap: Map<String, OptionDefinition>
    private val options = Options()
    private val execution = Execution()

    constructor() {
        val optionDefinitions = listOf<OptionDefinition>(
            VerboseOption(), ChangeNameOption()
        )

        optionDefinitionsMap = optionDefinitions.map { it.option.opt to it }.toMap()
        optionDefinitions.forEach { options.addOption(it.option) }
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
            optionDefinitionsMap[it.opt]?.apply(execution, it);
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