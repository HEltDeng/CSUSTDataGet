package com.dcelysia.csust_spider.core

/**
 * Authentication parameters supplied by the host application, never by this library binary.
 *
 * The host must configure these before using authenticated campus-card methods. Values are
 * passed through unchanged; this does not change the remote API or its authorization scheme.
 * Do not generate these values into checked-in source, BuildConfig, resources, or the AAR.
 */
object CampusCardAuthConfig {
    private class Credentials(val basicAuth: String, val chargeAuth: String)

    @Volatile
    private var credentials: Credentials? = null

    @JvmStatic
    fun configure(campusCardBasicAuth: String, chargeAuth: String) {
        validate(campusCardBasicAuth)
        validate(chargeAuth)
        credentials = Credentials(campusCardBasicAuth, chargeAuth)
    }

    @JvmStatic
    fun clear() {
        credentials = null
    }

    internal fun requireBasicAuth(): String = requireCredentials().basicAuth

    internal fun requireChargeAuth(): String = requireCredentials().chargeAuth

    private fun requireCredentials(): Credentials = checkNotNull(credentials) {
        "Campus-card authentication is not configured by the host application"
    }

    private fun validate(value: String) {
        require(value.isNotBlank() && '\r' !in value && '\n' !in value) {
            "Campus-card authentication must be nonblank and must not contain line breaks"
        }
    }
}
