package com.example.labiofam_android.Bioproducts

sealed class BioproductsCategories {
    object Agricola: BioproductsCategories()
    object Epidemias: BioproductsCategories()
    object Other: BioproductsCategories()
}