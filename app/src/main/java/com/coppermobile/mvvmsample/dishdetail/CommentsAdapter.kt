package com.coppermobile.mvvmsample.dishdetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.coppermobile.mvvmsample.R
import com.coppermobile.mvvmsample.commentRoom.Comments
import com.coppermobile.mvvmsample.utils.IShowPopUpListener

class CommentsAdapter(iShowPopUpListener: IShowPopUpListener) : RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    var iShowPopUpListener: IShowPopUpListener?
    private var commentsList: ArrayList<Comments> = ArrayList()

    init {
        this.iShowPopUpListener = iShowPopUpListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_comments, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (commentsList != null && !commentsList!!.isEmpty()) commentsList!!.size else 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comments = commentsList?.get(position)
        holder.tvComments.setText(comments?.comments)
        holder.tvDate.setText(comments?.date)
        holder.ivMenu.setOnClickListener { v ->
            iShowPopUpListener?.showPopupMenu(comments!!, holder.adapterPosition, holder.ivMenu)

        }
    }

    fun addSingleComment(note: Comments) {
        commentsList?.add(note)
        notifyDataSetChanged()
    }


    fun addData(commentsList: List<Comments>) {
        this.commentsList = commentsList as ArrayList<Comments>
        notifyDataSetChanged()
    }

    fun removeComment(position: Int) {
        commentsList.removeAt(position)
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        @BindView(R.id.tv_comments)
        lateinit var tvComments: TextView
        @BindView(R.id.tv_date)
        lateinit var tvDate: TextView
        @BindView(R.id.iv_menu)
        lateinit var ivMenu: ImageView

        init {
            ButterKnife.bind(this, v)
        }
    }
}