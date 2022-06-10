package eg.esperantgada.imagegallery.room.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photo_table")
data class PhotoItem(

    @NonNull
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: String ,

    @SerializedName("name")
    @ColumnInfo(name = "farm")
    val farm: Int = 0,

    @SerializedName("secret")
    val secret: String? = null,

    @SerializedName("server")
    val server: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url_s")
    val url_s: String? = null,

    ){

    fun getImageUrl() : String = "https://farm$farm.staticflickr.com/$server/${id}_${secret}_m.jpg"

}
