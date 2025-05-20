package com.example.ch18_network

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch18_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        var jsonfragment = JsonFragment()
        var xmlfragment = XmlFragment()
        var imgFragment = ImgFragment()
        val bundle = Bundle()

        binding.btnSearch.setOnClickListener {
            val loc = binding.edtLoc.text.toString()
            if(loc == ""){
                Toast.makeText(this, "지역 ID를 입력하세요. 예: 서울은 108, 제주는 184", Toast.LENGTH_SHORT).show()
            }
            else {
                bundle.putString("searchLoc", binding.edtLoc.text.toString())

                if (binding.rGroup.checkedRadioButtonId == R.id.rbJson) {
                    jsonfragment = JsonFragment()
                    jsonfragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, jsonfragment)
                        .commit()
                } else if (binding.rGroup.checkedRadioButtonId == R.id.rbXml) {
                    xmlfragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, xmlfragment)
                        .commit()
                } else if (binding.rGroup.checkedRadioButtonId == R.id.rbImg) {
                    imgFragment = ImgFragment()
                    imgFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_content, imgFragment)
                        .commit()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.menu_login -> {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}