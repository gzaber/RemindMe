package com.gzaber.remindme.data.service

interface AlarmService {
    fun schedule(requestCode: Int, title: String, content: String, dateTimeMillis: Long)
    fun delete(requestCode: Int)

    companion object {
        const val TITLE_KEY = "title-key"
        const val CONTENT_KEY = "content-key"
        const val REQUEST_CODE_KEY = "request-code-key"
    }
}