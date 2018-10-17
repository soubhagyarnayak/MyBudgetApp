package com.snsoft.mybudget

import android.os.Handler
import android.os.HandlerThread

class DbWorkerThread(threadName: String) : HandlerThread(threadName) {
    private var  workerHandler: Handler? = null

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        workerHandler = Handler(looper)
    }

    fun postTask(task: Runnable){
        if(workerHandler==null) workerHandler = Handler(looper)
        workerHandler?.post(task)
    }
}