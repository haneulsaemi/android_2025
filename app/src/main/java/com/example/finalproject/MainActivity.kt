package com.example.finalproject

import android.app.SearchManager
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.R
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    val TAG = "25android"
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding : ActivityMainBinding
    lateinit var sharedPreference: SharedPreferences


    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
    //OnCreate -> onStart -> OnResume
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val idPre = sharedPreference.getString("ID", "")
        val colorPre = sharedPreference.getString("color", "#aaaaaa")

        binding.toolbar.setTitle(idPre)
        binding.toolbar.setTitleTextColor(Color.parseColor(colorPre))

        toggle = ActionBarDrawerToggle(this, binding.main, R.string.drawer_opened,
            R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter
        binding.drawer.setNavigationItemSelectedListener(this)

        TabLayoutMediator(binding.tabs, binding.viewpager){
            tab, position -> tab.text = "TAB ${position+1}"
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "search text: $query")
                var intent = Intent(Intent.ACTION_WEB_SEARCH)
                intent.putExtra(SearchManager.QUERY, query)
                startActivity(intent)
                return true
            }
        })

        menu?.add(0,1,0,"Menu1")
        menu?.add(0,2,0,"Menu2")



        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        when(item.itemId){
            R.id.menu_setting ->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.menu0 -> {
                val lat = 37.651450
                val lon = 127.016637
                var uri = Uri.parse("geo:"+ lat + "," + lon)
                var intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                true
            }
            1 -> {
                Log.d(TAG, "menu1")
                true
            }
            2-> {
                Log.d(TAG, "menu2")
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:092-901-1111")
                startActivity(intent)
                true
            }
        }

        binding.main.closeDrawers()

        return false
    }

    override fun onResume() {
        super.onResume()

        val idPre = sharedPreference.getString("ID", "")
        val colorPre = sharedPreference.getString("color", "#aaaaaa")

        binding.toolbar.title  = idPre
        binding.toolbar.setTitleTextColor(Color.parseColor(colorPre))

    }
}