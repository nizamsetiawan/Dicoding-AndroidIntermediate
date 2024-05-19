package com.example.storysnap.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storysnap.data.remote.RemoteDataSource
import com.example.storysnap.data.remote.response.ListStoryItem
import retrofit2.HttpException
import java.io.IOException

class StoryPaging (private val remoteDataSource: RemoteDataSource, val token: String) : PagingSource<Int, ListStoryItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "StoriesPagging"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = remoteDataSource.getStories(token, page, params.loadSize)

            if (response.isSuccessful) {
                val responseData = response.body()
                val listStory = responseData?.listStory ?: emptyList()
                LoadResult.Page(
                    data = listStory,
                    prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                    nextKey = if (listStory.isEmpty()) null else page + 1

                ).also {
//                    Log.d(TAG, "Successfully loaded page: $page")
                }

            } else {
                LoadResult.Error(IOException("Failed to fetch data"))
            }
        } catch (exception: IOException) {
//            Log.e(TAG, "Error paging: ${exception.localizedMessage}")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
//            Log.e(TAG, "Error paging: ${exception.localizedMessage}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
