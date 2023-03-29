package com.neotica.submissiondicodingawal.main.fragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.databinding.IvUserListBinding
import com.neotica.submissiondicodingawal.response.GithubResponseItem
import com.neotica.submissiondicodingawal.room.Entity


class FavoriteAdapter(private val onFavClick: (Entity) -> Unit) :
    ListAdapter<Entity, FavoriteAdapter.FavViewHolder>(DIFF_CALLBACK) {

    class FavViewHolder(val binding: IvUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entity: Entity){
            binding.apply {
                tvUsername.text = entity.username
                Glide.with(root)
                    .load(entity.imageUrl)
                    .into(ivProfile)
                ivProfile
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = IvUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Entity> =
            object : DiffUtil.ItemCallback<Entity>() {
                override fun areItemsTheSame(oldUser: Entity, newUser: Entity): Boolean {
                    return oldUser.username == newUser.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: Entity, newUser: Entity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}