package com.example.creditcarder

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListAdapter
import android.widget.SpinnerAdapter
import androidx.databinding.DataBindingUtil
import com.example.creditcarder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    lateinit var adapter: NfcAdapter
    lateinit var binding: ActivityMainBinding
    var operationList : MutableList<Pair<String,String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.logs = ""
        operationList.add(Pair("CreditCard","asd"))
        binding.operationSpinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,operationList)

        adapter = NfcAdapter.getDefaultAdapter(this)

    }

    private fun log(string: String){
        binding.logs += string + "\n"
        Log.d("LOG",string)
    }


    override fun onNewIntent(intent: Intent) {


        super.onNewIntent(intent)

            var tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)!!
        var techlist = tag.techList.map { it.split(".").last() }

        log("TAG DISCOVERED: "+ tag.id.toHexString())

        log("TAG DISCOVERED: "+ techlist.joinToString("\t"))

        if (techlist.contains("IsoDep")){

            val commands = mutableListOf(
                arrayOf(

                    // 0x00, 0xA4,0x04,0x00,0x00
                    // OpenPgp not working tho
                    // 0x00, 0xA4, 0x04, 0x00,0x06,0xD2,0x76,0x00,0x01,0x24,0x01,0x00
                    // 2pay                    0x00,0xA4,0x04,0x00,0x0e,0x32,0x50,0x41,0x59,0x2e,0x53,0x59,0x53,0x2e,0x44,0x44,0x46,0x30,0x31,0x00
                    //0x00,0xa4,0x04,0x00,0x08,0xA0,0x00,0x00,0x00,0x03,0x00,0x00,0x00 ,0x00
                    //visa classic
                    0x00, 0xA4,0x04,0x00,0x07,0xA0,0x00,0x00,0x00,0x03,0x10,0x10,0x00
                ).toByteArray(),
            )

            val readAllRecords = arrayOf(
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x5F, 0x35 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x5F, 0x53 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0xDE ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x7f, 0x21 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0x6e ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0x65 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0x7a ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x7f, 0x77 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0x5e ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x5f, 0x50 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0x4f ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x7f, 0x74 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0xF9 ,0x00
                ).toByteArray(),
                arrayOf(
                    //read applidation related data
                    0x80,0xCA,0x00, 0xfa ,0x00
                ).toByteArray(),
            )

            commands.addAll(readAllRecords)

            val commandSequence = CommandSequence(commands.toTypedArray(),::log)
            commandSequence.execute(IsoDep.get(tag))

        }


    }


    public override fun onPause() {
        super.onPause()
        adapter.disableForegroundDispatch(this)
    }

    public override fun onResume() {
        super.onResume()
        val pendingIntent = PendingIntent.getActivity(this,
            0,
            Intent(this,javaClass).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            },
            PendingIntent.FLAG_MUTABLE)
        val intentFiltersArray = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))

        val techList = arrayOf(arrayOf<String>(
            IsoDep::class.java.name,
            NfcA::class.java.name,
            NfcB::class.java.name,
            NfcF::class.java.name,
            NfcF::class.java.name,
            NfcV::class.java.name,
            NfcBarcode::class.java.name,
            MifareClassic::class.java.name,
            MifareUltralight::class.java.name,
            NdefFormatable::class.java.name,
            NfcBarcode::class.java.name))

        adapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray,techList)
    }

}

