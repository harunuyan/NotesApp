package com.harunuyan.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harunuyan.notesapp.databinding.RecyclerViewRowBinding

class NoteRVAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = RecyclerViewRowBinding.bind(view)
        fun bind() {
            binding.apply {
                noteTitle.text = allNotes[position].noteTitle
                timeStamp.text = "Last Updated : ${allNotes[position].timeStamp}"
                noteDelete.setOnClickListener {
                    noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
                }
                itemView.setOnClickListener {
                    noteClickInterface.onNoteClick(allNotes[position])
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }


    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    interface NoteClickDeleteInterface {
        fun onDeleteIconClick(note: Note)
    }

    interface NoteClickInterface {
        fun onNoteClick(note: Note)
    }
}