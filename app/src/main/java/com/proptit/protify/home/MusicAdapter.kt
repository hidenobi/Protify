package com.proptit.protify.home

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proptit.protify.databinding.MusicItemBinding
import com.proptit.protify.models.Track

class MusicAdapter(
    private val context: Context,
    private var dataList: List<Track>
)
    : RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: MusicItemBinding = MusicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = dataList[position]
        holder.title.text = currentData.title
        Glide.with(context)
            .load(currentData.artist.picture)
            .into(holder.image)

        val mediaPlayer = MediaPlayer.create(context, currentData.preview.toUri())
        holder.playBtn.setOnClickListener { mediaPlayer.start() }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: List<Track>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterData(searchTerm:String){
        val filterList:ArrayList<Track> = ArrayList()
        for(item: Track in dataList){
            if(item.title.lowercase().contains(searchTerm.lowercase()))
                filterList.add(item)
        }
        dataList = filterList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView:MusicItemBinding): RecyclerView.ViewHolder(itemView.root) {
         val image: ImageView
         val title: TextView
         val playBtn: ImageButton

        init {
            image = itemView.image
            title = itemView.title
            playBtn = itemView.playBtn
        }
    }

}