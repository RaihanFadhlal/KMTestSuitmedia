package com.example.kmtestsuitmedia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.kmtestsuitmedia.data.remote.DataItem
import com.example.kmtestsuitmedia.databinding.ItemUserBinding

class UserAdapter(var userList: List<DataItem>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: DataItem) {
            binding.userName.text = user.firstName + " " + user.lastName
            binding.userEmail.text = user.email

            // Load avatar image using Glide
            Glide.with(binding.root)
                .load(user.avatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.userPhoto)

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(user.firstName + " " + user.lastName)
            }
        }
    }

    fun addData(newData: List<DataItem>) {
        val updatedList = userList.toMutableList()
        updatedList.addAll(newData)
        userList = updatedList
        notifyDataSetChanged()
    }

    fun clearData() {
        userList = emptyList()
        notifyDataSetChanged()
    }
}
