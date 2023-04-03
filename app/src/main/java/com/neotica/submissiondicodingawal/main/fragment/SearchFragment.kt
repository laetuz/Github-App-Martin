package com.neotica.submissiondicodingawal.main.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.main.fragment.adapter.SearchAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private lateinit var binding: RvUserListBinding

    private val viewModel: GithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RvUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchUser()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    /*---------------SEARCH----------------*/
    private fun searchUser() {
        showLoading(true)
        val searchArgs = SearchFragmentArgs.fromBundle(arguments as Bundle).profile
        Toast.makeText(context, searchArgs, Toast.LENGTH_SHORT).show()
        viewModel.getSearch(searchArgs)
        viewModel.githubResponse.observe(viewLifecycleOwner) { github ->
            if (github != null) {
                showLoading(false)
                setSearchRecView(github)
            }
        }
    }

    private fun setSearchRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { SearchAdapter(it) }
        binding.apply {
            rvHomeList.adapter = adapter
            rvHomeList.layoutManager = LinearLayoutManager(context)
        }
        listData?.get(0)
    }
}