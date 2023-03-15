package com.neotica.submissiondicodingawal.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.main.MainAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import com.neotica.submissiondicodingawal.mvvm.GithubViewModelFactory
import com.neotica.submissiondicodingawal.response.GithubResponseItem

class FollowersFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private val viewModel by viewModels<GithubViewModel> { GithubViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
      //  lifecycleScope.launch { getUserViewModel() }
        getUserViewModel()
    }

    private fun showLoading(isLoading: Boolean) {
        //Step 24: Declare the condition
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getUserViewModel() {
        showLoading(true)
//        val avatar = FollowersFragmentArgs.fromBundle(arguments as Bundle).avatar.toString()
        val test = requireParentFragment().arguments?.let {
            FollowersFragmentArgs.fromBundle(it)
        }?.profile
      //  val profile = FollowersFragmentArgs.fromBundle(arguments as Bundle).profile
        if (test != null) {
            viewModel.getFollowers(test)
        }
       // Toast.makeText(context, test, Toast.LENGTH_SHORT).show()
        viewModel.githubResponse.observe(viewLifecycleOwner) { github ->
            if (github != null) {
                showLoading(false)
                setRecView(github)
            }
        }
    }

    private fun bindHEHE(){
        val avatar = FollowersFragmentArgs.fromBundle(arguments as Bundle).avatar.toString()
        val profile = FollowersFragmentArgs.fromBundle(arguments as Bundle).profile
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { MainAdapter(it) }
        binding.rvHomeList.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        val layoutManager = LinearLayoutManager(context)
        binding.apply {
            val itemDivider = DividerItemDecoration(context, layoutManager.orientation)
            rvHomeList.addItemDecoration(itemDivider)
        }
        listData?.get(0)
    }
}