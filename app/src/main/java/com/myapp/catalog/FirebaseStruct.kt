package com.myapp.catalog

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

data class Work(val name: String? = null, val description: String? = null,
                val userId: String? = null, val cost: String? = null,
                val categ: String? = null){
    companion object{
        fun addInFBase(product: Product) {
            val id = FirebaseAuth.getInstance().currentUser?.uid
            val work = Work(product.name, product.description, id, product.cost, product.category)
            val database = Firebase.database.reference
            val workRef = database.child("works")//.setValue(work)

            val key = workRef.push().key
            key?.let { workRef.child(it).setValue(work) }
            workRef.get().addOnSuccessListener {
                val list = it.getValue() as HashMap<*,*>
                Log.i("mylog",list.keys.toString())
                Log.i("mylog",list["-MSxk-daghvvBjJlaUan"].toString())
            }
    }
    }
}
