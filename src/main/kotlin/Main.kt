import org.apache.commons.cli.ParseException
import pl.mskruch.gpstools.Application
import pl.mskruch.gpstools.ExecutionFailure
import pl.mskruch.gpstools.InvalidOptions

fun main(args: Array<String>) {
    val app = Application()

    try {
        app.execute(args)
    } catch (e: InvalidOptions) {
        println("${e.message}\n")
        app.showUsage()
    } catch (e: ParseException) {
        println("${e.message}\n")
        app.showUsage()
    } catch (e: ExecutionFailure){
        println("${e.message}\n")
    } catch (e: Exception) {
        println("Application error, sorry.")
        e.printStackTrace()
    }
}