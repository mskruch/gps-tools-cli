package pl.mskruch

import kotlin.Exception

class ParserFailure(e: Exception) : Exception(e) {
}