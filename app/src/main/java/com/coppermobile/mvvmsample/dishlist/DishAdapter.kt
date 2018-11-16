package com.coppermobile.mvvmsample.dishlist

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.coppermobile.mvvmsample.R
import com.coppermobile.mvvmsample.models.DishResponse
import com.coppermobile.mvvmsample.utils.IClickListener

class DishAdapter(context: Context, iClickListener: IClickListener) : RecyclerView.Adapter<DishAdapter.MyViewHolder>() {

    var context: Context?
    var iClickListener: IClickListener?
    private var dishList: List<DishResponse>? = null

    init {
        this.context = context
        this.iClickListener = iClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_dish, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (dishList != null) {
            return dishList?.size!!
        }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dishResponse = dishList?.get(position)
        if (dishResponse != null) {
            Glide.with(context)
                    .load(dishResponse.thumbnail)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.image_not_found)
                    .into(holder.ivDish)
            holder.tvName?.setText(dishResponse.name)
            holder.tvDescription?.setText(dishResponse.description)
            holder.tvPrice?.setText("₹ " + dishResponse.price)
        }
        holder.rlContainer?.setOnClickListener({ v ->
            holder.itemView.setBackgroundResource(R.drawable.item_clicked)
            if (iClickListener != null) {
                if (dishResponse != null) {
                    iClickListener?.onItemPressed(dishResponse)
                }
            }
        })
    }

    fun setDishList(dishList: List<DishResponse>?) {
        this.dishList=dishList
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @BindView(R.id.iv_dish)
        lateinit var ivDish: ImageView
        @BindView(R.id.tv_name)
        lateinit var tvName: TextView

        @BindView(R.id.tv_description)
        lateinit var tvDescription: TextView
        @BindView(R.id.tv_price)
        lateinit var tvPrice: TextView

        @BindView(R.id.rl_container)
        lateinit var rlContainer: RelativeLayout

        init {
            ButterKnife.bind(this, v)
        }
    }
}