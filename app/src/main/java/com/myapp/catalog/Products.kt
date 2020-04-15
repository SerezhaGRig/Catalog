package com.myapp.catalog



import android.content.Context

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Handler
import android.widget.ImageView

import java.io.InputStream



class Product
{
    var id: String? = null
    var name:String? =null
    var image1: String? =null
    var image2: String? =null
    var image3: String? =null
    var category: String? =null
    lateinit var description: String
    lateinit var cost:String

    constructor()
    constructor(name_a:String,description_a: String,cost_a:String,category_a:String,images:ArrayList<String>)
    {
        name = name_a
        cost = cost_a
        description = description_a
        image1 = images[0]
        image2 = images[1]
        image3 = images[2]
        category = category_a

    }
    companion object {
        /*fun bitmapRes(srcBmp:Bitmap):Bitmap
        {

            if (srcBmp.width >= srcBmp.height){


                val matrix = Matrix()
                matrix.postRotate(90F)
                return bitmapRes(Bitmap.createBitmap(
                    srcBmp,
                    0,
                    0,
                    srcBmp.width,
                    srcBmp.height,
                    matrix,
                    true
                ))

            }else{

                val height = srcBmp.width*16/9
                return Bitmap.createBitmap(
                    srcBmp,
                    0,
                    (height - srcBmp.height)/2,
                    srcBmp.width,
                    height
                )
            }

        }*/
        fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
        ): Int {
            // Raw height and width of image

            var height = options.outHeight
            var width = options.outWidth
            var inSampleSize = 1
            if (width > height) {
                val temp = width
                width = height
                height = temp
            }
            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }

        //@SuppressLint("ShowToast")
        fun decodeSampledBitmapFromResource(
            uri: Uri,
            reqWidth: Int, reqHeight: Int, context: Context
        ): Bitmap? {
            var stream: InputStream? = context.contentResolver.openInputStream(uri)
            // First decode with inJustDecodeBounds=true to check dimensions
            var bitmap:Bitmap?=null
            stream?.let {
                val options = BitmapFactory.Options()

                options.inJustDecodeBounds = true

                //BitmapFactory.decodeResource(res, resId, options)
                //options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(stream, null, options)
                stream?.close()
                stream = context.contentResolver.openInputStream(uri)!!
                //Toast.makeText(context,"erkar="+options.outWidth,Toast.LENGTH_LONG).show()
                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
                //Toast.makeText(context,"poqracum="+options.inSampleSize,Toast.LENGTH_LONG).show()
                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeStream(stream, null, options)!!
                stream?.close()
                if (bitmap!!.width > bitmap!!.height) {
                    val matrix = Matrix()

                    matrix.postRotate(90F)
                    bitmap = Bitmap.createBitmap(
                        bitmap!!,
                        0,
                        0,
                        bitmap!!.width,
                        bitmap!!.height,
                        matrix,
                        true
                    )
                }
            }
            return bitmap
            //return BitmapFactory.decodeResource(res, resId, options)
        }
        /*private fun getFilePath(uri: Uri,context: Context): String? {
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = context.getContentResolver().query(uri, projection, null, null, null)
            if (cursor != null) {
                cursor!!.moveToFirst()

                val columnIndex = cursor!!.getColumnIndex(projection[0])
                val picturePath = cursor!!.getString(columnIndex) // returns null
                cursor!!.close()
                return picturePath
            }
            return null
        }*/
        fun setupImage(
            path: String?,
            view: ImageView,
            reqHeight: Int,
            reqWidth: Int,
            context: Context
        ) {
            //val thread =
             Thread(
                Runnable {
                    try {
                        if (!path.equals("NULL") && !path.equals("") && path != null) {
                            val uri = Uri.parse(path)
                            //val path = myDataset.prList[position].image1

                            //val image_stream: InputStream? =
                            // context.contentResolver.openInputStream(uri)

                            //if (File(getFilePath(uri, context)).exists()) {
                            val bitmap = decodeSampledBitmapFromResource(
                                uri,
                                reqWidth,//225
                                reqHeight,//450
                                context
                            )
                            val mainHandler = Handler(context.mainLooper)

                            val myRun = Runnable {

                                if (bitmap != null)
                                    view.setImageBitmap(bitmap)
                                else
                                    view.setImageDrawable(null)

                            }
                            mainHandler.post(myRun)
                            //carImage.setImageBitmap(bitmap)
                            // view.setImageBitmap(bitmap)
                        }
                    }catch(e:Exception){
                        e.stackTrace
                    }
                    finally {
                        view.setImageDrawable(null)
                    }

                }).run()

        }
    }
}
class Products(val context: Context)
{
    val prList = ArrayList<Product>()
    /*fun load()
    {
        val bitmap1 = BitmapFactory.decodeResource(context.resources,
        R.drawable.play);
        val bitmap = BitmapFactory.decodeResource(context.resources,
            R.drawable.good_king);
        for(i in 0 until 7)
        {

            if(i%3 == 0) {
                prList.add(Product())
                val pr = Product()
                pr.name = "name"
                pr.image = bitmap
                prList.add(pr)
            }

            else {
                prList.add(Product())
                val pr = Product()
                pr.name = "name long long long     long            long"
                pr.image = bitmap
                prList.add(pr)
            }
        }
    }*/
}
