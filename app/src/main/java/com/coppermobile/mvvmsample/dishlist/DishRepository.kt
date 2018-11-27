package com.coppermobile.mvvmsample.dishlist

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.coppermobile.mvvmsample.database.AppDatabase
import com.coppermobile.mvvmsample.dishListRoom.Dish
import com.coppermobile.mvvmsample.dishListRoom.DishDAO
import com.coppermobile.mvvmsample.models.DishResponse
import com.coppermobile.mvvmsample.repository.APIInterface
import com.coppermobile.mvvmsample.utils.AppExecutor
import com.coppermobile.mvvmsample.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class DishRepository private constructor(application: Application) {

    private lateinit var dishInterface: APIInterface
    private var dishDao: DishDAO
    private var appExecutors: AppExecutor
    private lateinit var mediatorLiveData: MediatorLiveData<List<Dish>>

    init {
        appExecutors = AppExecutor()
        val db = AppDatabase.getInstance(application)
        dishDao = db.dishDaoMethod()
    }

    fun getDishList(): LiveData<List<Dish>> {

        mediatorLiveData = MediatorLiveData()
        var allDishesFromDao: LiveData<List<Dish>> = dishDao.getAllDishes()
        mediatorLiveData.addSource(allDishesFromDao, object : Observer<List<Dish>> {
            override fun onChanged(t: List<Dish>?) {
                mediatorLiveData.removeSource(allDishesFromDao)
                if (allDishesFromDao.value != null) {
                    if (allDishesFromDao.value == null || allDishesFromDao.value!!.isEmpty()) {
                        fetchDishes(allDishesFromDao)
                    } else {

                        mediatorLiveData.addSource(allDishesFromDao, object : Observer<List<Dish>> {
                            override fun onChanged(dishesFromDao: List<Dish>?) {
                                mediatorLiveData.value = dishesFromDao
                            }

                        })
                    }
                }
            }

        })
        return mediatorLiveData
    }

    fun getAllDishesFromServer(): LiveData<List<Dish>> {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        dishInterface = retrofit.create<APIInterface>(APIInterface::class.java)

        val mutableLiveData = MutableLiveData<List<Dish>>()
        dishInterface?.getDishes()?.enqueue(object : Callback<List<DishResponse>> {
            override fun onFailure(call: Call<List<DishResponse>>?, t: Throwable?) {
                mutableLiveData.value = null
            }

            override fun onResponse(call: Call<List<DishResponse>>?, response: Response<List<DishResponse>>?) {

                if (response?.body() != null) {
                    val dishResponseList = response.body()
                    if (dishResponseList != null) {
                        val dishList = ArrayList<Dish>()
                        var dish: Dish
                        for (i in 0..dishResponseList.size - 1) {
                            dish = Dish(dishResponseList.get(i).id, dishResponseList.get(i).thumbnail
                                    , dishResponseList.get(i).name, dishResponseList.get(i).description, dishResponseList.get(i).price.toString())
                            dishList.add(dish)
                        }
                        mutableLiveData.value = dishList
                    }
                }
            }

        })

        return mutableLiveData
    }

    fun fetchDishes(dishData: LiveData<List<Dish>>) {
        var allDishes = getAllDishesFromServer()
        mediatorLiveData.addSource(allDishes, object : Observer<List<Dish>> {
            override fun onChanged(dishList: List<Dish>?) {
                mediatorLiveData.removeSource(allDishes)
                mediatorLiveData.removeSource(dishData)

                if (dishList != null) {
                    insert(dishList)
                    mediatorLiveData.addSource(dishDao.getAllDishes(), object : Observer<List<Dish>> {
                        override fun onChanged(dishListDao: List<Dish>?) {
                            mediatorLiveData.value = dishListDao
                        }

                    })
                }
            }
        })

    }

    fun insert(dish: List<Dish>) {
        var runnable = object : Runnable {
            override fun run() {
                dishDao.insertData(dish)
            }
        }
        appExecutors.diskIo.execute(runnable)
    }

    companion object {

        private var INSTANCE: DishRepository? = null

        fun getInstance(application: Application): DishRepository {
            if (INSTANCE == null) {
                synchronized(DishRepository::class.java) {
                    INSTANCE = DishRepository(application)
                }
            }
            return INSTANCE!!
        }
    }


}