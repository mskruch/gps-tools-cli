package pl.mskruch.gpstools.options

import org.apache.commons.cli.Option
import pl.mskruch.gpstools.Execution
import pl.mskruch.gpstools.processors.FileOutput

class FileOutputOption : OptionDefinition {
    override val option = Option("o", "out", true, "specify output file")

    override fun apply(execution: Execution, option: Option) {
        execution.processors.add(FileOutput(option.value))
    }
}