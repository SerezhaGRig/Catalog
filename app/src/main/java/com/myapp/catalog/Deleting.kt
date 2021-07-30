package com.myapp.catalog

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Deleting : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deleteing)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        findViewById<Button>(R.id.button5).setOnClickListener(this)
    }



    override fun onClick(v: View?) {
        val id = findViewById<TextView>(R.id.del_id)
        //val db = ProductsDatabase(this)
        //db.deleteProduct(id.text.toString())
        Work.delete(id.text.toString())
    }

}
