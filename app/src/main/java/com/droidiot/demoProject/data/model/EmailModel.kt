package com.droidiot.demoProject.data.model

import java.io.Serializable

data class EmailModel(
    val _id: String,
    val message: String,
    val subject: String,
    val time: String,
    val sender: SenderModel
) : Serializable