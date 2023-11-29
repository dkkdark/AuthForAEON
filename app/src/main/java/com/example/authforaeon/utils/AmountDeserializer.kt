package com.example.authforaeon.utils

import com.google.gson.*
import java.lang.reflect.Type

class AmountDeserializer : JsonDeserializer<String> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String? {
        return json?.asString
    }
}
