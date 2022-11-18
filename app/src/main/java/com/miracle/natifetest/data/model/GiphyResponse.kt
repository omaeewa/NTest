package com.miracle.natifetest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GiphyResponse(val data: List<Data>) : Parcelable

@Parcelize
data class Data(val images: Images) : Parcelable

@Parcelize
data class Images(val fixed_height: Original) : Parcelable

@Parcelize
data class Original(val url: String) : Parcelable