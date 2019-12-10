package com.mohseen78.countryfacts.model

import com.squareup.moshi.Json

data class Fact(@Json(name = "title") val factTitle: String? = null,
                @Json(name = "imageHref") val factImgSrcUrl : String? = null,
                @Json(name = "description") val factDescription: String? = null)

data class FactsList(@Json(name = "rows") var facts: List<Fact>, val title : String)