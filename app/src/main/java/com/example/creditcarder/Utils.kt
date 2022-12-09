package com.example.creditcarder

fun ByteArray.toHexString(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun Byte.toHesString():String = "%02x".format(this)
fun Array<Int>.toByteArray():ByteArray {
    var byteArray = byteArrayOf()

    this.forEach { byteArray += it.toByte() }
    return byteArray

}