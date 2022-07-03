package eg.esperantgada.imagegallery.network

import eg.esperantgada.imagegallery.data.ApiPhotos
import eg.esperantgada.imagegallery.data.Photo
import eg.esperantgada.imagegallery.room.entities.PhotoItem
import eg.esperantgada.imagegallery.room.entities.SearchItem
import eg.esperantgada.imagegallery.utils.ENDPOINT
import eg.esperantgada.imagegallery.utils.ENDPOINT_PAGE3
import eg.esperantgada.imagegallery.utils.SEARCH_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ENDPOINT)
    suspend fun getImage() : ApiPhotos

    @GET(ENDPOINT_PAGE3)
    suspend fun getImageFromPage3() : ApiPhotos

    @GET(SEARCH_ENDPOINT)
    suspend fun searchImage(
        @Query("text")
        searchQuery: String) : ApiPhotos

}