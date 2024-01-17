package com.example.contactapp.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactapp.R
import com.example.contactapp.data.ContactData
import com.example.contactapp.data.Contants
import com.example.contactapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val data : ContactData? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Contants.ITEM_DATA, ContactData::class.java)
        }else {
            intent?.getParcelableExtra<ContactData>(Contants.ITEM_DATA)
        }
    }
    private val position by lazy {
        intent.getIntExtra(Contants.ITEM_INDEX,0)
    }

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        



    }
}