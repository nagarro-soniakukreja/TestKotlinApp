package com.example.testkotlinapp.view.ui

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testkotlinapp.R
import com.example.testkotlinapp.data.api.APIHelperImp
import com.example.testkotlinapp.data.api.RetrofitBuilder
import com.example.testkotlinapp.view.adapter.ApiUserTodoAdapter
import com.example.testkotlinapp.view.callback.ViewInterface
import com.example.testkotlinapp.viewmodel.TodoViewModel
import com.example.testkotlinapp.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.skeleton_layout.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class TodoListActivity : AppCompatActivity() , ViewInterface, CoroutineScope {

    companion object { //to make it constant and cannot have multiple companion object in 1 class
        private val TAG: String = TodoListActivity::class.java.simpleName
    }
    private lateinit var adapter: ApiUserTodoAdapter
    private lateinit var viewModel: TodoViewModel
    private lateinit var job: Job
    private val inflater: LayoutInflater by lazy {
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
    }


    private val handler =
        CoroutineExceptionHandler { _, exception -> Log.d(TAG, "$exception handled!") }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        job = Job()
        setToolbar()
        setupUI()
        showSkeleton(true)
        setupViewModel()
        fetchViewModelData()
        setupObserver()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun showSkeleton(show : Boolean) {
        if (show) {
            skeletonLayout.removeAllViews()
            val skeletonRows = getSkeletonRowCount()
            for (i in 0..skeletonRows) {
                val rowLayout = inflater
                    .inflate(R.layout.skeleton_row_layout,skeletonLayout,false)
                skeletonLayout.addView(rowLayout)
            }

            skeletonLayout.visibility = View.VISIBLE;
            skeletonLayout.bringToFront();
        } else {
            skeletonLayout.visibility = View.GONE
        }
    }

    private fun getSkeletonRowCount() : Int {
        val pxHeight: Int = getDeviceHeight()
        val skeletonRowHeight = resources
            .getDimension(R.dimen.row_layout_height).toInt() //converts to pixel

        return Math.ceil(pxHeight / skeletonRowHeight.toDouble()).toInt()
    }

    private fun getDeviceHeight(): Int {
        val resources: Resources = this.resources
        val metrics: DisplayMetrics = resources.getDisplayMetrics()
        return metrics.heightPixels
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ApiUserTodoAdapter(
            arrayListOf()
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.getTodoList().observe(this, Observer { todoList ->
            todoList?.let {
                animateReplaceSkeleton(recyclerView)
                adapter.setData(todoList)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.getTodoListError().observe(this, Observer { error ->
            if (error) {
                errorScreen.visibility = View.VISIBLE
            } else {
                errorScreen.visibility = View.GONE
            }
        })
    }

    private fun animateReplaceSkeleton(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.animate().alpha(1f).setDuration(1000).start()

        skeletonLayout.animate().alpha(0f).setDuration(1000).withEndAction( Runnable {
            showSkeleton(false)
        }).start()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(APIHelperImp(RetrofitBuilder.apiService
        ))).get(TodoViewModel::class.java)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    fun reloadClick(view: View) {
        fetchViewModelData()
    }

    private fun fetchViewModelData() {
        viewModel.fetchData()
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}
