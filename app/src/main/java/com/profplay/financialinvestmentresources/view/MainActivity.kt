package com.profplay.financialinvestmentresources.view

import android.os.Bundle
import android.util.ArrayMap
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.profplay.financialinvestmentresources.adapter.RecyclerViewAdapter
import com.profplay.financialinvestmentresources.databinding.ActivityMainBinding
import com.profplay.financialinvestmentresources.model.CurrencyModel
import com.profplay.financialinvestmentresources.service.CurrencyAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val BASEURL = "https://api.genelpara.com/"
    private var currencies : ArrayMap<String,CurrencyModel>? = null
    private var recyclerViewAdapter:RecyclerViewAdapter? = null

    private var job : Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //https://api.genelpara.com/embed/altin.json
        //https://api.genelpara.com/embed/para-birimleri.json

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadCurrency()
    }

    private fun loadCurrency(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(CurrencyAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()

            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body().let { currencies ->
                        currencies?.let {
                            recyclerViewAdapter= RecyclerViewAdapter(it)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}