package by.slizh.pexelsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.slizh.pexelsapp.R
import by.slizh.pexelsapp.databinding.FragmentHomeBinding
import by.slizh.pexelsapp.ui.adapters.HomeAdapter
import by.slizh.pexelsapp.util.Resource
import by.slizh.pexelsapp.viewModel.PhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val photoListViewModel: PhotoListViewModel by viewModels()

    @Inject
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeRecycleView.apply {
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL )
        }

        photoListViewModel.photoList.observe(viewLifecycleOwner, Observer { photoList ->
            when (photoList) {
                is Resource.Success -> {
                    binding.homeProgressBar.visibility = View.GONE
                    homeAdapter.differ.submitList(photoList.data?.photos)
                }
                is Resource.Error -> {
                    binding.homeProgressBar.visibility = View.GONE
                    Log.d("ERROR HOME FRAGMENT","onViewCreated: ERROR ")
                }
                is Resource.Loading -> {
                    binding.homeProgressBar.visibility = View.VISIBLE
                    Log.d("ERROR HOME FRAGMENT","onViewCreated: LOADING..")

                }
            }
        })
        photoListViewModel.getCuratedPhotoList()

    }
}