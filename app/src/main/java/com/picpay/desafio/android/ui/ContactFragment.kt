package com.picpay.desafio.android.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.UserListAdapter
import com.picpay.desafio.android.viewmodel.MainViewModel

class ContactFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.user_list_progress_bar)
        val messageOffline = "Dados Offline"

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        progressBar.visibility = View.VISIBLE

        viewModel.getContacts()

        viewModel.contacts.observe(viewLifecycleOwner) { users ->
            progressBar.visibility = View.GONE
            adapter.users = users
            recyclerView.visibility = View.VISIBLE
        }

        viewModel.error.observe(viewLifecycleOwner) { failure ->
            Log.e("ContactFragment", messageOffline)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE

        }
    }
}