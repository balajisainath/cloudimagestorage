package com.example.firebase_4_2sem

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

class image : AppCompatActivity() {
    val pic_id=123
    lateinit var clickbutton:Button
    lateinit var uploadbutton:Button
    lateinit var imgview:ImageView
    lateinit var fromcamera:Button
     var firebaseStorage: FirebaseStorage?=null
    var firebaseDatabase:FirebaseDatabase?=null
    var storageReference:StorageReference?=null
    lateinit var filepath:Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        clickbutton = findViewById(R.id.clickimage)
        uploadbutton = findViewById(R.id.uploadimage)
        imgview = findViewById(R.id.imgview)
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val ip=registerForActivityResult(ActivityResultContracts.GetContent()){
            filepath=it!!
            imgview.setImageURI(it)

        }
        clickbutton.setOnClickListener(){
            ip.launch("image/*")

        }
        uploadbutton.setOnClickListener(){
            uploadimage()
        }
        fromcamera.setOnClickListener(){
            
        }


    }

    private fun uploadimage() {
        firebaseStorage?.getReference("images")?.putFile(filepath)
    }

}