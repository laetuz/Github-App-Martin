package com.neotica.submissiondicodingawal.view.fragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neotica.submissiondicodingawal.databinding.IvUserFavoriteBinding
import com.neotica.submissiondicodingawal.view.fragment.FavoriteFragmentDirections
import com.neotica.submissiondicodingawal.viewmodel.GithubViewModel
import com.neotica.submissiondicodingawal.data.local.database.Entity


class FavoriteAdapter(private val viewModel: GithubViewModel) :
    ListAdapter<Entity, FavoriteAdapter.FavViewHolder>(DIFF_CALLBACK) {

    class FavViewHolder(val binding: IvUserFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entity: Entity) {
            binding.apply {
                tvUsername.text = entity.username
                Glide.with(root)
                    .load(entity.imageUrl)
                    .circleCrop()
                    .into(ivProfile)
                ivProfile
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding =
            IvUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val user = getItem(position)
        val username = user.username
        val avatar = user.imageUrl
        holder.bind(user)
        holder.binding.ivFavorite.setOnClickListener {
            viewModel.deleteUser(user.username)
            Toast.makeText(holder.itemView.context, "${user.username} Deleted", Toast.LENGTH_SHORT)
                .show()
        }
        holder.itemView.setOnClickListener { view ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToUserProfileFragment(
                avatar, username, username, username
            )
            view.findNavController().navigate(action)
        }

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