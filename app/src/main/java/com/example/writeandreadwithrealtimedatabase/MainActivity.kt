package com.example.writeandreadwithrealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

// realtime database 읽어오는 부분 해결해야함
// 각 로그인 계정 이름으로 단어와 단어의 뜻 추가 하는 것 까진 했음
class MainActivity : AppCompatActivity() {

    private val TAG : String = "jeongmin"

    private val database = Firebase.database
    private val myRef = database.getReference("dictionary")

    private val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    private val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열
    private val adapter = ListAdapter(itemList)         // 리사이클러 뷰 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infLoad()

        load()

        rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = adapter

    }

    // 엡이 시작되자마자 FireStore database 의 데이터를 불러오기 위한 함수
    private fun load() {
        db.collection("dictionary") // 작업할 컬렉션
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for(document in result) { // 가져온 문서들은 result 에 들어감
                    val item = ListLayout(document["word"] as String, document["mean"] as String)
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged() // 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.d(TAG, "Error getting documents : $exception")
            }
    }

    private fun infLoad() {
        Log.d(TAG, "MainActivity 안 nameLoad 안 받은 이름 : ${intent.getStringExtra("name")}, 메일 : ${intent.getStringExtra("email")}")

        mainTv.text = "${intent.getStringExtra("name")}님의 사전"
    }


/* onClick 함수 */
    fun onClickAdd(view: View) {
        var dicAddWord = mainEtWord.text.toString()
        var dicAddMean = mainEtMean.text.toString()

        var getEmail = intent.getStringExtra("email").toString() // 골뱅이 뒤에를 잘라서 child 로 만들어야 함

        var splitEmail = getEmail.split("@")
        var split0thEmailString = splitEmail[0].toString()

        myRef.child("$split0thEmailString").child("$dicAddWord").setValue(dicAddMean)//대신 child 값이 중복되면 덮어쓰여짐
        mainEtWord.text.clear()
        mainEtMean.text.clear()
    }
}