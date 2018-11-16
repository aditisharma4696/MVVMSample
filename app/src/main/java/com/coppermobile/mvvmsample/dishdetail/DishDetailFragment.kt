package com.coppermobile.mvvmsample.dishdetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.coppermobile.mvvmsample.R
import com.coppermobile.mvvmsample.models.DishResponse
import com.coppermobile.mvvmsample.utils.Constants


/**
 * A simple [Fragment] subclass.
 *
 */
class DishDetailFragment : Fragment() {

    @BindView(R.id.iv_largeDish)
    lateinit var ivLargeDish: ImageView
    @BindView(R.id.tv_name)
    lateinit var tvName: TextView
    @BindView(R.id.tv_description)
    lateinit var tvDescription: TextView
    @BindView(R.id.tv_price)
    lateinit var tvPrice: TextView


    private lateinit var dishName: String
    private lateinit var dishDescription: String
    private lateinit var dishThumbnail: String
    private var dishPrice: Int? = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var v = inflater.inflate(R.layout.fragment_dish_detail, container, false)

        ButterKnife.bind(this, v)

        getBundle()
        setData()

        return v
    }

    fun getBundle() {
        val args = Bundle()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.dishName = it.getString(Constants.KEY_DISH_NAME)
            this.dishDescription = it.getString(Constants.KEY_DISH_DESCRIPTION)
            this.dishThumbnail = it.getString(Constants.KEY_DISH_THUMBNAIL)
            this.dishPrice = it.getInt(Constants.KEY_DISH_PRICE)
        }
    }

    fun setData() {
        Glide.with(context)
                .load(dishThumbnail)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.image_not_found)
                .into(ivLargeDish)
        tvName.setText(dishName)
        tvDescription.setText(dishDescription)
        tvPrice.setText("" + dishPrice)
    }


    companion object {
        @JvmStatic
        fun newInstance(dish: DishResponse): DishDetailFragment =
                DishDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(Constants.KEY_DISH_ID, dish.id)
                        putString(Constants.KEY_DISH_NAME, dish.name)
                        putString(Constants.KEY_DISH_DESCRIPTION, dish.description)
                        putString(Constants.KEY_DISH_THUMBNAIL, dish.thumbnail)
                        putInt(Constants.KEY_DISH_PRICE, dish.price)

                    }
                }

    }

}
