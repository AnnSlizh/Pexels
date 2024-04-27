package by.slizh.pexelsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.slizh.pexelsapp.MainActivity
import by.slizh.pexelsapp.R
import by.slizh.pexelsapp.data.response.PhotoList
import by.slizh.pexelsapp.databinding.FragmentHomeBinding
import by.slizh.pexelsapp.ui.adapters.HomeAdapter
import by.slizh.pexelsapp.util.Constans
import by.slizh.pexelsapp.util.Resource
import by.slizh.pexelsapp.viewModel.PhotoViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val photoViewModel: PhotoViewModel by viewModels()

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = HomeAdapter()

        binding.homeRecycleView.apply {
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        photoViewModel.photoList.observe(viewLifecycleOwner, Observer { photoList ->
            checkState(photoList)
        })
        photoViewModel.getCuratedPhotoList()

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                photoViewModel.photoList.observe(viewLifecycleOwner, Observer { photoList ->
                    checkState(photoList)
                })

                if (query.isNullOrEmpty()) binding.chipGroup.clearCheck()

                query?.let { query ->
                    val chipGroup = binding.chipGroup

                    for (i in 0 until chipGroup.childCount) {
                        val chip = chipGroup.getChildAt(i) as Chip
                        val chipText = chip.text.toString()

                        if (chipText.contains(query, ignoreCase = true)) {
                            chip.isChecked = true
                            return@let
                        } else chip.isChecked = false
                    }
                    photoViewModel.getSearchPhotoList(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                photoViewModel.photoList.observe(viewLifecycleOwner, Observer { photoList ->
                    checkState(photoList)
                })
                if (newText.isNullOrEmpty()) {
                    binding.chipGroup.clearCheck()
                    photoViewModel.getCuratedPhotoList()
                }

                newText?.let { query ->
                    photoViewModel.getSearchPhotoList(query)
                }
                return true
            }
        })

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->

            if (checkedIds.isNotEmpty()) {
                val selectedChip = group.findViewById<Chip>(checkedIds[0])
                val selectedText = selectedChip?.text?.toString() ?: ""
                binding.searchView.setQuery(selectedText, true)
            }
        }


        photoViewModel.featuredCollection.observe(
            viewLifecycleOwner,
            Observer { featuredCollection ->
                when (featuredCollection) {
                    is Resource.Success -> {
                        binding.chipGroup.visibility = View.VISIBLE

                        if (binding.chipGroup.childCount > 0) {
                            for (item in 0 until binding.chipGroup.childCount) {
                                val chip = binding.chipGroup.getChildAt(item) as Chip
                                chip.text = featuredCollection.data?.collections!![item].title
                            }
                        } else {
                            for (item in 0..6) {
                                val chip = Chip(context)
                                chip.text = featuredCollection.data?.collections!![item].title
                                binding.chipGroup.addView(chip)
                            }
                        }

                    }

                    is Resource.Error -> {
                        binding.chipGroup.visibility = View.GONE
                    }

                    is Resource.Loading -> {
                        binding.homeProgressBar.visibility = View.VISIBLE
                    }
                }
            })
        photoViewModel.getFeaturedCollections()

        homeAdapter.showDetailPhoto {

            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.id!!)
            Navigation.findNavController(view).navigate(action)
        }

    }

    private fun checkState(photoList: Resource<PhotoList>) {
        when (photoList) {
            is Resource.Success -> {
                binding.homeProgressBar.visibility = View.GONE
                homeAdapter.differ.submitList(photoList.data?.photos)
            }

            is Resource.Error -> {

                binding.homeProgressBar.visibility = View.GONE

                when (photoList.message) {
                    Constans.MESSAGE_NO_INTERNET -> {
                        binding.noNetworkViewStub.visibility = View.VISIBLE
                    }

                    Constans.MESSAGE_NETWORK_FAILED -> {
                        Toast.makeText(context, "Check your network connection", Toast.LENGTH_SHORT)
                            .show()
                    }

                    Constans.MESSAGE_ERROR_GET_PHOTO -> {
                        binding.emptyHomeViewStub.visibility = View.VISIBLE
                    }
                }
            }

            is Resource.Loading -> {
                binding.homeProgressBar.visibility = View.VISIBLE
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val activityMainBinding = (activity as? MainActivity)?.binding
        activityMainBinding?.let { binding ->
            binding.bottomNavigationView.visibility = View.VISIBLE
        }
    }
}