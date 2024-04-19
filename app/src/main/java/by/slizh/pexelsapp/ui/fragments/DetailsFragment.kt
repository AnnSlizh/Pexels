package by.slizh.pexelsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import by.slizh.pexelsapp.R
import by.slizh.pexelsapp.databinding.FragmentDetailsBinding
import by.slizh.pexelsapp.util.downloader.ImageDownloader
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
                    binding.buttonsDetails.visibility = View.VISIBLE

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

                    binding.downloadImageButton.setOnClickListener {

                        val downloader = context?.let { it1 -> ImageDownloader(it1) }
                        photo.data?.src?.original?.let { it1 -> downloader?.downloadFile(it1) }
                        Toast.makeText(context, "Image download started", Toast.LENGTH_SHORT).show()
                    }

//                    val imageDownloader = ImageDownloader(requireContext())
//
//                    binding.downloadImageButton.setOnClickListener {
//                        photo.data?.src?.original?.let { it1 -> imageDownloader.downloadImage(it1) }
//                    }
                }

                is Resource.Error -> {
                    binding.detailProgressBar.visibility = View.GONE
                    binding.buttonsDetails.visibility = View.GONE
                    binding.emptyDetailsViewStub.visibility = View.VISIBLE
                }

                is Resource.Loading -> {
                    binding.detailProgressBar.visibility = View.VISIBLE
                    binding.buttonsDetails.visibility = View.VISIBLE
                }
            }
        })
        photoViewModel.getPhotoById(currentPhotoId)




    }
}