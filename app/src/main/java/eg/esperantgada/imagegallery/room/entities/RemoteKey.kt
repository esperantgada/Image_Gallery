package eg.esperantgada.imagegallery.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys_table")
data class RemoteKey(
    @ColumnInfo(name = "key_id")
    @PrimaryKey(autoGenerate = false)
    val id : String,

    @ColumnInfo(name = "prev_key")
    val prevKey : Int?,

    @ColumnInfo(name = "next_key")
    val nextKey : Int?
)
