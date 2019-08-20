package com.lambdaschool.androidlayoutanimations.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.androidlayoutanimations.R
import com.lambdaschool.androidlayoutanimations.model.Shopping
import com.lambdaschool.androidlayoutanimations.ui.ItemDetailActivity
import kotlinx.android.synthetic.main.shopping_item_list.view.*

class ShoppingListAdapter (val shoppingList: MutableList<Shopping>) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    private var lastPosition = -1

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val card = view.card_view as CardView
        val itemImageView = view.iv_product_image as ImageView
        val itemNameView = view.tv_product_name as TextView
        val shoppingItemParent = view.ll_shopping_list

        fun bindModel(item: Shopping) {
            itemImageView.setImageResource(item.imageId)
            itemNameView.text = item.product
            if (item.isAdded)
                shoppingItemParent.setBackgroundColor(
                    ContextCompat.getColor(itemView.context,
                        R.color.colorAdded
                    ))
            else
                shoppingItemParent.setBackgroundColor(
                    ContextCompat.getColor(itemView.context,
                        R.color.colorList
                    ))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.shopping_item_list,
                parent,
                false
            ) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = shoppingList[position]
        holder.bindModel(item)

        //holder.shoppingItemParent.setOnClickListener {
        //    item.isAdded = !item.isAdded

        //    notifyItemChanged(position)

        holder.card.setOnClickListener { view ->
            val intent = Intent(view.context, ItemDetailActivity::class.java)
            intent.putExtra(ItemDetailActivity.ITEM_KEY, item)

            val optionsBundle: Bundle =
                ActivityOptions.makeSceneTransitionAnimation(view.context as Activity, holder.itemImageView, "shared_image").toBundle()

            view.context.startActivity(intent, optionsBundle)
        }

        setEnterAnimation(holder.card, position)
    }

    private fun setEnterAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.my_slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}
