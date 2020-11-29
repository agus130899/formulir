package com.example.formulir

import android.content.Context
import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (private val listVolunteer: ArrayList<DataVolunteer>,
context: Context):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val context: Context

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val IdNumber: TextView
        val Name: TextView
        val Address: TextView
        val Phone: TextView
        val ListItem: LinearLayout

        init {//Menginisialisasi View yang terpasang pada layout RecyclerView kita
            IdNumber = itemView.findViewById(R.id.idx)
            Name = itemView.findViewById(R.id.namex)
            Address = itemView.findViewById(R.id.addressx)
            Phone = itemView.findViewById(R.id.phonex)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//Membuat View untuk Menyiapkan & Memasang Layout yang digunakan pada RecyclerView
        val V: View = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.view_design, parent, false
        )
        return ViewHolder(V)
    }

    @Suppress("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//Mengambil Nilai/Value pada RecyclerView berdasarkan Posisi Tertentu
        val IdNumber: String? = listVolunteer.get(position).idnumber
        val Name: String? = listVolunteer.get(position).name
        val Address: String? = listVolunteer.get(position).address
        val Phone: String? = listVolunteer.get(position).phone
//Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.IdNumber.text = "ID: $IdNumber"
        holder.Name.text = "Name: $Name"
        holder.Address.text = "Address: $Address"
        holder.Phone.text = "Phone: $Phone"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
//Kodingan untuk fungsi Edit dan Delete, yang dibahas pada Tutorial Berikutnya.
                return true
            }
        })
    }

    override fun getItemCount(): Int {
//Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listVolunteer.size
    }

    //Membuat Konstruktor, untuk menerima input dari Database
    init {
        this.context = context
    }
}