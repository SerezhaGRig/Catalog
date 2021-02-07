package com.myapp.catalog


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo


import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar


class Adding : AppCompatActivity(), View.OnClickListener {

    private val RESULT_LOAD_IMG1 = 1
    private val RESULT_LOAD_IMG2 = 2
    private val RESULT_LOAD_IMG3 = 3
    lateinit var product :Product
    var imageExist:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        product = Product()
        findViewById<Button>(R.id.button).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.categoryButton).setOnClickListener(this)

    }
    @SuppressLint("ShowToast")
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.button->{
                getFromGallery(RESULT_LOAD_IMG1)

            }
            R.id.button2->{getFromGallery(RESULT_LOAD_IMG2)}
            R.id.button3->{getFromGallery(RESULT_LOAD_IMG3)}
            R.id.button4->{
                val parentLayout = findViewById<View>(android.R.id.content);
                if(imageExist) {
                val name = findViewById<EditText>(R.id.name)
                val cost = findViewById<EditText>(R.id.cost)
                val description = findViewById<EditText>(R.id.description)
                val category = findViewById<TextView>(R.id.categoryTextView)
                product.name = name.text.toString()
                product.cost = cost.text.toString()
                product.description = description.text.toString()

                    product.category = category.text.toString()
                Work.addInFBase(product)
                val db = ProductsDatabase(this)
                    db.insertProduct(product)
                   // val parentLayout = findViewById<View>(android.R.id.content);
                    Snackbar.make(parentLayout,"prod created",Snackbar.LENGTH_SHORT).show()
                    //Toast.makeText(this,"prod created",Toast.LENGTH_LONG).show()
                }
                else Snackbar.make(parentLayout,"Image Doesn't selected",Snackbar.LENGTH_SHORT).show()//Toast.makeText(this,"image no selected",Toast.LENGTH_LONG)

            }
            R.id.categoryButton->{
                //Toast.makeText(this,"pressed",Toast.LENGTH_SHORT).show()

                val categs = arrayOf(getString(R.string.all),getString(R.string.vznoc),getString(R.string.tevnoc),
                    getString(R.string.kulon),getString(R.string.matani),getString(R.string.akanjox))
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Category")
                    .setItems(categs)
                    { _, which ->
                        findViewById<TextView>(R.id.categoryTextView).text = categs[which]

                    }
                    .create().show()
            }

        }
    }
    private fun getFromGallery(code:Int)
    {
        //Log.d("my","hasav")
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, code)
    }


    @SuppressLint("ShowToast")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            imageExist = true
            when (requestCode) {

                RESULT_LOAD_IMG1 -> {

                    product.image1 = data?.data.toString()

                    /*val bitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            selectedImage
                        )*/
                    //carImage.setImageBitmap(bitmap)
                }
                    RESULT_LOAD_IMG2 ->{
                        product.image2 = data?.data.toString()
                    }
                    RESULT_LOAD_IMG3 ->{
                        product.image3 = data?.data.toString()
                    }


            }
            }else Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show()

        super.onActivityResult(requestCode, resultCode, data)
    }


}
