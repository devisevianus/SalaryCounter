package com.devis.salarycounter.model

/**
 * Created by devis on 2019-11-09
 */
 
data class UserMdl(
    val group: String = "",
    val employeeId: Int = 0,
    val fullName: String = "",
    val password: String = "",
    val gender: String = ""
)