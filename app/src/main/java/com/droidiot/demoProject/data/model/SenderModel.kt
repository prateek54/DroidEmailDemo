package com.droidiot.demoProject.data.model

import java.io.Serializable

data class SenderModel(
    val first_name: String?,
    val last_name: String?,
    val picture: String?
) : Serializable