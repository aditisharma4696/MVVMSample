package com.coppermobile.mvvmsample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.coppermobile.mvvmsample.R
import com.coppermobile.mvvmsample.dishdetail.DishDetailFragment
import com.coppermobile.mvvmsample.dishlist.DishFragment
import com.coppermobile.mvvmsample.models.DishResponse
import com.coppermobile.mvvmsample.utils.Constants

class MainActivity : AppCompatActivity() {

    @BindView(R.id.fragment_container)
    lateinit var container: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        if (savedInstanceState == null) {
            val fragment = DishFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, Constants.DishListFragmentTag).commit()
        }

    }

    fun show(dish: DishResponse) {
        val dishDeatilFragment = DishDetailFragment.newInstance(dish)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container,
                        dishDeatilFragment, Constants.DishDetailFragmentTag).commit()
    }

}
