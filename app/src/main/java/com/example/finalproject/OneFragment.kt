package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.FragmentOneBinding
import com.example.finalproject.databinding.ItemRecyclerviewBinding
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class OneFragment : Fragment() {
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentOneBinding.inflate(inflater, container, false)

        var datas = mutableListOf<String>()
        val db = DBHelper(inflater.context).readableDatabase
        val cursor = db.rawQuery("select * from TODO_TB", null)
        cursor.run {
            while(moveToNext()){
                datas.add(cursor.getString(1))
            }
        }
        db.close()
//        for(i in 1..10){
//            datas.add("item $i")
//        }

        val radapter = MyAdapter(datas)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)//GridLayoutManager(activity,2)
        binding.recyclerView.adapter = radapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        val requestLauncher:ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                val todo = it.data!!.getStringExtra("result")
                if(todo != ""){
                    datas.add(todo!!)
                    radapter.notifyDataSetChanged()
                }
        }
        binding.fab.setOnClickListener{
//            datas.add("Item 100")
//            radapter.notifyDataSetChanged()
            val intent = Intent(it.context, AddActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            intent.putExtra("today", dateFormat.format(System.currentTimeMillis()))
            //startActivity(intent)
            requestLauncher.launch(intent)
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}