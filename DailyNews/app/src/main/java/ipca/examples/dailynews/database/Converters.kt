package ipca.examples.dailynews.database

import androidx.room.TypeConverter
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {

    @TypeConverter
    fun fromString(value: String?): Date? {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        var result : Date? = null
        value?.let {
            result = SimpleDateFormat(pattern, Locale.getDefault()).parse(value)
        }
        return result
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        var result : String? = null
        date?.let {
            result = SimpleDateFormat(pattern, Locale.getDefault())
                .format(date)
        }
        return result

    }
}

fun Date.toYYYYMMDD() : String {
    val pattern = "yyyy-MM-dd"
    return SimpleDateFormat(pattern, Locale.getDefault())
        .format(this)
}