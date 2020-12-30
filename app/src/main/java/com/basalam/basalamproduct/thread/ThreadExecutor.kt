package com.basalam.basalamproduct.thread

import java.util.concurrent.Executor

class ThreadExecutor : Executor {
    override fun execute(command: Runnable?) {
        Thread(command).start()
    }
}