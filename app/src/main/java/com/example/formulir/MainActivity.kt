package com.example.formulir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.formulir.login
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var auth:FirebaseAuth? = null
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        viewdata.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }

    private fun isEmpty (s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View) {
        when (v.getId()){
            R.id.save -> {
                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val getName: String = name.getText().toString()
                val getAddress: String = address.getText().toString()
                val getPhone: String = phone.getText().toString()
                val getIdnumber: String = idnumber.getText().toString()

                val getReferences: DatabaseReference
                getReferences = database.reference

                if (isEmpty(getName) || isEmpty(getAddress) || isEmpty(getPhone) || isEmpty(getIdnumber)){
                    Toast.makeText(this@MainActivity, " Data cannot ve empty!",
                    Toast.LENGTH_SHORT).show()
                }else{
                    getReferences.child("Admin").child(getUserID).child("DataVolunteer").push()
                        .setValue(DataVolunteer(getName, getIdnumber, getAddress, getPhone))
                        .addOnCompleteListener(this){
                            name.setText("")
                            idnumber.setText("")
                            address.setText("")
                            phone.setText("")
                            Toast.makeText(this@MainActivity, " Data Saved Successfully",
                            Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.logout -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Logout Succesfull",
                            Toast.LENGTH_LONG).show()
                            intent = Intent(applicationContext, login::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
            R.id.viewdata -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))

            }
        }
    }
}

