package eg.esperantgada.imagegallery.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import eg.esperantgada.imagegallery.R
import eg.esperantgada.imagegallery.adapter.FlickrImageAdapter
import eg.esperantgada.imagegallery.databinding.FragmentHomeBinding
import eg.esperantgada.imagegallery.viewmodel.FlickrImageViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get()  = _binding!!

    private val viewModel : FlickrImageViewModel by viewModels()

    private lateinit var flickrImageAdapter: FlickrImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flickrImageAdapter = FlickrImageAdapter()

        binding.recyclerView.apply {
            adapter = flickrImageAdapter
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2
            else 3, StaggeredGridLayoutManager.VERTICAL
            )
        }

        viewModel.retrievedPhotos.observe(viewLifecycleOwner){ photo ->
            flickrImageAdapter.submitData(viewLifecycleOwner.lifecycle, photo)
        }

        flickrImageAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }

}