package com.neotica.submissiondicodingawal.presentation.fragment.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.presentation.fragment.UserFragmentDirections
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.core.data.remote.model.GithubResponseItem
import java.util.*

class UserAdapter(private val users: List<GithubResponseItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: IvUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(listUser: GithubResponseItem) {
            binding.apply {
                tvUsername.text = listUser.login
                    .replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                Glide.with(root)
                    .load(listUser.avatarUrl)
                    .circleCrop()
                    .into(ivProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = IvUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Log.d(TAG, "Binding item at position $position")
        holder.bindData(users[position])
        val avatar = users[position].avatarUrl
        val username = users[position].login
        val followers = users[position].followersUrl
        val following = users[position].followingUrl
        holder.itemView.setOnClickListener { view ->
            val action =
                UserFragmentDirections.actionUserFragmentToUserProfileFragment(
                    avatar, username, followers, following
                )
            view.findNavController().navigate(action)
        }
    }
}