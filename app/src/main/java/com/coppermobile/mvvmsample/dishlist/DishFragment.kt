package com.coppermobile.mvvmsample.dishlist


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import com.coppermobile.mvvmsample.R
import com.coppermobile.mvvmsample.dishListRoom.Dish
import com.coppermobile.mvvmsample.models.DishResponse
import com.coppermobile.mvvmsample.ui.MainActivity
import com.coppermobile.mvvmsample.utils.Constants
import com.coppermobile.mvvmsample.utils.IClickListener


/**
 * A simple [Fragment] subclass.
 *
 */
class DishFragment : Fragment() {

    @BindView(R.id.rv_dish)
    lateinit var rvDish: RecyclerView



    private lateinit var dishAdapter: DishAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_dish, container, false)

        ButterKnife.bind(this, view)
        setUpRecyclerView()
        val viewModel = ViewModelProviders.of(this).get(DishViewModel::class.java)
        observeViewModel(viewModel)
        return view
    }

    private fun observeViewModel(viewModel: DishViewModel) {
        viewModel.getDishListObservable().observe(this, object : Observer<List<Dish>> {
            override fun onChanged(dishes: List<Dish>?) {
                dishAdapter.setDishList(dishes)
            }
        })
    }

    fun setUpRecyclerView() {
        dishAdapter = DishAdapter(context!!, dishClickCallback)
        rvDish?.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvDish?.setLayoutManager(linearLayoutManager)
        rvDish?.setAdapter(dishAdapter)
    }


    private val dishClickCallback = object : IClickListener {
        override fun onItemPressed(dishResponse: Dish) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(dishResponse)
            }
        }
    }



}
