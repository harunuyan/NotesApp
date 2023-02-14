package com.harunuyan.notesapp

import androidx.lifecycle.LiveData

class NoteRepository(private val notesDAo: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDAo.getAllNote()

    suspend fun insert(note: Note) {
        notesDAo.insert(note)
    }

    suspend fun delete(note: Note) {
        notesDAo.delete(note)
    }

    suspend fun update(note: Note) {
        notesDAo.update(note)
    }



}