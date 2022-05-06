package com.songnick.mincy.data

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/*****
 * @author qfsong
 * Create Time: 2022/4/26
 **/
class ThumbnailDelegate : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        TODO("Not yet implemented")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        TODO("Not yet implemented")
    }

}