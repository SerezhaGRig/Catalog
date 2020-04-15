package com.myapp.catalog

import android.content.Context
import android.content.Intent

import android.net.Uri

import android.text.Html

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Build
import androidx.core.text.HtmlCompat


class CustomPagerAdapter(val context: Context, val dateset:Products,val index:Int) : PagerAdapter() {
    //var dpWidth by Delegates.notNull<Float>()
    lateinit var mDots:ArrayList<TextView>
    override fun getCount()=3/*dateset.prList.size*/
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
    /*init {
        val outMetrics = Resources.getSystem().displayMetrics
        val density = context.resources.displayMetrics.density
        dpWidth = outMetrics.widthPixels / density

    }*/
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //val holderLey = LayoutInflater.from(context).inflate(R.layout.view_page_pr,container,false)
        //Log.d("station","stex hasav2")
        val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val item_view = mInflater.inflate(R.layout.view_page_pr,container,false)
        val imageView = item_view.findViewById<ImageView>(R.id.imageView2)
        //var bitmap:Bitmap? = null

        when(position) {
            0-> {
                Product.setupImage(dateset.prList[index].image1,imageView,800,450,context)
                dateset.prList[index].image1?.let {
                    imageView.setOnClickListener(
                        myClick(
                            Uri.parse(dateset.prList[index].image1),
                            context
                        )
                    )
                }
            }
            1-> {
                Product.setupImage(dateset.prList[index].image2,imageView,800,450,context)
                dateset.prList[index].image2?.let {
                    imageView.setOnClickListener(
                        myClick(
                            Uri.parse(dateset.prList[index].image2),
                            context
                        )
                    )
                }

            }
            2-> {

                Product.setupImage(dateset.prList[index].image3,imageView,800,450,context)
                dateset.prList[index].image3?.let {
                    imageView.setOnClickListener(
                        myClick(
                            Uri.parse(dateset.prList[index].image3),
                            context
                        )
                    )
                }
            }
                /*bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    uri
                )*/
            }


        //?.let { Product.bitmapRes(it) })
        addsDotIndicator(position,item_view.findViewById<LinearLayout>(R.id.lin_lay))
        container.addView(item_view)
        return item_view
    }
    class myClick(val imgUri: Uri,val context: Context):View.OnClickListener{
        override fun onClick(v: View?) {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW;
            intent.setDataAndType(imgUri,"image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(intent, "View using"));
        }

    }
    fun addsDotIndicator(position: Int,linearLayout: LinearLayout)
    {
        mDots = ArrayList<TextView>()
        for(i in 0 until 3)
        {
            mDots.add(TextView(context))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mDots[i].text= Html.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            else
                mDots[i].text= Html.fromHtml("&#8226;")

            mDots[i].textSize = 35F
            mDots[i].setTextColor(context.resources.getColor(R.color.colorPrimary))
            linearLayout.addView(mDots[i])
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            mDots[i].setLayoutParams(params)
        }

        if(mDots.size>0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mDots[position].setTextColor(context.resources.getColor(R.color.black,null))
            }
            else
            {
                mDots[position].setTextColor(context.resources.getColor(R.color.black))
            }

        }

    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }


}
