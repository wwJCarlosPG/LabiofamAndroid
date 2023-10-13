package com.example.labiofam_android

sealed class ContactsCategories {
    object Comercial :ContactsCategories()
    object CorreoInstitucional :ContactsCategories()
    object Other :ContactsCategories()
}