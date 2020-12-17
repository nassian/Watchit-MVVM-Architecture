package com.nassiansoft.watchit.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Movie(

    @PrimaryKey(autoGenerate = false)
    var trackId:String,
    var trackName:String,
    var trackViewUrl:String?=null,
    var previewUrl:String?=null,
    var artworkUrl100:String?=null,
    var releaseDate:String?=null,
    var trackTimeMillis:String?=null,
    var primaryGenreName:String?=null,
    var longDescription:String?=null,
    var country:String?=null,
    var inMyList:Boolean=false,
    var watched:Boolean=false
):Parcelable