package com.menadev.thesparkbank.Transaction

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.menadev.thesparkbank.ModelClasses.TransactionData
import com.menadev.thesparkbank.R
import com.menadev.thesparkbank.SqliteDBS.TransactionHistoryDB
import com.menadev.thesparkbank.SqliteDBS.UserDB
import com.menadev.thesparkbank.ModelClasses.userData


class TransactionHistoryActivity : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        val toolbar: Toolbar = findViewById(R.id.app_bar_layout) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Transaction History"

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.transaction_list) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val items: ArrayList<TransactionData> = ArrayList()

        val userDB: UserDB= UserDB(applicationContext)
        val transactionDB: TransactionHistoryDB= TransactionHistoryDB(applicationContext)

        val cursor: Cursor = transactionDB.viewAllTransactions()

        if (cursor!=null && cursor.moveToFirst()) {

            val from = cursor.getInt(cursor.getColumnIndex("from_user"))
            val to = cursor.getInt(cursor.getColumnIndex("to_user"))
            val amount = cursor.getInt(cursor.getColumnIndex("amount"))
            val date_time = cursor.getString(cursor.getColumnIndex("date_time"))

            val fromUser: userData = userDB.viewSingleUser(from)
            val toUser: userData = userDB.viewSingleUser(to)

            val item= TransactionData(
                fromUser.name,
                toUser.name,
                date_time,
                amount
            )

            items.add(item)

            while (cursor.moveToNext())
            {
                val from = cursor.getInt(cursor.getColumnIndex("from_user"))
                val to = cursor.getInt(cursor.getColumnIndex("to_user"))
                val amount = cursor.getInt(cursor.getColumnIndex("amount"))
                val date_time = cursor.getString(cursor.getColumnIndex("date_time"))

                val fromUser: userData = userDB.viewSingleUser(from)
                val toUser: userData = userDB.viewSingleUser(to)

                val item= TransactionData(
                    fromUser.name,
                    toUser.name,
                    date_time,
                    amount
                )

                items.add(item)
            }
        }

        items.reverse()

        //creating our adapter
        val adapter: TransactionRecyclerViewAdapter= TransactionRecyclerViewAdapter(items)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}