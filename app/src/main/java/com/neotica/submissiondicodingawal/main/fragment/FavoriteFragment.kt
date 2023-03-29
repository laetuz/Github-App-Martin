package com.neotica.submissiondicodingawal.main.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.R
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.main.fragment.adapter.FragmentType
import com.neotica.submissiondicodingawal.main.fragment.adapter.UserAdapter
import com.neotica.submissiondicodingawal.mvvm.GithubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class FavoriteFragment : Fragment() {
    private lateinit var binding: RvUserListBinding
    private lateinit var itemList: IvUserListBinding

    private val viewModel : GithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = RvUserListBinding.inflate(layoutInflater, container, false)
        itemList = IvUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        getUserViewModel()
    }

    private fun getUserViewModel(){
       // val adapter =
        binding.progressBar.isVisible = true
        viewModel.getFavorite().observe(viewLifecycleOwner){
            binding.progressBar.isVisible = false
        }
        binding.apply {  }
    }
    private fun bindData(listUser: GithubResponseItem) {
        itemList.apply {
            tvUsername.text = listUser.login
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            Glide.with(root)
                .load(listUser.avatar_url)
                .into(ivProfile)
        }
    }

    private fun setRecView(listData: List<GithubResponseItem>?) {
        val adapter = listData?.let { UserAdapter(it, FragmentType.USERS_FRAGMENT) }
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

}