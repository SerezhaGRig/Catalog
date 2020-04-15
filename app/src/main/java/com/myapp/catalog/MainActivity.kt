package com.myapp.catalog

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView



class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{
    companion object {
        const val MY_PERMISSIONS_REQUEST_READ_CONTACTS: Int = 1
    }
    //var first_time = true
    //var permission = false
    override fun onCreate(savedInstanceState: Bundle?) {

        //lateinit var viewAdapter: RecyclerView.Adapter<*>
        //lateinit var viewManager: RecyclerView.LayoutManager
        //lateinit var myDataset:Products
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener(this)

        //var cate = intent.getStringExtra("category")
        supportActionBar?.title=getString(R.string.title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp)
        permission()
        //if(permission) {
            val fragmentManager = this.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction();
            val fragment = CategoryFragment(getString(R.string.all))
            fragmentTransaction.add(R.id.frame_view, fragment)
            fragmentTransaction.commit()
       // }


    }

    override fun onResume() {
        super.onResume()
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction();
        val fragment = CategoryFragment(getString(R.string.all))
        fragmentTransaction.replace(R.id.frame_view, fragment)
        fragmentTransaction.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId)
        {
            android.R.id.home ->{
                val dr = findViewById<DrawerLayout>(R.id.drawer)
                dr.openDrawer(GravityCompat.START)
                return true
            }
            R.id.add->{
                val intent = Intent(this,Adding::class.java)
                startActivity(intent)
            }
            R.id.del->{
                val intent = Intent(this,Deleting::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_manu, menu)
        return true
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var categ:String = getString(R.string.all)
        when(item.itemId){
            R.id.All->categ = getString(R.string.all)
            R.id.Tevnoc->categ = getString(R.string.tevnoc)
            R.id.Vznoc->categ = getString(R.string.vznoc)
            R.id.Matani->categ = getString(R.string.matani)
            R.id.Akanjox->categ = getString(R.string.akanjox)
            R.id.Kulon->categ = getString(R.string.kulon)
        }
            val fragmentManager = this.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction();
            val fragment = CategoryFragment(categ)
            fragmentTransaction.replace(R.id.frame_view, fragment)
            fragmentTransaction.commit()

        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show()
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    fun permission()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this, "No Permission", Toast.LENGTH_LONG).show()
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS)
                }

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
           // Toast.makeText(this, "No Permissions", Toast.LENGTH_LONG).show()
        } else {
            // Permission has already been granted
        }
    }

}
