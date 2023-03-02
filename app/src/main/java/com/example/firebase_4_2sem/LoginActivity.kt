package com.example.firebase_4_2sem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.net.PasswordAuthentication

class LoginActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var pass:EditText
    lateinit var login:Button
    lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email=findViewById(R.id.Email)
        pass=findViewById(R.id.Password)
        firebaseAuth=FirebaseAuth.getInstance()
        login=findViewById(R.id.Loginbutton)

        login.setOnClickListener{
            firebaseAuth.signInWithEmailAndPassword(email.text.toString(),pass.text.toString()).addOnCompleteListener (){
                    task->
                if(task.isSuccessful){
                    startActivity(Intent(this,Welcome::class.java))
                }else{
                    Toast.makeText(this,"wrong credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}