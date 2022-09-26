package com.example.nicacashcounter

class Currency {
    private var name:String? = null
    private var country:String? = null
    private var banknote:Map<String,Float> = emptyMap()
}
class Session{
    var currency:Currency?=null
}