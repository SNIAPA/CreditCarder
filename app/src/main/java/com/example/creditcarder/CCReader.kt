package com.example.creditcarder

import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.IsoDep
import android.nfc.tech.NfcA
import android.util.Log

class CCReader {

    var tag:Tag
    var log: (String)->Unit
    var isoDep: IsoDep

    private fun sendCommand(byteArray: ByteArray): ByteArray?{

        log("SENT: " + byteArray.toHexString())
        for (i in 0..10) {
            try {
                val ans = isoDep.transceive(byteArray)
                log("ANS : " + ans.toHexString())

                return ans
            } catch (e: TagLostException) {
                log("ERR :" + e.message)
            }
            log("RETRY "+(i+1)+": " + byteArray.toHexString())
        }
        return  null
    }

    private fun apduCommands(){


      sendCommand(arrayOf(
          0x00,
          0xA4,
          0x04,
          0x00,
          0x07,
          0xA0,
          0x00,
          0x00,
          0x00,
          0x03,
          0x10,
          0x10,
          0x00
      ).toByteArray())

    }

    constructor(tag:Tag, log: (String)->Unit) {

        this.tag = tag
        this.log = log
        isoDep = IsoDep.get(tag)
        isoDep.connect()
        isoDep.timeout = 100000
        log(isoDep.maxTransceiveLength.toString())
        apduCommands()

        isoDep.close()




    }

}

