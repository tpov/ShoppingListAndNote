package com.tpov.shoppinglist.pojo

import androidx.room.Embedded

data class AnalyzedInstruction(
    val name: String?,
    val steps: List<Step>?
)