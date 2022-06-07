package com.gmail.vlaskorobogatov.stopwatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LapAdapter(context: Context, list: List<Int>) : RecyclerView.Adapter<LapAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    val laps = list
    val context = context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.flag)
        val text: TextView = itemView.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.lap_info_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lap: Int = laps[position]
        holder.img.setImageResource(R.drawable.flag)
        holder.text.text = formatSeconds(context, lap)
    }

    override fun getItemCount(): Int {
        return laps.size
    }
}