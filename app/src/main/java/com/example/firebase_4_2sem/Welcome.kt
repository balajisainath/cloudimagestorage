package com.example.firebase_4_2sem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.view.View

class Welcome : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var uid:String
   lateinit var details: TextView
    lateinit var databaseReference: DatabaseReference
    lateinit var show:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)
        show=findViewById(R.id.show)
        details=findViewById(R.id.Detaill)

        firebaseDatabase=FirebaseDatabase.getInstance()
        firebaseAuth=FirebaseAuth.getInstance()
        databaseReference=firebaseDatabase.getReference("users")
        val user=FirebaseAuth.getInstance().currentUser

        if(user!=null){
            uid=user!!.uid
        }


       firebaseDatabase=FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("data")


    }



    fun showuserdetails(view: android.view.View) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
        firebaseDatabase.getReference().child("User").child(uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            val user=snapshot.getValue(User::class.java)
                if (user!=null){
                    details.text="email: ${user.email} \npassword: ${user.pass}"
                    Log.d("qwerty","${user.email}")

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}