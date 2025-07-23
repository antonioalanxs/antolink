package org.example.backend.exception

/**
 * Custom exception for duplicate key errors.
 */
class CustomDuplicateKeyException(message: String?) : RuntimeException(message) {
    /**
     * Returns the duplicate key from the exception message.
     *
     * @return A human-readable message with the duplicate key.
     */
    fun message(): String {
        val key = """dup key: (\{.*?\})""".toRegex().find(message!!)!!.groupValues[1]
        return "$key already exists"
    }
}