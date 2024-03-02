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
const val GET_WORD_WORK_NAME = "get_word_work"
const val MODIFY_WORD_WORK_NAME = "modify_word_work"

// Other keys
const val TAG_OUTPUT = "OUTPUT"
const val KEY_PAYLOAD = "KEY_ACTION_PAYLOAD"
const val KEY_ACTION = "KEY_ACTION_TYPE"
const val KEY_OUTPUT_DATA = "KEY_OUTPUT_DATA"
const val KEY_OUTPUT_TYPE = "KEY_OUTPUT_TYPE"
