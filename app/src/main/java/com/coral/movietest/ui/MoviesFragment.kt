package com.coral.movietest.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.R
import com.coral.movietest.models.Movie
import com.coral.movietest.models.Resource
import com.coral.movietest.ui.adapters.MoviesAdapter
import com.coral.movietest.ui.viewmodels.MoviesViewModel
import com.coral.movietest.util.Injection
import com.coral.movietest.util.ItemOffsetDecoration
import com.coral.movietest.util.MovieType

class MoviesFragment : Fragment() {

    private var viewModel: MoviesViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = obtainViewModel(activity)
        setupListAdapter()

        viewModel!!.getCurrentTitle().observe(this,
            Observer<Int> { title -> activity?.actionBar?.setTitle(title) })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_toolbar, menu)

        when {
            viewModel?.currentSorting === MovieType.POPULAR -> menu?.findItem(R.id.popular_imenu)?.isChecked = true
            viewModel?.currentSorting === MovieType.UPCOMING -> menu?.findItem(R.id.upcoming_imenu)?.isChecked = true
            else -> menu?.findItem(R.id.top_rated_imenu)?.isChecked = true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.groupId == R.id.menu_sort_group) {
            viewModel?.setSortMoviesBy(item.itemId)
            item.isChecked = true
        } else if (item?.itemId == R.id.action_search) {
            val searchbar: EditText? = activity?.findViewById(R.id.search_et)
            if (searchbar?.visibility == View.GONE)
                searchbar?.visibility = View.VISIBLE
            else{
                searchbar?.visibility = View.GONE
                viewModel?.setSortMoviesBy(R.id.popular_imenu)
            }
            searchbar?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    viewModel?.searchMovies(searchbar.text.toString())
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })

        }



        return super.onOptionsItemSelected(item)
    }

    private fun setupListAdapter() {
        val recyclerView: RecyclerView = activity!!.findViewById(R.id.rv_movie_list)
        val moviesAdapter = MoviesAdapter(viewModel!!)
        val layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.span_count))

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (moviesAdapter.getItemViewType(position)) {
                    R.layout.item_network_state -> layoutManager.spanCount
                    else -> 1
                }
            }
        }

        recyclerView.setAdapter(moviesAdapter)
        recyclerView.setLayoutManager(layoutManager)
        val itemDecoration = ItemOffsetDecoration(activity!!, R.dimen.item_offset)
        recyclerView.addItemDecoration(itemDecoration)

        viewModel?.pagedList?.observe(viewLifecycleOwner,
            Observer<PagedList<Movie>> { movies -> moviesAdapter.submitList(movies) })

        viewModel?.networkState?.observe(viewLifecycleOwner,
            Observer<Any> { resource -> moviesAdapter.setNetworkState(resource as Resource<Any>) })
    }

    companion object {

        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }

        fun obtainViewModel(activity: FragmentActivity?): MoviesViewModel {
            val factory = Injection.provideViewModelFactory(activity?.applicationContext!!)
            return ViewModelProviders.of(activity, factory).get(MoviesViewModel::class.java)
        }
    }
}
