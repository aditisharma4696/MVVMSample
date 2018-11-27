package com.coppermobile.mvvmsample.dishdetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.coppermobile.mvvmsample.R
import com.coppermobile.mvvmsample.commentRoom.Comments
import com.coppermobile.mvvmsample.dishListRoom.Dish
import com.coppermobile.mvvmsample.utils.Constants
import com.coppermobile.mvvmsample.utils.Helper
import com.coppermobile.mvvmsample.utils.IShowPopUpListener


/**
 * A simple [Fragment] subclass.
 *
 */
class DishDetailFragment : Fragment(), IShowPopUpListener {

    @BindView(R.id.iv_largeDish)
    lateinit var ivLargeDish: ImageView
    @BindView(R.id.tv_name)
    lateinit var tvName: TextView
    @BindView(R.id.tv_description)
    lateinit var tvDescription: TextView
    @BindView(R.id.tv_price)
    lateinit var tvPrice: TextView
    @BindView(R.id.rv_comments)
    lateinit var rvComments: RecyclerView
    @BindView(R.id.et_comments)
    lateinit var etComments: EditText
    @BindView(R.id.iv_postComment)
    lateinit var ivComment: ImageView


    private lateinit var dishName: String
    private lateinit var dishDescription: String
    private lateinit var dishThumbnail: String
    private lateinit var dishPrice: String
    private var dishId: Int? = null
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var commentsViewModel: CommentsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var v = inflater.inflate(R.layout.fragment_dish_detail, container, false)

        ButterKnife.bind(this, v)


        setData()
        setUpRecyclerView()

        commentsViewModel = ViewModelProviders.of(this).get(CommentsViewModel::class.java)
        observeViewModel(commentsViewModel)

        return v
    }

    fun setUpRecyclerView() {
        commentsAdapter = CommentsAdapter(this)
        rvComments.setHasFixedSize(true)
        rvComments.layoutManager = LinearLayoutManager(activity)
        rvComments.adapter = commentsAdapter
    }

    private fun observeViewModel(commentsViewModel: CommentsViewModel) {
        commentsViewModel.getAllComments(dishId!!)?.observe(this, object : Observer<List<Comments>> {
            override fun onChanged(commentsList: List<Comments>?) {
                if (commentsList != null) commentsAdapter.addData(commentsList)
            }

        })
    }

    @OnClick(R.id.iv_postComment)
    fun postCommentClicked() {
        val comments = etComments.text.toString()
        if (!TextUtils.isEmpty(comments)) {
            val comment = Comments()
            comment.comments = comments
            comment.date = Helper.formatDate()
            comment.dishID = dishId!!
            commentsViewModel.insertComment(comment)
            commentsAdapter.addSingleComment(comment)
            Toast.makeText(activity, "Comment added", Toast.LENGTH_SHORT).show()
            etComments.setText("")
            Helper.hideKbd(activity!!, view)

        }

    }

    override fun showPopupMenu(comments: Comments, position: Int, view: View) {
        val popup = PopupMenu(context!!, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.comments_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            commentsViewModel.deleteComment(comments)
            commentsAdapter.removeComment(position)
            Toast.makeText(activity, "Comment Deleted", Toast.LENGTH_SHORT).show()
            true
        }
        popup.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.dishName = it.getString(Constants.KEY_DISH_NAME)
            this.dishDescription = it.getString(Constants.KEY_DISH_DESCRIPTION)
            this.dishThumbnail = it.getString(Constants.KEY_DISH_THUMBNAIL)
            this.dishPrice = it.getString(Constants.KEY_DISH_PRICE)
            this.dishId = it.getInt(Constants.KEY_DISH_ID)
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
        fun newInstance(dish: Dish): DishDetailFragment =
                DishDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(Constants.KEY_DISH_ID, dish.dishId)
                        putString(Constants.KEY_DISH_NAME, dish.dishName)
                        putString(Constants.KEY_DISH_DESCRIPTION, dish.dishDescription)
                        putString(Constants.KEY_DISH_THUMBNAIL, dish.dishImage)
                        putString(Constants.KEY_DISH_PRICE, dish.dishPrice)

                    }
                }
    }
}
