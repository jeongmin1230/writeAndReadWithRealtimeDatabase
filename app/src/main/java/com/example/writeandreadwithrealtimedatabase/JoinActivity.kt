package com.example.writeandreadwithrealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    private val TAG : String = "jeongmin"

    private lateinit var auth : FirebaseAuth
    private val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
    }

    /* 일반 함수*/
    // 회원가입 함수
    private fun createUser(email:String, password:String) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "가입되었습니다.\n다시 로그인 해 주세요.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else { // 형식에 맞지 않을 경우
                    Log.d(TAG, "형식에 맞지 않습니다.")
                }
            }
            .addOnFailureListener { // 파이어베이스의 데이터와 중복됐을 경우
                Toast.makeText(this, "[회원가입 실패]", Toast.LENGTH_SHORT).show()
            }
        return
    }

    // 파이어스토어 다큐먼트 생성 함수
    private fun createDocument() {

        val data = hashMapOf(
            "email" to joinEmail.text.toString(),
            "name" to joinName.text.toString(),
            "phone" to joinPhone.text.toString()
        )
        db.collection("member").document(joinEmail.text.toString()).set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "document 를 추가했습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d(TAG, "document 추가 실패 : $it")
            }
    }

    /* onClick 함수 */
    fun onClickSubmit(view: View) {
        Log.d(TAG, "가입 완료, 입력 이름은 ${joinName.text.toString()}")
        val name = joinName.text.toString().trim()
        val email = joinEmail.text.toString().trim()
        val password = joinPW.text.toString().trim()
        val phone = joinPhone.text.toString().trim()

        if(name == "" || email == "" || password == "" || phone == "") {
            Toast.makeText(this, "이름, 이메일, 비밀번호, 핸드폰번호는 반드시 입력해야합니다.", Toast.LENGTH_SHORT).show()
        } else {
            createUser(email, password)
            createDocument()
        }
    }
}