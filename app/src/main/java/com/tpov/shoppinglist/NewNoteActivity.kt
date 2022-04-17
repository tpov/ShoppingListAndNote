package com.tpov.shoppinglist

import Question
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.method.TextKeyListener.clear
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.text.getSpans
import androidx.preference.PreferenceManager
import com.tpov.shoppinglist.databinding.ActivityNewNoteBinding
import com.tpov.shoppinglist.db.CrimeNewQuiz
import com.tpov.shoppinglist.db.MainViewModel
import com.tpov.shoppinglist.entities.NoteItem
import com.tpov.shoppinglist.fragments.NoteFragment
import com.tpov.shoppinglist.utils.HtmlManager
import com.tpov.shoppinglist.utils.MyTouchListener
import com.tpov.shoppinglist.utils.TimeManager
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@InternalCoroutinesApi
class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    private var pref: SharedPreferences? = null

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("NewNoteActivity", "onCreate")
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()

        init()
        setTextSize()
        onClickColorPicker()
        actionMenuCallback()
    }

    private fun getNote() {
        Log.d("NewNoteActivity", "getNote")

        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            Log.d("NewNoteActivity", "sNote != null")
            note = sNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote() = with(binding) {
        Log.d("NewNoteActivity", "fillNote")

            edTitle.setText(note?.title)
            edDescription.setText(HtmlManager.getFormatHtml(note?.content!!))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("NewNoteActivity", "onCreateOptionsMenu")

        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("NewNoteActivity", "onOptionsItemSelected")

        when (item.itemId) {
            R.id.id_save -> {
                setMainResult()
            }
            android.R.id.home -> {
                finish()
            }
            R.id.id_bold -> {
                setBoldForSelectedText()
            }
            R.id.id_color-> {
                if (binding.colorPicker.isShown) {

                    closeColorPicker()
                } else {
                    openColorPicker()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickColorPicker() = with(binding) {
        imBlack.setOnClickListener {
            setColorForSelectedText(R.color.picker_black)
        }
        imCyan.setOnClickListener {
            setColorForSelectedText(R.color.picker_cyan)
        }
        imGreen.setOnClickListener {
            setColorForSelectedText(R.color.picker_green)
        }
        imOrange.setOnClickListener {
            setColorForSelectedText(R.color.picker_orange)
        }
        imRed.setOnClickListener {
            setColorForSelectedText(R.color.picker_red)
        }
        imYellow.setOnClickListener {
            setColorForSelectedText(R.color.picker_yellow)
        }
    }

    private fun setBoldForSelectedText() = with(binding) {
        Log.d("NewNoteActivity", "setBoldForSelectedText")

        val startPos = edDescription.selectionStart
        val endPos = edDescription.selectionEnd
        val styles = edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null

        if (styles.isNotEmpty()) edDescription.text.removeSpan(styles[0])
        else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        edDescription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDescription.text.trim()
        edDescription.setSelection(startPos)
    }
    private fun setColorForSelectedText(colorId: Int) = with(binding) {
        Log.d("NewNoteActivity", "setBoldForSelectedText")

        val startPos = edDescription.selectionStart
        val endPos = edDescription.selectionEnd
        val styles = edDescription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)

        if (styles.isNotEmpty()) edDescription.text.removeSpan(styles[0])
        edDescription.text.setSpan(ForegroundColorSpan(ContextCompat.getColor(this@NewNoteActivity, colorId)),
            startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDescription.text.trim()
        edDescription.setSelection(startPos)
    }
    private fun setMainResult() {               //Передаем информацию через Интент
        Log.d("NewNoteActivity", "setMainResult")

        var editState = ""
        val tempNote: NoteItem? = if (note == null) {
            Log.d("NewNoteActivity", "setMainResult, note == null")
            editState = "new"
            createNewNote()
        } else {
            Log.d("NewNoteActivity", "setMainResult, note != null")

            editState = "update"
            updateNote()
        }
        val i = Intent().apply {
                putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
                putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun updateNote() : NoteItem? = with(binding) {
        Log.d("NewNoteActivity", "updateNote")

        return note?.copy(
            title = edTitle.text.toString(),
            content = HtmlManager.toHtml(edDescription.text)
        )
    }

    private fun createNewNote(): NoteItem {
        Log.d("NewNoteActivity", "createNewNote")

        return NoteItem(null,
        binding.edTitle.text.toString(),
        HtmlManager.toHtml(binding.edDescription.text),
        TimeManager.getCurrentTime(),
        "")
    }

    private fun actionBarSettings() {       //Кнопка назад в баре
        Log.d("NewNoteActivity", "actionBarSettings")

        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openColorPicker() {
        Log.d("NewNoteActivity", "openColorPicker")

        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {
        Log.d("NewNoteActivity", "closeColorPicker")

        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        binding.colorPicker.startAnimation(openAnim)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
        pref = PreferenceManager.getDefaultSharedPreferences(this)
    }

    private fun actionMenuCallback() {
        val actionCallback = object: ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }
            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }
            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return true
            }
            override fun onDestroyActionMode(p0: ActionMode?) {
            }
        }
        binding.edDescription.customSelectionActionModeCallback = actionCallback
    }

    private fun setTextSize() = with(binding){
        edTitle.setTextSize(pref?.getString("title_size_key", "16"))
        edTitle.setTextSize(pref?.getString("content_size_key", "14"))
    }

    private fun EditText.setTextSize(size: String?) {
        if (size != null) this.textSize = size.toFloat()
    }
}