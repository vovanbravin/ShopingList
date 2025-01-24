package com.example.shopinglist.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopinglist.Constances
import com.example.shopinglist.R
import com.example.shopinglist.databinding.ActivityNewNoteBinding
import com.example.shopinglist.entities.NoteItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()
    }

    fun actionBarSettings()
    {
        val actionB = supportActionBar
        actionB?.setDisplayHomeAsUpEnabled(true)
        actionB?.title = getString(R.string.NoteNew)

    }

    private fun getNote()
    {
        val data = intent.getSerializableExtra(Constances.NEW_NOTE_KEY)
        if(data!=null) {
            note = data as NoteItem
            binding.edTtitle.setText(note?.title)
            binding.edContent.setText(note?.content)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save) saveResult()
        else if(item.itemId == R.id.bold)
            setBoldStyleForSelectedText()
        else
            finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldStyleForSelectedText() = with(binding)
    {
        val stardPos = edContent.selectionStart
        val endPos = edContent.selectionEnd
        val styles = edContent.text.getSpans(stardPos,endPos, StyleSpan:: class.java)

        var boldStyle: StyleSpan? = null
        if(styles.isNotEmpty())
        {
            edContent.text.removeSpan(styles[0])
        }
        else{
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        edContent.text.setSpan(boldStyle,stardPos,endPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edContent.text.trim()
        edContent.setSelection(stardPos)
    }



    private fun saveResult()
    {
        val intent = Intent()
        if(note == null) {
            intent.putExtra(Constances.NEW_NOTE_KEY, creareNewNote())
            intent.putExtra(Constances.MODE_KEY,"new")
        }
        else{
            intent.putExtra(Constances.NEW_NOTE_KEY, saveUpdateNote())
            intent.putExtra(Constances.MODE_KEY,"update")
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun saveUpdateNote(): NoteItem?
    {
        return note?.copy(title = binding.edTtitle.text.toString(),
            content = binding.edContent.text.toString())
    }

    private fun getCurrnetTime(): String
    {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }


    private fun creareNewNote(): NoteItem
    {
        return NoteItem(
            null,
            binding.edTtitle.text.toString(),
            binding.edContent.text.toString(),
            getCurrnetTime(),
            ""
        )
    }




}