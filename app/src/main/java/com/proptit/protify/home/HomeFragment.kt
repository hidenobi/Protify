package com.proptit.protify.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proptit.protify.databinding.FragmentHomeBinding
import com.proptit.protify.extensions.hide
import com.proptit.protify.extensions.show
import com.proptit.protify.models.Data
import com.proptit.protify.models.Track
import com.proptit.protify.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val apiKey = ""
const val apiHost = ""
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MusicAdapter
    private lateinit var listData: List<Track>
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        getListMusic()
        initComponent()

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            if (text != null){
                adapter.filterData(text.toString())
            }
        }
        binding.edtSearch.doBeforeTextChanged { _, _, _, _ -> adapter.setData(listData) }
        return binding.root
    }

    private fun initComponent() {
        listData = ArrayList()
        recyclerView = binding.recyclerview
        adapter = MusicAdapter(requireContext(), listData)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        toolbar = binding.toolbar

    }

    private fun getListMusic() {
        binding.progressBar.show()
        binding.recyclerview.hide()
        val call = ApiClient.apiService.getListMusic("eminem",apiKey, apiHost)
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    val dataList = response.body()?.data
                    if (dataList != null) {
                        listData = dataList
                        adapter.setData(dataList)
                    }
                    Log.d( "onResponse: ", response.body().toString())
                } else {
                    Log.d( "onResponse: ", response.errorBody().toString())
                }
                binding.progressBar.hide()
                binding.recyclerview.show()
            }
            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.d( "onResponse: ",t.toString())
                binding.progressBar.hide()
                binding.recyclerview.show()
            }
        })
    }

}