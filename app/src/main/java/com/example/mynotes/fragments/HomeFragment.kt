package com.example.mynotes.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.adapter.NoteAdapter
import com.example.mynotes.database.NoteDatabase
import com.example.mynotes.databinding.FragmentHomeBinding
import com.example.mynotes.entities.Notes
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private var noteAdapter = NoteAdapter()
    var arrNotes = ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding = FragmentHomeBinding.bind(view)
        homeBinding.recyclerView.setHasFixedSize(true)
        homeBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        launch {
            context?.let {
                var notes = NoteDatabase.getDatabase(it).noteDao().getAllNotes()
                noteAdapter!!.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                homeBinding.recyclerView.adapter = noteAdapter
            }
        }

        noteAdapter.setOnClickListener(onClicked)

        homeBinding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var tempArr = ArrayList<Notes>()
                for (arr in arrNotes){
                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(newText.toString())){
                        tempArr.add(arr)
                    }
                }

                noteAdapter.setData(tempArr)
                noteAdapter.notifyDataSetChanged()
                return true
            }

        })

        homeBinding.fabButtonCreatenote.setOnClickListener {
            replaceFragment(CreateFragment.newInstance(), true)
        }
    }

    private val onClicked  = object : NoteAdapter.OnItemClickListener{
        override fun onClicked(notesId: Int) {
            var fragment:Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",notesId)
            fragment  = CreateFragment.newInstance()
            fragment.arguments = bundle
            replaceFragment(fragment, false)
        }

    }

    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        var fragmentTrasition = activity!!.supportFragmentManager.beginTransaction()

//        if (istransition)
//        {
//            fragmentTrasition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
//        }
        fragmentTrasition.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commitAllowingStateLoss()

    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}