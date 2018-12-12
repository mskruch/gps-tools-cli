package pl.mskruch.gpstools.options

import org.apache.commons.cli.Option
import pl.mskruch.gpstools.Execution

class Verbose : OptionDefinition {
    override val option = Option("v", "verbose", false, "verbose summary")

    override fun apply(execution: Execution) {
        execution.verbose = true
    }
}