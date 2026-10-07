package com.dcelysia.csust_spider.core

/**
 * No-op logger for the local security build.
 *
 * Authentication credentials, cookies, request/response bodies and HTML must never be written
 * to logcat. These compatibility overloads keep upstream call sites unchanged while ensuring
 * that this local AAR emits no Android logs.
 */
internal object SafeLog {
    fun d(tag: String?, message: String?): Int = 0
    fun d(tag: String?, message: String?, throwable: Throwable?): Int = 0
    fun d(tag: String?, throwable: Throwable?): Int = 0

    fun e(tag: String?, message: String?): Int = 0
    fun e(tag: String?, message: String?, throwable: Throwable?): Int = 0
    fun e(tag: String?, throwable: Throwable?): Int = 0

    fun w(tag: String?, message: String?): Int = 0
    fun w(tag: String?, message: String?, throwable: Throwable?): Int = 0
    fun w(tag: String?, throwable: Throwable?): Int = 0
}
