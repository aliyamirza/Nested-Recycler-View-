package com.smartheard.recyclerviewusingnested

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_desc.*
import kotlinx.android.synthetic.main.activity_desc.textAddress
import kotlinx.android.synthetic.main.activity_desc.textName
import kotlinx.android.synthetic.main.recycler_list.*

class DescActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc)

        val data = intent.getParcelableExtra<LocationData>("loc_data")

        textName.text = data?.locationName
        var address = ""

        data?.address?.let {
            address += it
        }
        data?.city?.let {
            address += it
        }
        data?.state?.let {
            address += it
        }
        data?.zip?.let {
            address += it
        }
        data?.country?.let {
            address += it
        }
       textAddress.text = address

        Glide.with(imageView).load(data?.url).into(imageView)
    }
}