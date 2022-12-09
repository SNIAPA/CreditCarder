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
    var operationList : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.logs = ""
        operationList.add("CreditCard")
        binding.operationSpinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,operationList)

        binding.runClick = View.OnClickListener {



        }

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

            val cCReader = CCReader(tag,::log)

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

