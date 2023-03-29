package com.neotica.submissiondicodingawal.main.fragment.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import java.util.*

class FollowingAdapter(private val users: List<GithubResponseItem>) :
    RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {

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
        val avatar = users[position].avatar_url
        val username = users[position].login
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Search $username on Home screen", Toast.LENGTH_SHORT).show()
        }
    }
}