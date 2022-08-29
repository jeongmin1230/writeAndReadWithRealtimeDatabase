package com.example.writeandreadwithrealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG : String = "jeongmin"

    private val database = Firebase.database
    private val myRef = database.getReference("dictionary")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infLoad()
    }

    private fun infLoad() {
        Log.d(TAG, "MainActivity 안 nameLoad 안 받은 이름 : ${intent.getStringExtra("name")}, 메일 : ${intent.getStringExtra("email")}")

        mainTv.text = "${intent.getStringExtra("name")}님의 사전"
    }
/* onClick 함수 */
    fun onClickAdd(view: View) {
        var dicAddWord = mainEtWord.text.toString()
        var dicAddMean = mainEtMean.text.toString()

        var getEmail = intent.getStringExtra("email")

//        myRef.child("$dicAddWord").push().setValue(dicAddMean)
        myRef.setValue("$getEmail")
        myRef.child("$dicAddWord").setValue(dicAddMean)
    }
}