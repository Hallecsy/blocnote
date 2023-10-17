package com.example.blocnote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val notes = ArrayList<Note>()
    private val adapter = NoteAdapter(notes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        val noteEditText: EditText = findViewById(R.id.noteEditText)

        addButton.setOnClickListener {
            val content = noteEditText.text.toString()
            if (content.isNotEmpty()) {
                notes.add(Note(content))
                adapter.notifyDataSetChanged()
                noteEditText.text.clear()
            }
        }
    }

    data class Note(val content: String)
}

class NoteAdapter(private val notes: List<MainActivity.Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: MainActivity.Note) {
            val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
            contentTextView.text = note.content

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, EditNoteActivity::class.java)
                intent.putExtra("notePosition", adapterPosition)
                intent.putExtra("noteContent", note.content)
                itemView.context.startActivity(intent)
            }
        }
    }
}
