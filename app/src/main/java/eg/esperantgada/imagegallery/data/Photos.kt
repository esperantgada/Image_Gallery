package eg.esperantgada.imagegallery.data

import com.google.gson.annotations.SerializedName

data class Photos(

    @SerializedName("photo")
    val photo: List<Photo>

)