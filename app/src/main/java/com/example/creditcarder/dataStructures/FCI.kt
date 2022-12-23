package com.example.creditcarder.dataStructures

import android.util.Log
import com.example.creditcarder.*

class FCI {

    var tags: MutableList<DO> = mutableListOf()

    private fun add(reader:ByteArrayDOReader, tag: Int): ByteArray{return  add(reader, arrayOf(tag))}
    private fun add(reader:ByteArrayDOReader, tag: Array<Int>): ByteArray{
        val dO = reader.readDO(tag.toByteArray())
        tags.add(dO)
        return dO.value
    }


    constructor(bytes: ByteArray){

        Log.d("FCI", bytes.toHexString())

        var reader = ByteArrayDOReader(bytes)

        reader = ByteArrayDOReader(add(reader,0x6f))
        add(reader,0x84)
        reader = ByteArrayDOReader(add(reader,0xa5))
        reader = ByteArrayDOReader(add(reader, arrayOf(0xbf,0x0c)))
        reader = ByteArrayDOReader(add(reader, 0x61))
        add(reader,0x4f)
        add(reader,0x50)
        add(reader,0x87)
        add(reader,arrayOf(0x9f,0x0a))


    }

    override fun toString(): String {


        return tags.joinToString("\n\n") { "${it.tag.toHexString()} => ${it.value.toHexString()}" }

    }
}