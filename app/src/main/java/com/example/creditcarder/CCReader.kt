package com.example.creditcarder

import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.IsoDep

class CCReader : NfcReader{

    var isoDep: IsoDep

    private fun sendCommand(command:ByteArray): ByteArray?{

        log("SENT: " + command.toHexString())
        for (i in 0..10) {
            try {
                val ans = isoDep.transceive(command)
                log("ANS : " + ans.toHexString())
                log("ANS ASCI : " + ans.toAsciString())

                return ans
            } catch (e: TagLostException) {
                log("ERR :" + e.message)
            }
            log("RETRY "+(i+1)+": " + command.toHexString())
        }
        return  null
    }

    private fun selectApdu(aid: ByteArray){

        var command = arrayOf(
            0x00,
            0xA4,
            0x04,
            0x00,
            aid.size

        ).toByteArray()
        command += aid
        command += byteArrayOf(0x00)


        sendCommand(command)

    }
    private fun getData(){

        var command = arrayOf(
            0x00,
            0xB2,
            0x01,
            0x0C,

        ).toByteArray()
        command += byteArrayOf(0x00)


        sendCommand(command)

    }

    constructor(tag:Tag, log: (String)->Unit):super(tag,log) {

        isoDep = IsoDep.get(tag)
        isoDep.connect()
        isoDep.timeout = 100000
        log(isoDep.maxTransceiveLength.toString())

        //select master
        sendCommand(arrayOf(0x00,0xA4,0x04,0x00,0x00).toByteArray())

        selectApdu(arrayOf(
            0x32,0x50,0x41,0x59,0x2E,0x53,0x59,0x53,0x2E,0x44,0x44,0x46,0x30,0x31
        ).toByteArray())

        sendCommand(arrayOf(0x00,0x00,0xB2,0x01,0x14,0x00).toByteArray())



        //getData()

        isoDep.close()




    }

}

