package com.neotica.submissiondicodingawal.view.fragment.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.data.remote.model.GithubResponseItem
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.view.fragment.UserProfileFragmentDirections
import com.neotica.submissiondicodingawal.view.fragment.adapter.FollowersAdapter.ListViewHolder
import java.util.Locale


class FollowersAdapter(private val users: List<GithubResponseItem>) :
    RecyclerView.Adapter<ListViewHolder>() {

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
                    .load(listUser.avatar_url)
                    .circleCrop()
                    .into(ivProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(IvUserListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Log.d(TAG, "Binding item at position $position")
        holder.bindData(users[position])
        val userPosition = users[position]
        val username = userPosition.login
        val avatar = userPosition.avatar_url
        holder.itemView.setOnClickListener {
            holder.itemView.setOnClickListener {
                val action = UserProfileFragmentDirections.actionUserProfileFragmentSelf(
                    avatar, username, username, username
                )
                it.findNavController().navigate(action)
            }
        }
    }
}