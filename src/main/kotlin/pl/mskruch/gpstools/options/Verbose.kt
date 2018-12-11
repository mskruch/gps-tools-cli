package pl.mskruch.gpstools.options

import org.apache.commons.cli.Option
import pl.mskruch.gpstools.Execution

class Verbose : CliOption {
    override val option = Option("v", "verbose", false, "verbose summary")

    override fun apply(execution: Execution) {
        execution.verbose = true
    }
}