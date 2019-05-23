package com.coral.movietest.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.coral.movietest.data.repository.db.PageKeyedMovieDataSource

class MovieRepo(
    var data: LiveData<PagedList<Movie>>,
    var resource: LiveData<Any>,
    var sourceLiveData: MutableLiveData<PageKeyedMovieDataSource>
)