package ipca.examples.dailynews

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.parseDate() : Date? {
    //2024-10-14T08:20:01Z
    val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    return SimpleDateFormat(pattern, Locale.getDefault())
        .parse(this)
}

fun Date.toYYYYMMDD() : String {
    val pattern = "yyyy-MM-dd"
    return SimpleDateFormat(pattern, Locale.getDefault())
        .format(this)
}