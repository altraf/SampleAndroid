package com.example.sampleandroid.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleandroid.R
import com.example.sampleandroid.adapter.AlbumAdapter
import com.example.sampleandroid.adapter.SkeletonAdapter
import com.example.sampleandroid.container.App
import com.example.sampleandroid.databinding.FragmentSearchBinding
import com.example.sampleandroid.dialog.AlbumInfoDialogFragment
import com.example.sampleandroid.model.Album
import com.example.sampleandroid.model.AlbumResponse
import com.example.sampleandroid.util.State
import com.example.sampleandroid.viewModel.SearchUiViewModel
import com.example.sampleandroid.viewModel.SearchViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import java.io.IOException

class SearchFragment : Fragment(), AlbumAdapter.ItemClickListener {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by activityViewModels()
    private val uiViewModel: SearchUiViewModel by viewModels()
    private val albums = arrayListOf<Album>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var captionConstraintLayout: ConstraintLayout
    private lateinit var noResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentSearchBinding.inflate(inflater)

        recyclerView = view.searchRv
        recyclerView.layoutManager = LinearLayoutManager(context)

        captionConstraintLayout = view.searchBeforeSearchCl
        noResultTextView = view.searchNoResultTv

        viewModel.albums.observe(viewLifecycleOwner, Observer {
            albums.clear()
            albums.addAll(it)
            showAlbums()
            recyclerView.adapter?.notifyDataSetChanged()
            noResultTextView.visibility = if (it.size == 0) View.VISIBLE else View.INVISIBLE
        })


        uiViewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.Idle -> {}
                is State.Loading -> {
                    showSkeleton()
                    noResultTextView.visibility = View.INVISIBLE
                }
                is State.Error -> {
                    Snackbar.make(view.root, it.error, Snackbar.LENGTH_SHORT).show()
                    uiViewModel.updateState(State.Idle)
                }
            }
        })

        return view.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val mSearchMenuItem = menu.findItem(R.id.search)
        searchView = mSearchMenuItem.actionView as SearchView
        val handler = Handler(Looper.getMainLooper())

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                uiViewModel.updateState(State.Loading)
                searchView.clearFocus()
                handler.removeCallbacksAndMessages(null)
                getAlbums(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                uiViewModel.updateState(State.Loading)
                handler.removeCallbacksAndMessages(null)
                getAlbums(newText, handler)
                return true
            }
        })
    }

    private fun getAlbums(searchTerm: String?, handler: Handler? = null) {
        if (searchTerm?.isNotEmpty() == true) {
            if (handler != null) {
                handler.postDelayed({
                    searchView.clearFocus()
                    getAlbums(searchTerm)
                }, 1000)
            } else {
                getAlbums(searchTerm)
            }
        } else {
            noResultTextView.visibility = View.INVISIBLE
            captionConstraintLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        }
    }

    private fun getAlbums(searchTerm: String) {
        noResultTextView.visibility = View.INVISIBLE
        captionConstraintLayout.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE

        App().container.searchController.search(searchTerm, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiViewModel.updateState(State.Error(getString(R.string.error_getting_albums)))
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    uiViewModel.updateState(State.Idle)
                    val body = response.body?.string()
                    val mapper = jacksonObjectMapper()
                    val albumResponse = mapper.readValue<AlbumResponse>(body ?: "")
                    noResultTextView.visibility = View.INVISIBLE
                    captionConstraintLayout.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                    viewModel.updateAlbums(albumResponse.results)
                }
                else {
                    uiViewModel.updateState(State.Error(getString(R.string.error_getting_albums)))
                }
            }
        })
    }

    private fun showAlbums() {
        recyclerView.adapter = AlbumAdapter(albums, requireContext(), this)
    }

    private fun showSkeleton() {
        recyclerView.adapter = SkeletonAdapter(getSkeletonRowCount(), requireContext())
    }

    private fun getSkeletonRowCount(): Int {
        val skeletonRowHeight =
            resources.getDimension(R.dimen.skeleton_height) //converts to pixel
        return (requireView().height / skeletonRowHeight).toInt()
    }

    override fun onItemClicked(album: Album) {
        viewModel.updateSelectedAlbum(album)
        val dialog = AlbumInfoDialogFragment()
        activity?.supportFragmentManager?.let { dialog.show(it, "dialogFragment") }
    }
}
