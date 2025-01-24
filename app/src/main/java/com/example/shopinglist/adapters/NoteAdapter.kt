package com.example.shopinglist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinglist.R
import com.example.shopinglist.databinding.NoteItemBinding
import com.example.shopinglist.entities.NoteItem

class NoteAdapter(private val listener: Listener): ListAdapter<NoteItem,NoteAdapter.NoteHolder>(NoteComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }


    class NoteHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private val binding = NoteItemBinding.bind(view)
        fun setData(noteItem: NoteItem, listener: Listener) = with(binding)
        {
            tvTitle.text = noteItem.title
            tvContent.text = noteItem.content
            tvTime.text = noteItem.time
            itemView.setOnClickListener{
                listener.udpateNote(noteItem)
            }
            imDelete.setOnClickListener {
                listener.deleteNote(noteItem.id!!)
            }

        }
        companion object
        {
            fun create(parent: ViewGroup): NoteHolder
            {
                return NoteHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.note_item,parent,false))
            }
        }
    }

    class NoteComparator: DiffUtil.ItemCallback<NoteItem>()
    {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return newItem == oldItem
        }

    }

    interface Listener
    {
        fun deleteNote(id: Int)
        fun udpateNote(noteItem: NoteItem)
    }


}