package com.neotica.submissiondicodingawal.main.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.neotica.submissiondicodingawal.databinding.RvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.main.fragment.adapter.FavoriteAdapter
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
        val gitAdapter = FavoriteAdapter {
            if (it.isBookmarked){viewModel.deleteFavorite(it)}
            else {viewModel.setFavorite(it,true)}
        }
        binding.progressBar.isVisible = true
        viewModel.getFavorite().observe(viewLifecycleOwner){
            binding.progressBar.isVisible = false
            gitAdapter.submitList(it)
        }
        binding.apply {
            rvHomeList.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gitAdapter
            }
        }
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
        val adapter = listData?.let { UserAdapter(it) }
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