package com.example.sampleandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleandroid.R

class SkeletonAdapter(
    private val skeletonRowCount: Int,
    private val context: Context
) : RecyclerView.Adapter<SkeletonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.view_album_skeleton_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return skeletonRowCount
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
