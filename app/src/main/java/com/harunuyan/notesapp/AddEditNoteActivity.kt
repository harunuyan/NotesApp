package com.harunuyan.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.harunuyan.notesapp.databinding.ActivityAddEditNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditNoteBinding
    lateinit var noteViewModel: NoteViewModel
    var noteId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val noteType = intent.getStringExtra("noteType")
        if(noteType == "Edit") {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId",-1)

            binding.apply {
                editAddNote.text = "Update Note"
                editNoteTitle.setText(noteTitle)
                editNoteDescription.setText(noteDescription)
            }
        } else {
            binding.editAddNote.setText("Save Note")
        }
        binding.editAddNote.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString()
            val noteDescription = binding.editNoteDescription.text.toString()

            if (noteType=="Edit") {
                if(noteTitle.isNotEmpty() || noteDescription.isNotEmpty()) {
                    val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String =simpleDateFormat.format(Date())
                    val updateNote = Note(noteTitle,noteDescription,currentDate)
                    updateNote.id = noteId
                    noteViewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated!", Toast.LENGTH_LONG).show()
                }
            } else {
                if(noteTitle.isNotEmpty() || noteDescription.isNotEmpty()) {
                    val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = simpleDateFormat.format(Date())
                    noteViewModel.addNote(Note(noteTitle,noteDescription,currentDate))
                    Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Please Enter a Note", Toast.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }
    }


}