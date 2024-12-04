package ipca.examples.dailynews.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import ipca.examples.dailynews.encodeURL
import ipca.examples.dailynews.parseDate
import ipca.examples.dailynews.toServerDate
import org.json.JSONObject
import java.util.Date

@Entity
class Article (var title: String? = null,
               var description: String? = null ,
               var urlToImage: String? = null,
               @PrimaryKey
               var url: String,
               var publishedAt: Date? = null) {

    companion object {
        fun fromJson(json: JSONObject): Article {
            return Article(
                title       = json.getString("title"        ),
                description = json.getString("description"  ),
                urlToImage  = json.getString("urlToImage"   ),
                url         = json.getString("url"          )?:"no url",
                publishedAt = json.getString("publishedAt"  ).parseDate())
        }
    }

    fun toJsonString() : String {
        val jsonObject = JSONObject()
        jsonObject.put("title"       , title                        )
        jsonObject.put("description" , description                  )
        jsonObject.put("urlToImage"  , urlToImage?.encodeURL()      )
        jsonObject.put("url"         , url?.encodeURL()             )
        jsonObject.put("publishedAt" , publishedAt?.toServerDate()  )
        return jsonObject.toString()
    }
}

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Query("SELECT * FROM article WHERE url = :url")
    fun loadByUrl(url: String): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( article: Article)

    @Delete
    fun delete(article: Article)
}

