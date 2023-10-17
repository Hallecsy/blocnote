package com.example.blocnote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditNoteActivity : AppCompatActivity() {

    private var notePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val saveButton: Button = findViewById(R.id.saveButton)
        val editNoteEditText: EditText = findViewById(R.id.editNoteEditText)

        // Récupérez les données de l'intent
        val intent = intent
        if (intent != null) {
            notePosition = intent.getIntExtra("notePosition", -1)
            val noteContent = intent.getStringExtra("noteContent")
            if (noteContent != null) {
                editNoteEditText.setText(noteContent)
            }
        }

        saveButton.setOnClickListener {
            val editedContent = editNoteEditText.text.toString()
            if (notePosition != -1) {
                if (editedContent.isNotEmpty()) {
                    updateNoteInList(notePosition, editedContent)
                } else {
                    removeNoteFromList(notePosition)
                }
            }

            finish()
        }
    }

    private fun updateNoteInList(position: Int, content: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("editedNotePosition", position)
        resultIntent.putExtra("editedNoteContent", content)
        setResult(RESULT_OK, resultIntent)
    }

    private fun removeNoteFromList(position: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra("deletedNotePosition", position)
        setResult(RESULT_CANCELED, resultIntent)
    }
}
