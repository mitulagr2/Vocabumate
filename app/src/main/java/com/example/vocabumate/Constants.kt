package com.example.vocabumate


// Notification Channel constants

// Name of Notification Channel for verbose notifications of background work
val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
  "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
  "Shows notifications whenever work starts"
val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

// The name of the fetch word work
const val FETCH_WORD_WORK_NAME = "fetch_word_work"

// Other keys
const val KEY_API_SERVICE = "KEY_API_SERVICE"
const val KEY_WORD_QUERY = "KEY_WORD_QUERY"
const val TAG_OUTPUT = "OUTPUT"
const val KEY_QUERY_OUTPUT = "KEY_QUERY_OUTPUT"
