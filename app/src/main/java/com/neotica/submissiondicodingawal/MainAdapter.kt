package com.neotica.submissiondicodingawal

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.response.UserData


class MainAdapter :
    RecyclerView.Adapter<MainAdapter.ListViewHolder>() {
    private lateinit var userList: ArrayList<GithubResponseItem>

    fun setData(listUser: ArrayList<GithubResponseItem>) {
        this.userList = listUser
    }

    class ListViewHolder(private val binding: IvUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(listUser: GithubResponseItem) {
            binding.apply {
                tvUsername.text = listUser.login
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
        return userList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindData(userList[position])
        holder.itemView.setOnClickListener {
            Log.d(TAG, "Binding item at position $position")
            Toast.makeText(holder.itemView.context, "position", Toast.LENGTH_SHORT).show()
        }
    }
}