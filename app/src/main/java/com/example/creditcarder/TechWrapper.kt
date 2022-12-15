package com.example.creditcarder

import android.nfc.tech.IsoDep
import android.util.Range

class TechWrapper(val tech: IsoDep,val log:(message:String)->Unit) {

    fun select(aid:ByteArray) { transceive(arrayOf(0x00,0xa4,0x04,0x00,aid.size).toByteArray()+aid+0x00.toByte())}


    fun transceive(command:ByteArray): ByteArray {

        tech.connect()
        try {
            val ans = tech.transceive(command)
            log("[%s] <-> %s".format(ans.slice(IntRange(ans.size-2,ans.size-1)).toByteArray().toHexString() , command.toHexString()))
            return ans.slice(IntRange(0,ans.size-3)).toByteArray()
        }catch (e:Error){
            log("ERROR: %s".format(e.message))
        }finally {
            tech.close()
        }

        tech.close()
    }

}