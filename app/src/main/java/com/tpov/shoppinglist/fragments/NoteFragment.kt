package com.tpov.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tpov.shoppinglist.MainApp
import com.tpov.shoppinglist.NewNoteActivity
import com.tpov.shoppinglist.databinding.FragmentNoteBinding
import com.tpov.shoppinglist.db.MainViewModel
import com.tpov.shoppinglist.db.NoteAdapter
import com.tpov.shoppinglist.entities.NoteItem
import kotlinx.coroutines.InternalCoroutinesApi as InternalCoroutinesApi1

@kotlinx.coroutines.InternalCoroutinesApi
class NoteFragment : BaseFragment(), NoteAdapter.Listener {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter
    private lateinit var defPref: SharedPreferences

    @InternalCoroutinesApi1
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        Log.d("NoteFragment", "onClickNew")

        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    @OptIn(kotlinx.coroutines.InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("NoteFragment", "onCreate")

        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        defPref = PreferenceManager.getDefaultSharedPreferences(activity)
        rcViewNote.layoutManager = getLayoutManager()
        adapter = NoteAdapter(this@NoteFragment, defPref)
        rcViewNote.adapter = adapter
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return if (defPref.getString("note_style_key", "Linear") == "Linear") {
            LinearLayoutManager(activity)
        } else {
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    @kotlinx.coroutines.InternalCoroutinesApi
    private fun observer() {
        mainViewModel.allNotes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onEditResult() {
        Log.d("NoteFragment", "onEditResult")

        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {            //Проверка был ли передан результат
                Log.d("NoteFragment", "it.resultCode == Activity.RESULT_OK")

                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if (editState == "new") {           //Передаем целый класс
                    Log.d("NoteAdapter_itemView", "editState == new")
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                } else {
                    Log.d("NoteAdapter_itemView", "editState != new")
                    mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                }
            }
        }
    }

    override fun deleteItem(id: Int) {
        mainViewModel.deleteNote(id)
    }

    override fun onClickItem(note: NoteItem) {
        Log.d("NoteAdapter_itemView", "onClickItem")
        val intent = Intent(activity, NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, note)
        }
        editLauncher.launch(intent)
    }

    companion object {
        const val NEW_NOTE_KEY = "new note key"
        const val EDIT_STATE_KEY = "edit state key"

        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}