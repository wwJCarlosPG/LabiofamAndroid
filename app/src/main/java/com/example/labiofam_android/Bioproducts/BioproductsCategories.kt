package com.example.labiofam_android.Bioproducts

sealed class BioproductsCategories(var isSelected: Boolean = false) {
    object Agricola: BioproductsCategories()
    object Epidemias: BioproductsCategories()
    object Other: BioproductsCategories()
}