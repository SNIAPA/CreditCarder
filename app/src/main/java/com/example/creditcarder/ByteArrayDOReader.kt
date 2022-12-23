package com.example.creditcarder

import android.util.Log
import com.example.creditcarder.dataStructures.DO

class ByteArrayDOReader(var byteArray: ByteArray) {


    fun next(count:Int, peek: Boolean = false): ByteArray{
        val ans = byteArray.sliceArray(IntRange(0,count-1))
        if ( !peek) {
            byteArray = byteArray.sliceArray(IntRange(count, byteArray.size - 1))
        }
        return ans
    }

    fun nextOne(peek: Boolean = false): Byte{
        return next(1, peek)[0]
    }

    fun nextSize(peek: Boolean = false): ByteArray{
        return  next(nextOne().toInt(),peek)
    }

    fun readDO(tag:ByteArray): DO{

        if(!next(tag.size).contentEquals(tag)){
            throw java.lang.Error("invalid tag")
        }

        return DO(tag,nextOne(true).toInt(),nextSize())
    }

    fun readDO(tag:Byte): DO{
        return readDO(byteArrayOf(tag))
    }
}