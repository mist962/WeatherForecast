package com.sideki.weatherforecast.utils

import android.os.Message
import java.io.IOException

class Exceptions(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)