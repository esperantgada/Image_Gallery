package eg.esperantgada.imagegallery.data

data class Photo(
    val farm: Int = 0,
    val id: String? = null,
    val secret: String? = null,
    val server: String? = null,
    val title: String? = null,
    val url_s: String? = null,

){
    fun getImageUrl() : String = "https://farm$farm.staticflickr.com/$server/${id}_${secret}_m.jpg"
}