package com.nishanth.moviesearch.data.repositories

import com.nishanth.moviesearch.data.model.SearchResults
import com.nishanth.moviesearch.data.network.ApiInterface
import com.nishanth.moviesearch.data.network.SafeApiRequest

class HomeRepository(
    private val api: ApiInterface
) : SafeApiRequest() {

    suspend fun getMovies(
        searchTitle: String,
        apiKey: String,
        pageIndex: Int
    ): SearchResults {

        return apiRequest { api.getSearchResultData(searchTitle, apiKey, pageIndex) }
    }


}