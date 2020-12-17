package com.nassiansoft.watchit.data.network

import com.nassiansoft.watchit.data.model.ApiResponse
import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("search")
    fun searchMovie(@Field("term")term:String
                    ,@Field("media") media:String="movie" ):Flowable<Response<ApiResponse>>
}