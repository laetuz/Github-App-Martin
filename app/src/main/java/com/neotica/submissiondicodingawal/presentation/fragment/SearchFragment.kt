package com.neotica.submissiondicodingawal.presentation.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.presentation.fragment.adapter.SearchAdapter
import com.neotica.submissiondicodingawal.presentation.viewmodel.GithubViewModel
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoadingSearch.collect{
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchResponse.collect {
                if (it != null) {
                    showLoading(false)
                    setSearchRecView(it)
                }
            }
        }
    }

    private fun setSearchRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { SearchAdapter(it) }
        binding.apply {
            rvHomeList.adapter = adapter
            rvHomeList.layoutManager = LinearLayoutManager(context)
        }
    }
}