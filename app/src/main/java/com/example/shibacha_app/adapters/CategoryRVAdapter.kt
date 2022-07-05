package com.example.shibacha_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.shibacha_app.R
import com.example.shibacha_app.models.CategoryModel

class CategoryRVAdapter (var context: Context, var categoryArrayList: ArrayList<CategoryModel>): RecyclerView.Adapter<CategoryRVAdapter.CategoryHolder>() {

    var checkedArr: ArrayList<String> = ArrayList()
    private lateinit var quantityListener: CategoryQuantityListener

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var hobbyCardCategoryName : CheckBox = itemView.findViewById(R.id.cb_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_rv_item, parent, false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        if(categoryArrayList.isNotEmpty() && categoryArrayList.size > 0){
            val currCategory = categoryArrayList.get(position)

            holder.hobbyCardCategoryName.text = currCategory.categoryName
            holder.hobbyCardCategoryName.setOnClickListener {
                if(holder.hobbyCardCategoryName.isChecked){
                    checkedArr.add(currCategory.categoryName)
                }
                else{
                    checkedArr.remove(currCategory.categoryName)
                }
            }

//            quantityListener.
        }

    }

}