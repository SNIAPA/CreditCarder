package com.example.creditcarder

import android.nfc.Tag

abstract class NfcReader(val tag: Tag,val log: (String)->Unit) {



}