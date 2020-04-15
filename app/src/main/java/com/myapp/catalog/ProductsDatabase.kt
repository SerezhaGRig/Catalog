package com.myapp.catalog

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.myapp.catalog.RecordDbSchema.RecordTable
import android.widget.Toast




private const val VERSION = 1
private const val DATABASE_NAME = "produc.db"
class ProductsDatabase(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION)
{

    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL(
            "create table " + RecordDbSchema.RecordTable.NAME + "(" +
                    RecordTable.Cols.Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    RecordTable.Cols.Name + " TEXT, " +
                    RecordTable.Cols.Cost + " TEXT, " +
                    RecordTable.Cols.Description + " TEXT, " +
                    RecordTable.Cols.Image1 + " TEXT, " +
                    RecordTable.Cols.Image2 + " TEXT, " +

                    RecordTable.Cols.Image3 + " TEXT, "+
                    RecordTable.Cols.Type + " TEXT ); ")
        //add type

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun deleteProduct(productId: String) {
        val sqldb = writableDatabase
        if (sqldb.delete(RecordTable.NAME, RecordTable.Cols.Id + "=?", arrayOf(productId)) > 0) {
            Toast.makeText(context, "Record removed", Toast.LENGTH_SHORT).show()
        }
    }
        fun insertProduct(product: Product )
        {
            val values = ContentValues()
            val db = this.writableDatabase
            db.beginTransaction()
            values.put(RecordTable.Cols.Name, product.name)
            values.put(RecordTable.Cols.Cost, product.cost)
            values.put(RecordTable.Cols.Description, product.description)
            values.put(RecordTable.Cols.Image1, product.image1)
            values.put(RecordTable.Cols.Image2, product.image2)
            values.put(RecordTable.Cols.Image3, product.image3)
            values.put(RecordTable.Cols.Type, product.category)


            db.insert(RecordTable.NAME, null, values)
            //Log.e("Contact Entered", "DATABASE")
            db.setTransactionSuccessful()
            db.endTransaction()
            db.close()



        }
     fun selectProduct(filter:String?):Products
    {
        //var fil:String
        val products = Products(context)
        val db = this.readableDatabase
        //if (filter == null)
         //   fil = "All"
        //else fil = filter
        val c:Cursor
        if (filter == "All")
            c = db.rawQuery("SELECT * FROM "+RecordTable.NAME +";", null)
        else
            c = db.rawQuery("SELECT * FROM "+RecordTable.NAME+ " WHERE "+RecordTable.Cols.Type+" = '"+filter+"';", null)

        if (c.moveToFirst()) {
            do {
                // Passing values
                val pr =Product()
                pr.id = c.getInt(0).toString()
                pr.name = c.getString(1)
                pr.cost = c.getString(2)
                pr.description = c.getString(3)
                pr.image1 = c.getString(4)
                pr.image2 = c.getString(5)
                pr.image3 = c.getString(6)
                pr.category = c.getString(7)
                // Do something Here with values
                products.prList.add(pr)
            } while (c.moveToNext())
        }
        c.close()
        db.close()
        return products
    }





}
object RecordDbSchema {
    object RecordTable {
        const val NAME = "products"

        object Cols {
            const val Id = "id"
            const val Name = "name"
            const val Cost = "cost"
            const val Description = "description"
            const val Image1 = "image1"
            const val Image2 = "image2"
            const val Image3 = "image3"
            const val Type = "type"

        }
    }
}
