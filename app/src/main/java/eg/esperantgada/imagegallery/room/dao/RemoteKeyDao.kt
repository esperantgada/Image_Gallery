package eg.esperantgada.imagegallery.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eg.esperantgada.imagegallery.room.entities.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(keys: List<RemoteKey?>?)

    @Query("SELECT * FROM keys_table WHERE key_id =:keyId")
    fun getRemoteKeyById(keyId : String) : RemoteKey

    @Query("DELETE  FROM keys_table")
    fun clearAllKeys()
}