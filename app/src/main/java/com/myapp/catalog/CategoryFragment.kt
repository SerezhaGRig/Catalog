package com.myapp.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView



class CategoryFragment(val category:String) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewManager = GridLayoutManager(activity,2) as RecyclerView.LayoutManager
        val db = activity?.let { ProductsDatabase(it) }

        val myDataset = db?.selectProduct(category)
        myDataset?.let {
            if (it.prList.size == 0)
                Toast.makeText(context, "Empty page add product", Toast.LENGTH_SHORT).show()
        }
        val viewAdapter = myDataset?.let { activity?.let { it1 -> MyAdapter(it, it1,category) } }


        val recyclerView = view.findViewById<RecyclerView>(R.id.rec_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
        val space = resources.getDimensionPixelSize(R.dimen.spc)
        /*val horizontalDivider = ContextCompat.getDrawable(this, R.drawable.line_divider)
        val verticalDivider = ContextCompat.getDrawable(this, R.drawable.line_divider)
        recyclerView.addItemDecoration(
            GridDividerItemDecoration(
                horizontalDivider,
                verticalDivider,
                2
            )
        )*/
        val vDivider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(vDivider)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(space))




    }





}
