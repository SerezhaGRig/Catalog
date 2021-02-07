package com.myapp.catalog

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class RegActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var login:EditText
    private lateinit var pass:EditText
    private lateinit var sign:Button
    private lateinit var reg:Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        auth = FirebaseAuth.getInstance();
        login=findViewById<EditText>(R.id.editTextTextPersonName)
        pass=findViewById<EditText>(R.id.editTextTextPassword)
        sign=findViewById<Button>(R.id.sign)
        reg=findViewById<Button>(R.id.reg)
        sign.setOnClickListener(this)
        reg.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if(p0?.id == R.id.sign)
        {
            signIn(login.getText().toString(),pass.getText().toString());
            finish()
        }else if (p0?.id == R.id.reg)
        {
            registration(login.getText().toString(),pass.getText().toString());
            finish()
        }
    }

    @SuppressLint("ShowToast")
    private fun signIn(log: String, password: String) {
        auth.signInWithEmailAndPassword(log,password).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"ok",Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(this,"sorry",Toast.LENGTH_SHORT);
                }
        }
    }
    @SuppressLint("ShowToast")
    private fun registration(log: String, password: String) {
        auth.createUserWithEmailAndPassword(log,password).addOnCompleteListener(this) {
            if(it.isSuccessful){
                Toast.makeText(this,"ok",Toast.LENGTH_SHORT);
            }
            else{
                Toast.makeText(this,"sorry",Toast.LENGTH_SHORT);
            }
        }
    }
}