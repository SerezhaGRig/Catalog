package com.myapp.catalog

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager


import android.util.DisplayMetrics
import android.widget.LinearLayout

import android.widget.TextView


class ScrollingActivity : AppCompatActivity() {
    lateinit var myDataset:Products

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        //val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels*3/4 /// density





        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val cost = findViewById<TextView>(R.id.prCost)
        val name = findViewById<TextView>(R.id.prName)
        val desc = findViewById<TextView>(R.id.desc)
        val id = findViewById<TextView>(R.id.id)
        val params = viewPager.layoutParams as LinearLayout.LayoutParams
        params.height = (dpWidth*16 / 9).toInt()
        viewPager.layoutParams = params
        val db = ProductsDatabase(this)
        val cate =intent.getStringExtra("category")
        /*myWorkDataset = db.selectProduct(cate)
        //myWorkDataset.load()
        //Log.d("station","stex hasav")
        val inx = intent.getIntExtra("index",0)
        id.text = "id="+myWorkDataset.prList[inx].id
        cost.text = myWorkDataset.prList[inx].cost
        name.text = myWorkDataset.prList[inx].name
        desc.text = myWorkDataset.prList[inx].description
        if(myWorkDataset.prList[inx].description.equals(""))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                desc.background = null
            }
        viewPager.adapter = CustomPagerAdapter(this,myWorkDataset,inx)*/
        myDataset = db.selectProduct(cate)
        //myDataset.load()
        //Log.d("station","stex hasav")
        val inx = intent.getIntExtra("index",0)
        id.text = "id="+myDataset.prList[inx].id
        cost.text = myDataset.prList[inx].cost
        name.text = myDataset.prList[inx].name
        desc.text = myDataset.prList[inx].description
        if(myDataset.prList[inx].description.equals(""))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                desc.background = null
            }
        viewPager.adapter = CustomPagerAdapter(this,myDataset,inx)
    }
}
