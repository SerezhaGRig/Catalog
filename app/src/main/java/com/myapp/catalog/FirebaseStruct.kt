package com.myapp.catalog

import android.net.Uri
import android.os.Build
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

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
        fun selectWorks(
            category: String?,
            recyclerView: RecyclerView,
            activity: FragmentActivity?
        ):Products{
            val products=Products()

            val filtered = if(category.equals("All")){
                Firebase.database.reference.child("works").get()
            } else{
                Firebase.database.reference.child("works").orderByChild("category").equalTo(category).get()
            }
            val works = filtered.addOnSuccessListener { it1 ->
                it1?.let { it ->
                    //it.children.forEach { item ->
                        val res=it.getValue(Work::class.java)
                        if (res!=null) {
                            val pr = Product(
                                res.name.toString(),
                                res.description.toString(),
                                res.category.toString(),
                                res.cost.toString(),

                                arrayListOf(
                                    res.image1.toString(),
                                    res.image2.toString(),
                                    res.image3.toString()
                                )
                            )
                            pr.id = it.key
                            products.prList.add(pr)
                            val viewAdapter = category?.let { activity?.let { it1 -> MyAdapter(products, it1,category) } }
                            recyclerView.adapter=viewAdapter
                            viewAdapter?.notifyDataSetChanged()
                        }
                  //  }


                }



            }
            return products
        }

        fun selectWorks(category: String?, pagerView: ViewPager?, activity: ScrollingActivity, inx:Int): Products {
            val products=Products()

            val filtered = if(category.equals("All")){
                Firebase.database.reference.child("works").get()
            } else{
                Firebase.database.reference.child("works").orderByChild("category").equalTo(category).get()
            }
            val works = filtered.addOnSuccessListener { it1 ->
                it1?.let { it ->
                    //it.children.forEach { item ->
                    val res=it.getValue(Work::class.java)
                    if (res!=null) {
                        val pr = Product(
                            res.name.toString(),
                            res.description.toString(),
                            res.category.toString(),
                            res.cost.toString(),

                            arrayListOf(
                                res.image1.toString(),
                                res.image2.toString(),
                                res.image3.toString()
                            )
                        )
                        pr.id = it.key
                        products.prList.add(pr)
                        val viewPager = activity.findViewById<ViewPager>(R.id.viewPager)
                        val cost = activity.findViewById<TextView>(R.id.prCost)
                        val name = activity.findViewById<TextView>(R.id.prName)
                        val desc = activity.findViewById<TextView>(R.id.desc)
                        val id = activity.findViewById<TextView>(R.id.id)
                        val params = viewPager.layoutParams as LinearLayout.LayoutParams
                        id.text = "id="+products.prList[inx].id
                        cost.text = products.prList[inx].cost
                        name.text = products.prList[inx].name
                        desc.text = products.prList[inx].description
                        if(products.prList[inx].description.equals(""))
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                desc.background = null
                            }
                        val viewAdapter = CustomPagerAdapter(activity,products,inx)
                        if (pagerView != null) {
                            pagerView.adapter=viewAdapter
                            viewAdapter.notifyDataSetChanged()
                        }

                    }
                    //  }


                }



            }
            return products
        }

    }
}
