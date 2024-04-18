package by.slizh.pexelsapp.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import by.slizh.pexelsapp.MainActivity
import by.slizh.pexelsapp.R
import by.slizh.pexelsapp.databinding.FragmentDetailsBinding
import by.slizh.pexelsapp.util.Resource
import by.slizh.pexelsapp.viewModel.PhotoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val detailsPhotoArgs: DetailsFragmentArgs by navArgs<DetailsFragmentArgs>()

    private lateinit var binding: FragmentDetailsBinding
    private val photoViewModel: PhotoViewModel by viewModels()

    var bottomNavigationViewVisibility = View.VISIBLE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { activity ->
            val bottomNavigationView =
                activity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.visibility = View.GONE
        }

        val currentPhotoId = detailsPhotoArgs.photoId

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_homeFragment)
        }

        photoViewModel.photo.observe(viewLifecycleOwner, Observer { photo ->
            when (photo) {
                is Resource.Success -> {
                    binding.detailProgressBar.visibility = View.GONE

                    binding.photographerText.text = photo.data?.photographer

                    binding.detailImage.apply {
                        Glide.with(this)
                            .load(photo.data?.src?.original)
                            .error(R.drawable.download_button)
                            .fitCenter()
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(this)
                    }
                }

                is Resource.Error -> {
                    binding.detailProgressBar.visibility = View.GONE
                    Toast.makeText(context, "Data loading error", Toast.LENGTH_SHORT).show()
                    Log.d("ERROR DETAIL FRAGMENT", "onViewCreated: ERROR ")
                }

                is Resource.Loading -> {
                    binding.detailProgressBar.visibility = View.VISIBLE
                    Log.d("ERROR DETAIL FRAGMENT", "onViewCreated: LOADING..")

                }
            }
        })
        photoViewModel.getPhotoById(currentPhotoId)


    }
}