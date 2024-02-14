package com.example.labiofam_android.view.bioproduct

data class BioproductsCategories(var isSelected: Boolean = false, var name:String) {
    override fun toString(): String {
        return name
    }
}