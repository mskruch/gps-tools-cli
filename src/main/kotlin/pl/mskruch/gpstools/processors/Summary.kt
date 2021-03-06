package pl.mskruch.gpstools.processors

import gpstools.cmd.SummaryJob
import pl.mskruch.gpstools.Execution

class Summary : Output {
    override fun process(execution: Execution) {
        val summaryJob = SummaryJob(execution.verbose)
        summaryJob.execute(execution.track)
    }
}