package pl.mskruch.gpstools.options

import org.apache.commons.cli.Option
import pl.mskruch.gpstools.Execution
import pl.mskruch.gpstools.processors.ChangeName

class ChangeNameOption : OptionDefinition {
    override val option = Option("n", "name", true, "set the track name")

    override fun apply(execution: Execution, option: Option) {
        execution.processors.add(ChangeName(option.value))
    }
}