package com.example.labiofam_android.view.bioproduct

sealed class BioproductsCategories(var isSelected: Boolean = false) {
    object Bioestimulantes: BioproductsCategories(){
        override fun toString(): String {
            return "Bioestimulantes"
        }
    }
    object Biofertilizantes: BioproductsCategories()
    {
        override fun toString(): String {
            return "Biofertilizantes"
        }
    }

    object Bioplagicidas: BioproductsCategories(){
        override fun toString(): String {
            return "Bioplagicidas"
        }
    }
    object Biolarvicidas: BioproductsCategories(){
        override fun toString(): String {
            return "Biolarvicidas"
        }
    }
    object Biofungicidas: BioproductsCategories(){
        override fun toString(): String {
            return "Biofungicidas"
        }
    }

}