package eg.esperantgada.imagegallery.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import eg.esperantgada.imagegallery.R
import eg.esperantgada.imagegallery.adapter.FlickrImageLoadStateAdapter
import eg.esperantgada.imagegallery.adapter.SearchPhotoAdapter
import eg.esperantgada.imagegallery.application.ImageGalleryApplication
import eg.esperantgada.imagegallery.databinding.FragmentSearchPhotoBinding
import eg.esperantgada.imagegallery.utils.getQueryTextChangedStateFlow
import eg.esperantgada.imagegallery.utils.isInternetAvailable
import eg.esperantgada.imagegallery.viewmodel.SearchImageViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SearchPhotoFragment : Fragment() {

    private val viewModel : SearchImageViewModel by viewModels()

    private var _binding : FragmentSearchPhotoBinding? = null
    private val binding get() = _binding!!

    /**
     * We pass the fragment itself in the adapter after setting OnClickListener on it
     */
    private val searchPhotoAdapter = SearchPhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Handles [header] and [footer] behavior in the recyclerView when loading images
         */
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = searchPhotoAdapter.withLoadStateHeaderAndFooter(
                header = FlickrImageLoadStateAdapter{searchPhotoAdapter.retry()},
                footer = FlickrImageLoadStateAdapter{searchPhotoAdapter.retry()}
            )

            recyclerView.layoutManager = StaggeredGridLayoutManager(
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
                else 3, StaggeredGridLayoutManager.VERTICAL
            )


            //Sets clickListener on retry Button if there is error or the recyclerView is invisible
            retryButton.setOnClickListener {
                searchPhotoAdapter.retry()
            }
        }

        if (!isInternetAvailable(requireContext()))
            showSnackBar()


        /**
         * Handles [recyclerView] [retryButton]... visibility depending on the loading state
         */
        searchPhotoAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                resultStatusTextView.isVisible = loadState.source.refresh is LoadState.Error


                //If the recyclerView is empty, sets its visibility to false
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    searchPhotoAdapter.itemCount < 1){

                    recyclerView.isVisible = false
                    emptyTextStatus.isVisible = true

                }else{
                    emptyTextStatus.isVisible = false
                }
            }
        }
    }



    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        //Inflates the layout
        inflater.inflate(R.menu.search_image_menu, menu)

        //Gets reference to the view
        val search = menu.findItem(R.id.search_image)

        //Casts view as SearchView
        val searchView = search.actionView as SearchView

        lifecycleScope.launch {
            searchView.getQueryTextChangedStateFlow()
                .debounce(800)
                .filter { searchQuery ->
                    if (searchQuery.isEmpty()){
                        viewModel.getPhotosListBySearch("cat")
                        return@filter false
                    }else{
                        return@filter  true
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    query -> viewModel.getPhotosListBySearch(query)
                }.collectLatest {
                    photoList ->
                    searchPhotoAdapter.submitData(photoList)
                }
        }

    }

    private fun showSnackBar(){
       Snackbar.make(requireView(), "Network failure", Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setAction("Retry"){
                searchPhotoAdapter.retry()
            }.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}