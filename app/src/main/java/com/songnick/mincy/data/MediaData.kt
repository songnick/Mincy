package com.songnick.mincy.data


data class MediaData(val path:String,val name: String,
                     val date:Long, val mediaType:String
                    ) {
    var duration:Long = 0
    var thumbnail:String = ""

    fun getImagePath():String{
        if (mediaType.contains("video")){
            return thumbnail
        }
        return path
    }
}