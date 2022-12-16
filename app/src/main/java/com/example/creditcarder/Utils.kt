package com.example.creditcarder

fun ByteArray.toHexString(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
fun ByteArray.toAsciString(): String = String(this)

fun Byte.toHesString():String = "%02x".format(this)
fun Byte.toBinString():String = Integer.toBinaryString((this+256)%256)
fun Array<Int>.toByteArray():ByteArray {
    var byteArray = byteArrayOf()

    this.forEach { byteArray += it.toByte() }
    return byteArray

}