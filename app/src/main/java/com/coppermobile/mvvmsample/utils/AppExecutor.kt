package com.coppermobile.mvvmsample.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutor private constructor(val diskIo: Executor) {

    constructor() : this(DiskIOThreadExecutor())

    class DiskIOThreadExecutor : Executor {

        private val mDiskIO: Executor

        init {
            mDiskIO = Executors.newSingleThreadExecutor()
        }

        override fun execute(command: Runnable) {
            mDiskIO.execute(command)
        }
    }
}