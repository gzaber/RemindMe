package com.gzaber.remindme.data.service

interface NotificationService {
    fun send(id: Int, title: String, content: String)

    companion object {
        const val CHANNEL_ID = "notification-channel-id"
    }
}