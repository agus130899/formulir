package com.example.formulir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyListData : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    val database = FirebaseDatabase.getInstance()
    private var dataVolunteer = ArrayList<DataVolunteer>()
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_data)
        recyclerView = findViewById(R.id.datalist)
        supportActionBar!!.title = "Data Volunteer"
        auth = FirebaseAuth.getInstance()
        MyRecyclerView()
        GetData()
    }

    private fun GetData() {
        Toast.makeText(applicationContext, "Waiting...",
            Toast.LENGTH_LONG).show()
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        getReference.child("Admin").child(getUserID).child("Volunteer")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (snapshot in dataSnapshot.children) {
//Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            val volunteer =
                                snapshot.getValue(DataVolunteer::class.java)
//Mengambil Primary Key, digunakan untuk proses Update/Delete
                            volunteer?.key = snapshot.key
                            dataVolunteer.add(volunteer!!)
                        }
//Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                        adapter = RecyclerViewAdapter(dataVolunteer, this@MyListData)
//Memasang Adapter pada RecyclerView
                        recyclerView?.adapter = adapter

                        (adapter as RecyclerViewAdapter).notifyDataSetChanged()
                        Toast.makeText(applicationContext,"Data Berhasil Dimuat",
                            Toast.LENGTH_LONG).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
// Kode ini akan dijalankan ketika ada error, simpan ke LogCat
                    Toast.makeText(applicationContext, "Data Gagal Dimuat",

                        Toast.LENGTH_LONG).show()
                    Log.e("MyListActivity", databaseError.details + " " +
                            databaseError.message)
                }
            })
    }
    //Methode yang berisi kumpulan baris kode untuk mengatur RecyclerView
    private fun MyRecyclerView() {
//Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
//Membuat Underline pada Setiap Item Didalam List
        val itemDecoration = DividerItemDecoration(
            applicationContext,
            DividerItemDecoration.VERTICAL
        )
        recyclerView?.addItemDecoration(itemDecoration)
    }
}