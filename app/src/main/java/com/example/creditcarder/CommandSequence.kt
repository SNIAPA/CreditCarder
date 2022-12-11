package com.example.creditcarder

import android.nfc.Tag
import android.nfc.cardemulation.NfcFCardEmulation
import android.nfc.tech.IsoDep
import android.nfc.tech.TagTechnology

class CommandSequence(private val commands:Array<ByteArray>,private val log :(String)->Unit) {

   fun execute(tech: IsoDep){

       try {

           tech.connect()


           for (i in commands.indices) {

               val command = commands[i]


               log("[%d] -> %s".format(i,command.toHexString()))
               val ans = tech.transceive(command)
               log("[%d] <- %s".format(i,ans.toHexString()))
               log("[%d] <- %s\n".format(i,ans.toAsciString()))

           }

           tech.close()

       }catch (e:Error){
           log("ERROR: %s".format(e.message))
       }
   }


}