package com.myapp.catalog

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.HashMap

data class Work(val name: String? = null, val description: String? = null,
                val userId: String? = null, val cost: String? = null,
                val category: String? = null,val image1: String? = null,
                val image2: String? = null,val image3: String? = null){
    companion object{
        fun addInFBase(product: Product) {
            val id = FirebaseAuth.getInstance().currentUser?.uid
            val work = Work(product.name, product.description, id, product.cost, product.category
                , putImage(product.image1),putImage(product.image2),putImage(product.image3))
            val database = Firebase.database.reference
            val workRef = database.child("works")
            workRef.setValue(work)
        }
        private fun putImage(image1: String?):String{
            val storageRef=FirebaseStorage.getInstance().reference
            val keyIm1 = "images/"+UUID.randomUUID().toString()
            val im1Ref=storageRef.child(keyIm1)
            val uriIm1=Uri.parse(image1)
            im1Ref.putFile(uriIm1)
            return keyIm1
        }
        fun selectWorks(category: String?):Products{
            val products=Products()

            val filtered = if(category.equals("All")){
                Firebase.database.reference.child("works").get()
            } else{
                Firebase.database.reference.child("works").orderByChild("category").equalTo(category).get()
            }
            val works = filtered.addOnSuccessListener { it ->
                it?.let { it ->
                    it.children.forEach { item ->
                        val pr=Product(
                            item.child("name").value.toString(),
                            item.child("description").value.toString(),
                            item.child("category").value.toString(),
                            item.child("cost").value.toString(),

                            arrayListOf(item.child("image1").value.toString(),
                                item.child("image2").value.toString(),
                                item.child("image3").value.toString())
                        )
                        pr.id=item.key
                        products.prList.add(pr)
                    }


                }



            }
            return products
        }
    }
}
