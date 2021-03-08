package com.example.mynotes.adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.databinding.ItemNotesBinding
import com.example.mynotes.entities.Notes
import com.makeramen.roundedimageview.RoundedImageView

class NoteAdapter() :RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {
    var arrlis = ArrayList<Notes>()
    var listener:OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notes , parent ,  false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: NoteAdapter.NotesViewHolder, position: Int) {
       holder.binding.tvtitle.text =arrlis[position].title
        holder.binding.tvdesc.text =arrlis[position].noteText
        holder.binding.tvDateTime.text =arrlis[position].dateTime

        if (arrlis[position].color != null) {
            Log.i("tag", "onBindViewHolder: ${arrlis[position].color}")
            holder.binding.cardView.setBackgroundColor(Color.parseColor(arrlis[position].color))
        }else{
            holder.binding.cardView.setBackgroundColor(R.color.ColorLightBlack)
        }

        if (arrlis[position].imgPath != null) {
            Log.i("tag", "onBindViewHolder: ${arrlis[position].imgPath}")
            holder.binding.imgNoter.setImageBitmap(BitmapFactory.decodeFile(arrlis[position].imgPath))
            holder.binding.imgNoter.visibility = View.VISIBLE

        }else{
            Log.i("tag", "onBindViewHolder:  else part ${arrlis[position].imgPath}")
            holder.binding.imgNoter.visibility = View.GONE
        }

        if (arrlis[position].webLink != null) {
            Log.i("tag", "onBindViewHolder: ${arrlis[position].imgPath}")
            holder.binding.tvweblink.text = arrlis[position].webLink
            holder.binding.tvweblink.visibility = View.VISIBLE

        }else{
            Log.i("tag", "onBindViewHolder:  else part ${arrlis[position].imgPath}")
            holder.binding.tvweblink.visibility = View.GONE
        }

        holder.binding.cardView.setOnClickListener {
            listener!!.onClicked(arrlis[position].id!!)
        }

    }

    override fun getItemCount(): Int {
     return arrlis.size
    }

    fun setData(arrNoteslist:List<Notes>){
        arrlis = arrNoteslist as ArrayList<Notes>
    }
    fun setOnClickListener(listener1: OnItemClickListener){
         listener = listener1
    }

    public class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       val binding = ItemNotesBinding.bind(itemView)
    }

    interface OnItemClickListener{
        fun onClicked(noteId:Int){

        }
    }
}