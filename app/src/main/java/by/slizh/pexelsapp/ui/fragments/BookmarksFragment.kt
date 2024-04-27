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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.slizh.pexelsapp.MainActivity
import by.slizh.pexelsapp.R
import by.slizh.pexelsapp.databinding.ActivityMainBinding
import by.slizh.pexelsapp.databinding.FragmentBookmarksBinding
import by.slizh.pexelsapp.databinding.FragmentHomeBinding
import by.slizh.pexelsapp.ui.adapters.BookmarksAdapter
import by.slizh.pexelsapp.util.Constans
import by.slizh.pexelsapp.util.Resource
import by.slizh.pexelsapp.viewModel.BookmarkViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private lateinit var binding: FragmentBookmarksBinding
    private val bookmarkViewModel: BookmarkViewModel by viewModels()

    private lateinit var bookmarksAdapter: BookmarksAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarksAdapter = BookmarksAdapter()

        binding.bookmarkRecycleView.apply {
            adapter = bookmarksAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        bookmarkViewModel.allBookmarks.observe(viewLifecycleOwner, Observer { bookmarks ->
            bookmarksAdapter.setBookmarksList(bookmarks)
        })

        bookmarksAdapter.showDetailPhoto {
            val action =
                BookmarksFragmentDirections.actionBookmarksFragmentToDetailsFragment(it.id)
            Navigation.findNavController(view).navigate(action)
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