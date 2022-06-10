package eg.esperantgada.imagegallery.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eg.esperantgada.imagegallery.room.entities.PhotoItem

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photoList : List<PhotoItem>)

    @Query("SELECT * FROM photo_table")
    fun getAllPhotos() : PagingSource<Int, PhotoItem>

    @Query("DELETE FROM photo_table")
    fun clearAllPhotos()
}