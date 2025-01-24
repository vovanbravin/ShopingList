package com.example.shopinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopinglist.Constances
import com.example.shopinglist.R
import com.example.shopinglist.activities.MainApp
import com.example.shopinglist.activities.NewNoteActivity
import com.example.shopinglist.adapters.NoteAdapter
import com.example.shopinglist.databinding.FragmentNoteBinding
import com.example.shopinglist.entities.NoteItem
import com.example.shopinglist.viewModels.MainViewModel


class NoteFragment : BaseFragment(), NoteAdapter.Listener {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter
    private val mainViewModel: MainViewModel by activityViewModels{
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater,container,false)
        onEditLauncher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecycleView()
        observer()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }

    private fun observer()
    {
        mainViewModel.NoteList.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }


    private fun createRecycleView() = with(binding)
    {
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteAdapter(this@NoteFragment)
        rcViewNote.adapter = adapter
    }

    fun onEditLauncher()
    {
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            val mode = it.data?.getStringExtra(Constances.MODE_KEY)
            if(it.resultCode == Activity.RESULT_OK && mode == "new")
            {
                mainViewModel.insertNote(it.data?.getSerializableExtra(Constances.NEW_NOTE_KEY) as NoteItem)
            }
            else if(it.resultCode == Activity.RESULT_OK && mode == "update")
            {
                Log.d("MyLog","${it.data?.getSerializableExtra(Constances.NEW_NOTE_KEY) as NoteItem}")
                mainViewModel.updateNote(it.data?.getSerializableExtra(Constances.NEW_NOTE_KEY) as NoteItem)
            }
        }
    }

    override fun deleteNote(id: Int) {
        mainViewModel.deleteNote(id)
    }

    override fun udpateNote(noteItem: NoteItem) {
        val intent = Intent(activity, NewNoteActivity::class.java).apply {
            putExtra(Constances.NEW_NOTE_KEY, noteItem)
        }
        editLauncher.launch(intent)
    }


}