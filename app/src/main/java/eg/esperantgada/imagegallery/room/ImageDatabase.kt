package eg.esperantgada.imagegallery.room

import androidx.room.Database
import androidx.room.RoomDatabase
import eg.esperantgada.imagegallery.room.dao.ImageDao
import eg.esperantgada.imagegallery.room.dao.RemoteKeyDao
import eg.esperantgada.imagegallery.room.dao.SearchImageDao
import eg.esperantgada.imagegallery.room.entities.PhotoItem
import eg.esperantgada.imagegallery.room.entities.RemoteKey
import eg.esperantgada.imagegallery.room.entities.SearchItem

@Database(entities = [PhotoItem::class, SearchItem::class, RemoteKey::class], version = 2, exportSchema = false)
abstract class ImageDatabase : RoomDatabase(){

    abstract fun getImageDao() : ImageDao

    abstract fun getSearchPhotoDao() : SearchImageDao

    abstract fun getRemoteKeyDao() : RemoteKeyDao
}