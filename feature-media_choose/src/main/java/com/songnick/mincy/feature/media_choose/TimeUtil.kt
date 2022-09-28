package com.songnick.mincy.feature.media_choose

/*****
 * @author qfsong
 * Create Time: 2022/9/28
 **/
object TimeUtil {

    /***
     * format time to string
     * @param time which is millisecond
     * */
    fun formattedTime(time:Long):String{
        val second = time/1000
        val hs: String
        val ms: String
        val ss: String
        val formatTime: String

        val h: Long = second / 3600
        val m: Long = second % 3600 / 60
        val s: Long = second % 3600 % 60
        //hour string
        hs = if (h < 10) {
            "0$h"
        } else {
            "" + h
        }
        //min string
        ms = if (m < 10) {
            "0$m"
        } else {
            "" + m
        }
        //second string
        ss = if (s < 10) {
            "0$s"
        } else {
            "" + s
        }
        //result
        formatTime = if (h > 0) {
            "$hs:$ms:$ss"
        } else {
            "$ms:$ss"
        }
        return formatTime
    }
}