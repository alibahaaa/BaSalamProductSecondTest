package com.basalam.basalamproduct.thread

import java.util.concurrent.Executor
import javax.inject.Inject

class ThreadExecutor @Inject constructor() : Executor {
    override fun execute(command: Runnable?) {
        Thread(command).start()
    }
}