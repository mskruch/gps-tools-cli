package pl.mskruch.gpstools

import kotlin.Exception

class ParserFailure(e: Exception) : Exception(e) {
}