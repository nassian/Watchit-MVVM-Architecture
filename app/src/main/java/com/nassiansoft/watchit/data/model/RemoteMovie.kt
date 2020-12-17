package com.nassiansoft.watchit.data.model

data class RemoteMovie (
    var trackId:String?=null,
    var trackName:String?=null,
    var trackViewUrl:String?=null,
    var previewUrl:String?=null,
    var artworkUrl100:String?=null,
    var releaseDate:String?=null,
    var trackTimeMillis:String?=null,
    var primaryGenreName:String?=null,
    var longDescription:String?=null,
    var country:String?=null,
        ){

    fun toMovie():Movie?{
       return if (trackId==null || trackName==null) null
        else Movie(
                trackId!!,trackName!!,trackViewUrl,previewUrl,artworkUrl100,releaseDate,
                trackTimeMillis,primaryGenreName,longDescription,country
       )
    }
}