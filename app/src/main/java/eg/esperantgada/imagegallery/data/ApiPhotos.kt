package eg.esperantgada.imagegallery.data

import com.google.gson.annotations.SerializedName

data class ApiPhotos(
    @SerializedName("photos")
    val photos: Photos,
)