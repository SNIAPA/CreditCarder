package com.example.creditcarder

class ByteArrayDOReader(var byteArray: ByteArray) {


    fun next(count:Int, peek: Boolean = false): ByteArray{
        val ans = byteArray.sliceArray(IntRange(0,count-1))
        if ( !peek) {
            byteArray = byteArray.sliceArray(IntRange(count, byteArray.size - 1))
        }
        return ans
    }

    fun nextOne(): Byte{
        return next(1)[0]
    }

    fun nextSize(peek: Boolean = false): ByteArray{
        return  next(nextOne().toInt(),peek)
    }

}