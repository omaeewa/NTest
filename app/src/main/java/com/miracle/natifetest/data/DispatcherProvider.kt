package com.miracle.natifetest.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {

    val io: CoroutineDispatcher

    val main: CoroutineDispatcher

    val work: CoroutineDispatcher
}

class NatifeDispatcherProvider @Inject constructor() : DispatcherProvider {

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val work: CoroutineDispatcher
        get() = Dispatchers.Default

}