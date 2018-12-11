package pl.mskruch.gpstools.options

import org.apache.commons.cli.Option
import pl.mskruch.gpstools.Execution

interface CliOption {
    fun apply(execution: Execution)
    val option: Option
}