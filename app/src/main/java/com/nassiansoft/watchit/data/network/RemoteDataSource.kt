package com.nassiansoft.watchit.data.network

import com.nassiansoft.watchit.data.model.ApiResponse
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class  RemoteDataSource @Inject constructor(private val api: Api) : RemoteDataSourceInterface {

    override fun searchMovie(term:String)=api.searchMovie(term = term)
        .subscribeOn(Schedulers.io())
        .map {response: Response<ApiResponse> ->

            if (response.isSuccessful){

                if (response.body()==null){

                    return@map Resource.Error("Unable to fetch data")

                }else{

                    val converted= response.body()!!.results?.mapNotNull { it.toMovie() }
                    return@map Resource.Success(converted)
                }
            }else{

                return@map Resource.Error(response.errorBody()?.string())
            }
        }.onErrorReturn { Resource.Error(it.message) }

}