package eg.esperantgada.imagegallery.utils

const val BASE_URL = "https://api.flickr.com/services/rest/"
const val ENDPOINT = "?method=flickr.photos.getRecent&per_page=20&page=2,3&api_key=6f102c62f41" +
        "998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s"

const val ENDPOINT_PAGE3 = "?method=flickr.photos.getRecent&per_page=20&page=3&api_key=6f102c62f41" +
        "998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s"


const val SEARCH_ENDPOINT = "?method=flickr.photos.search&api_key=6f102c62f41998d151e5a1b48" +
        "713cf13&format=json&nojsoncallback=1&extras=url_s&text=cat"


const val STARTING_INDEX = 1



