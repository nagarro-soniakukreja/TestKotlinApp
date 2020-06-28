package com.example.testkotlinapp.data.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testkotlinapp.R
import com.example.testkotlinapp.data.model.ApiUserTodo
import kotlinx.android.synthetic.main.layout_user_list.view.*

class ApiUserTodoAdapter(private val users: ArrayList<ApiUserTodo>): RecyclerView.Adapter<ApiUserTodoAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_user_list,
                parent,
                false))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val todo = users[position]
        holder.bind(todo)

    }

    fun setData(todoList: List<ApiUserTodo>) {
        users.addAll(todoList)
    }

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(todo: ApiUserTodo) {
            val user = "User Id : "+todo.userId
            itemView.tvUserId.text = user
            val id = "Id : "+todo.id
            itemView.tvId.text = id
            val title = "Title : "+todo.title
            itemView.tvTitle.text = title
            if (todo.completed)
                itemView.tvCompleted.setText("Completed")
            else
                itemView.tvCompleted.setText("Pending")
        }

    }
}