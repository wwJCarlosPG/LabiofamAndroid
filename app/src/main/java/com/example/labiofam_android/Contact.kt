package com.example.labiofam_android

import android.util.Xml

data class Contact(val name: String, val age:String, val position:String,
                   val category: ContactsCategories, var phoneNumber: Int) {

}