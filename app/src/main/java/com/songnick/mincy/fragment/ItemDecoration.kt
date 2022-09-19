//package com.songnick.mincy.fragment
//
//import android.graphics.Canvas
//import android.graphics.Rect
//import android.view.View
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
//
//class ItemDecoration:RecyclerView.ItemDecoration() {
//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        val position = parent.getChildAdapterPosition(view)
//        val layoutManager = parent.layoutManager
//        var spanCount = 1
//        if (layoutManager is StaggeredGridLayoutManager){
//            spanCount = layoutManager.spanCount
//        }
//        outRect.left = 100
//        outRect.top = 100
//        if (position/spanCount == 2){
//            outRect.right = 100
//        }
//    }
//}