package com.neotica.submissiondicodingawal.main.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.neotica.submissiondicodingawal.R
import com.neotica.submissiondicodingawal.main.fragment.adapter.UserFragmentAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import com.neotica.submissiondicodingawal.mvvm.GithubViewModelFactory

class SearchFragment : Fragment() {
    private lateinit var binding: RvUserListBinding

    private val viewModel by viewModels<GithubViewModel> { GithubViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = RvUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getUserViewModel()
        searchUser()
    }

    private fun showLoading(isLoading: Boolean) {
        //Step 24: Declare the condition
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getUserViewModel(){
        val searchArgs = SearchFragmentArgs.fromBundle(arguments as Bundle).profile
        Toast.makeText(context, searchArgs, Toast.LENGTH_SHORT).show()
        showLoading(true)
        viewModel.getUser()
        viewModel.githubResponse.observe(viewLifecycleOwner) { github ->
            if (github != null) {
                showLoading(false)
                setRecView(github)
            }
        }
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { UserFragmentAdapter(it) }
        binding.rvHomeList.apply {
            layoutManager=LinearLayoutManager(context)
            this.adapter = adapter
        }
        val layoutManager = LinearLayoutManager(context)
        binding.apply {
            val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
        }
        listData?.get(0)
    }
    /*---------------SEARCH----------------*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(androidx.appcompat.R.string.abc_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val search = query.toString()
                val navController = Navigation.findNavController(binding.root)
                Toast.makeText(context, search, Toast.LENGTH_SHORT).show()
                val sendBundle = UserFragmentDirections.actionUserFragmentSelf()
                navController.navigate(UserFragmentDirections.actionUserFragmentSelf())

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun searchUser(){
        val searchArgs = SearchFragmentArgs.fromBundle(arguments as Bundle).profile
        Toast.makeText(context, searchArgs, Toast.LENGTH_SHORT).show()
        viewModel.getSearch(searchArgs)
        viewModel.githubResponse.observe(viewLifecycleOwner) {github ->
            if (github != null) {
                showLoading(false)
                setSearchRecView(github)
            }
        }
    }

    private fun setSearchRecView(listData: List<GithubResponseItem>?){
        val adapter = listData?.let { UserFragmentAdapter(it) }
        binding.apply {
            rvHomeList.adapter = adapter
            rvHomeList.layoutManager = LinearLayoutManager(context)
        }
        listData?.get(0)
    }
}