package com.harunuyan.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.harunuyan.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteRVAdapter.NoteClickDeleteInterface,
    NoteRVAdapter.NoteClickInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        val noteRVAdapter = NoteRVAdapter(this, this, this)
        binding.recyclerView.adapter = noteRVAdapter
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        })
        binding.addNote.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} deleted!", Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.apply {
            putExtra("noteType", "Edit")
            putExtra("noteTitle", note.noteTitle)
            putExtra("noteDescription", note.noteDescription)
            putExtra("noteId", note.id)
        }
        startActivity(intent)
    }
}