package com.example.firebase_4_2sem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View
import java.net.Inet4Address

class MainActivity : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var Password: EditText
    lateinit var submitbutton: Button
    lateinit var verifybutton:Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var databasereference:DatabaseReference
    lateinit var firebasedatabase:FirebaseDatabase
    lateinit var userId:String
    lateinit var Loginbutton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email = findViewById(R.id.Emailetext)
        Password = findViewById(R.id.Passwordetext)
        submitbutton = findViewById(R.id.submitbutton)
        verifybutton=findViewById(R.id.verifybutton)
        firebaseAuth = FirebaseAuth.getInstance()
        firebasedatabase=FirebaseDatabase.getInstance()
        Loginbutton=findViewById(R.id.Loginbutton)


        //////////////////-------------------------SIGN IN--------------------////////////////////////////
        submitbutton.setOnClickListener {
            val em=email.text.toString()
            val pw=Password.text.toString()

            if(email.text.isEmpty() || Password.text.isEmpty()){
                Toast.makeText(this,"enter all necessary fields",Toast.LENGTH_SHORT).show()
            }
            else if(pw.length<7 || !(pw.contains("^[a-zA-Z0-9]*$".toRegex()))){
                Toast.makeText(this,"Enter an alphanumeric password",Toast.LENGTH_SHORT).show()
            }
            else{
            firebaseAuth.createUserWithEmailAndPassword(email.text.toString(),
                Password.text.toString()).addOnCompleteListener(this) { task ->
                Toast.makeText(this, "completed" + task.isSuccessful, Toast.LENGTH_SHORT).show()

                if(task.isSuccessful){
                    databasereference=firebasedatabase.getReference("users")
                    val user=FirebaseAuth.getInstance().currentUser
                    userId=user!!.uid
                    val myuser=User(em,pw)
                    firebasedatabase.getReference().child("User").child(userId).setValue(myuser)
                }else
                {
                    Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                }

            }
            }
        }
        /////////////////---------------------------verify button--------------------////////////////////
        verifybutton.setOnClickListener{

            firebaseAuth.createUserWithEmailAndPassword(email.text.toString(),Password.text.toString()).addOnCompleteListener(this)
            { task->
                if(task.isSuccessful){
                    firebaseAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this){
                        if(task.isSuccessful){
                            Toast.makeText(this,"Link sent "+task.isSuccessful,Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext,task.exception!!.message,Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else{
                    Toast.makeText(applicationContext,task.exception!!.message,Toast.LENGTH_LONG).show()
                }
            }
        }
        ////////////////---------------------------------LOGIN ACTIVITY--------------///////////////////////////////////////////////

        Loginbutton.setOnClickListener {

            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}