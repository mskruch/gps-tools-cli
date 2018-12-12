package pl.mskruch.gpstools.options

import org.apache.commons.cli.Option
import pl.mskruch.gpstools.Execution

interface OptionDefinition {
    fun apply(execution: Execution, option: Option)
    val option: Option
}