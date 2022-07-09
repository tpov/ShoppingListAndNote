package com.tpov.shoppinglist.pojo

data class AnalyzedInstruction(
    private val name: String,
    private val steps: List<Step>
) {
    fun getStep(): List<Step> {
        return steps
    }
}