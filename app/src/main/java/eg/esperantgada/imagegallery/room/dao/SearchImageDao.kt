package eg.esperantgada.imagegallery.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eg.esperantgada.imagegallery.room.entities.SearchItem

@Dao
interface SearchImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPhotos(photoList : List<SearchItem>)

    @Query("SELECT * FROM search_table")
    fun getAllPhotos() : PagingSource<Int, SearchItem>

    @Query("DELETE FROM search_table")
    fun clearAllPhotos()

}