package com.example.labiofam_android.view.bioproduct

sealed class BioproductsCategories(var isSelected: Boolean = false) {
    object Agricola: BioproductsCategories()
    object Epidemias: BioproductsCategories()
    object Other: BioproductsCategories()
}