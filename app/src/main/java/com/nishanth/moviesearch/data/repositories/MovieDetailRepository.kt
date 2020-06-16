package com.nishanth.moviesearch.data.repositories

import com.nishanth.moviesearch.data.model.MovieDetail
import com.nishanth.moviesearch.data.network.ApiInterface
import com.nishanth.moviesearch.data.network.SafeApiRequest

class MovieDetailRepository(
    private val api: ApiInterface
) : SafeApiRequest() {

    suspend fun getMovieDetail(
        title: String,
        apiKey: String
    ): MovieDetail {

        return apiRequest { api.getMovieDetailData(title, apiKey) }
    }


}