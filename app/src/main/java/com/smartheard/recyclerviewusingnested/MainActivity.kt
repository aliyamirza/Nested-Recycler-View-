package com.smartheard.recyclerviewusingnested

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , Adapter.onItemClickListener {
    lateinit  var recyclerViewAdapter : Adapter
    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        localData()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(applicationContext,DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = Adapter(this@MainActivity)
            adapter = recyclerViewAdapter
        }
    }
    private fun localData(){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLocListObservable().observe(this, Observer <LocationList>{
        recyclerViewAdapter.locListData=it.data.toMutableList()
            recyclerViewAdapter.notifyDataSetChanged()
        })
        viewModel.loadData(this)
    }

    override fun onClickListener(data: LocationData) {
        val intent = Intent(this,DescActivity::class.java)
        intent.putExtra("loc_data",data)
        startActivity(intent)
    }
}