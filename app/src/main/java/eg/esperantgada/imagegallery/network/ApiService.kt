package eg.esperantgada.imagegallery.network

import eg.esperantgada.imagegallery.data.ApiPhotos
import eg.esperantgada.imagegallery.utils.API_KEY
import eg.esperantgada.imagegallery.utils.ENDPOINT
import eg.esperantgada.imagegallery.utils.FORMAT
import retrofit2.http.GET

interface ApiService {

    @GET("?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    suspend fun getImage() : ApiPhotos
}