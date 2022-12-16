package com.example.creditcarder.dataStructures

import android.util.Log
import com.example.creditcarder.*

class FCI {

    val DFName: ByteArray
    val label: String
    val priority:Byte
    val language:Array<String>
    val PDOL: List<Pair<ByteArray,Int>>

    constructor(bytes: ByteArray){

        with(ByteArrayDOReader(bytes)){

            assert(nextOne() == 0x6f.toByte())
            Log.d("fci",nextSize(true).toHexString())
            var size = nextOne()
            assert(nextOne() == 0x84.toByte())
            DFName = nextSize()
            assert(nextOne() == 0xa5.toByte())
            with(ByteArrayDOReader(nextSize())){
                assert(nextOne() == 0x50.toByte())
                label = nextSize().toAsciString()
                assert(nextOne() == 0x87.toByte())
                priority = nextSize()[0]
                assert(next(2).contentEquals(arrayOf(0x5f,0x2d).toByteArray()))
                language = nextSize().toAsciString().chunked(2).toTypedArray()
                assert(next(2).contentEquals(arrayOf(0x9f,0x38).toByteArray()))
                PDOL = nextSize().toList().chunked(3).map {it-> Pair(it.slice(IntRange(0,1)).toByteArray(), it.last().toInt() ) }
                assert(next(2).contentEquals(arrayOf(0xBF,0x0C).toByteArray()))
                with(nextSize()){
                    //proprietary shit
                }

            }


        }
    }

    override fun toString(): String {

        var ans = "{\n"

        ans += "\tAID: %s\n".format(DFName.toHexString())
        ans += "\tLABEL: %s\n".format(label)
        ans += "\tPRIORITY: %s\n".format(priority.toBinString())
        ans += "\tLANG: %s\n".format(language.joinToString(","))
        ans += "\tPDOL: %s\n".format(PDOL.toString())
        return "$ans}"

    }
}