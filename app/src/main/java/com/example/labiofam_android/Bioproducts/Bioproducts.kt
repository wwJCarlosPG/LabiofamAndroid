package com.example.labiofam_android.Bioproducts

data class Bioproducts (val name: String, val category: BioproductsCategories, val cost:String,
                        var isSelected: Boolean = false, val description: String, val photo: String)