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
const val KEY_WORD_QUERY = "KEY_WORD_QUERY"
const val TAG_REMOTE_OUTPUT = "REMOTE_OUTPUT"
const val KEY_QUERY_OUTPUT = "KEY_QUERY_OUTPUT"
const val KEY_LOCAL_OUTPUT = "KEY_LOCAL_OUTPUT"
const val TAG_LOCAL_OUTPUT = "LOCAL_OUTPUT"
const val KEY_ACTION_TYPE = "KEY_ACTION_TYPE"
const val KEY_ACTION_PAYLOAD = "KEY_ACTION_PAYLOAD"
