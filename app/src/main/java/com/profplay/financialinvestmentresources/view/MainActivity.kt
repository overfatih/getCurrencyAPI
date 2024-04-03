package com.profplay.financialinvestmentresources.view

import android.os.Bundle
import android.util.ArrayMap
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.profplay.financialinvestmentresources.R
import com.profplay.financialinvestmentresources.adapter.RecyclerViewAdapter
import com.profplay.financialinvestmentresources.databinding.ActivityMainBinding
import com.profplay.financialinvestmentresources.model.CurrencyModel
import com.profplay.financialinvestmentresources.service.CurrencyAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://api.genelpara.com/"
    private var currencies : ArrayMap<String,CurrencyModel>? = null
    private var recyclerViewAdapter:RecyclerViewAdapter? = null

    private var compositeDisposable: CompositeDisposable? = null

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

        compositeDisposable = CompositeDisposable()
        loadCurrency()
    }

    private fun loadCurrency(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(CurrencyAPI::class.java)

        compositeDisposable?.add(retrofit.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(currencies: ArrayMap<String, CurrencyModel>){
        recyclerViewAdapter= RecyclerViewAdapter(currencies)
        binding.recyclerView.adapter = recyclerViewAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}