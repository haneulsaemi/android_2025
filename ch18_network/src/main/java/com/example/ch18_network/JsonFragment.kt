package com.example.ch18_network

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_network.databinding.FragmentJsonBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JsonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JsonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    val TAG = "25android"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentJsonBinding.inflate(inflater, container, false)

        val loc = arguments?.getString("searchLoc") ?: "108"

        Log.d(TAG, loc)

        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val today = dateFormat.format(System.currentTimeMillis())

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -3)
        val three_ago = dateFormat.format(cal.time)

        val call : Call<JsonResponse> = RetrofitConnection.jsonNetworkService.getJsonList(
            loc.toInt(),
            1,
            10,
            "json",
            "jbGHJMq1TOHOzoWuOUcatgnyzGotAsC4yLEGOdHKw3GGF2RHmcgtdwINtemfO+7hzBtF/fysjVsDm1+GGUU5nQ==",
            three_ago,today
        )
        call?.enqueue(object : Callback<JsonResponse>{
            override fun onResponse(call: Call<JsonResponse>, response: Response<JsonResponse>) {
                if(response.isSuccessful){
                    Log.d(TAG, "${response}")
                    Log.d(TAG, "${response.body()}")

                    binding.jsonRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.jsonRecyclerView.adapter = JsonAdapter(response.body()!!.response.body.items.item)
                    binding.jsonRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

                }
            }

            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JsonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JsonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}