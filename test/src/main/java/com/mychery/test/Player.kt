package com.mychery.test

/**
 * @author: chunjinchen
 * @createTime: 11/14/24 17:33
 * @description:
 **/
class Player {
    companion object {
        init {
            try {
                System.loadLibrary("mpv")
                System.loadLibrary("player")
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

}