package com.gmail.vlaskorobogatov.stopwatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LapAdapter(private val context: Context, list: List<Long>) :
    RecyclerView.Adapter<LapAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val laps = list

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.flag)
        val index: TextView = itemView.findViewById(R.id.index)
        val timeTotal: TextView = itemView.findViewById(R.id.timeTotal)
        val timeLap: TextView = itemView.findViewById(R.id.timeLap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.lap_info_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lap: Long = laps[position]
        holder.img.setImageResource(R.drawable.flag)
        holder.index.text = (position + 1).toString()
        holder.timeLap.text =
            formatSeconds(context, lap - if (position > 0) laps[position - 1] else 0L)
        holder.timeTotal.text = formatSeconds(context, lap)
    }

    override fun getItemCount(): Int {
        return laps.size
    }
}