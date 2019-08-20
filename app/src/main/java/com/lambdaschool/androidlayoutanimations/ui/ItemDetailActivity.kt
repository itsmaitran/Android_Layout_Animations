package com.lambdaschool.androidlayoutanimations.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.lambdaschool.androidlayoutanimations.R
import com.lambdaschool.androidlayoutanimations.model.Shopping
import kotlinx.android.synthetic.main.activity_item_detail.*

class ItemDetailActivity : AppCompatActivity() {

    companion object {
        const val ITEM_KEY = "SHOPPING_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_item_detail)

        val item = intent.getSerializableExtra(ITEM_KEY) as Shopping

        item_name.text = item.product
        item_image.setImageDrawable(ContextCompat.getDrawable(this, item.imageId))

    }
}
