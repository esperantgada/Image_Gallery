package eg.esperantgada.imagegallery.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import eg.esperantgada.imagegallery.data.Photo
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.utils.STARTING_INDEX
import retrofit2.HttpException
import java.io.IOException


class SearchPhotoPagingSource(
    private val apiService: ApiService,
    private val searchQuery : String
    ) : PagingSource<Int, Photo>(){

    override suspend fun load(
        params: PagingSource.LoadParams<Int>
    ): PagingSource.LoadResult<Int, Photo> {

        val position = params.key ?: STARTING_INDEX

        //Gets the list of photos from the API and prepare this for the repository

        return try {
            val response = apiService.SearchImage(searchQuery)

            val photoList = response.photos!!.photo

            LoadResult.Page(
                data = photoList,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (photoList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return 0
    }

}
