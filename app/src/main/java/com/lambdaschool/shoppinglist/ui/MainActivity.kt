package com.lambdaschool.shoppinglist.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.lambdaschool.shoppinglist.R
import com.lambdaschool.shoppinglist.model.ShoppingRepository
import com.lambdaschool.shoppinglist.adapter.ShoppingListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ShoppingRepository.createShoppingList()

        rv_item_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ShoppingListAdapter(ShoppingRepository.shoppingList)
        }

        btn_send_list.setOnClickListener { p0 ->

            // Creates the string of the items selected once the button is pushed.
            fun getList(): String {
                var listString = ""
                for (item in ShoppingRepository.shoppingList) {
                    if (item.isAdded) listString += "${item.product}" + ", "
                }
                // Trims the end to remove the ", " at the end of the list.
                listString = listString.trimEnd(',', ' ')

                return listString
            }

            // When the button is pushed, it will create an action to send the list to another application.
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Shopping List: ${getList()}")
                type = "text/plain"
            }

            startActivity(sendIntent)

            // NotificationGenerator
            val channelId = "${this.packageName}.notificationchannel"
            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Shopping List Notification Channel"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val description = "Channel to send shopping list notification"

                val channel = NotificationChannel(channelId, name, importance)
                channel.description = description

                notificationManager.createNotificationChannel(channel)
            }

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setSmallIcon(android.R.drawable.ic_menu_agenda)
                .setContentTitle("Confirmation")
                .setContentText("Shopping list has been created.")
                .setAutoCancel(true)
            notificationManager.notify(1, notificationBuilder.build())
        }
    }
}
