package com.menadev.thesparkbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.menadev.thesparkbank.SqliteDBS.UserDB


class AddUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val toolbar: Toolbar =findViewById<Toolbar>(R.id.app_bar_layout)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Add User"

        val userName =findViewById<EditText>(R.id.username)
        val userEmail  =findViewById<EditText>(R.id.useremail)
        val amount  =findViewById<EditText>(R.id.amount)
        val add  =findViewById<Button>(R.id.add_button)

        val db: UserDB= UserDB(applicationContext)

        add.setOnClickListener {
            val check: Long = db.insertUsers(userName.text.toString(),userEmail.text.toString(),Integer.valueOf(amount.text.toString()))

            Toast.makeText(applicationContext,"Add Successful", Toast.LENGTH_LONG).show()

        }
    }
}