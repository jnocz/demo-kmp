package datasource.secure

import android.content.SharedPreferences

class EncryptedSharedPreferencesDataSourceProvider(private val sharedPreferences: SharedPreferences) :
    SecureDataStoreProvider {

    override fun set(key: String, value: Boolean): Boolean = storeValue(key, value)
    override fun set(key: String, value: Double): Boolean = storeValue(key, value)
    override fun set(key: String, value: Float): Boolean = storeValue(key, value)
    override fun set(key: String, value: Int): Boolean = storeValue(key, value)
    override fun set(key: String, value: Long): Boolean = storeValue(key, value)
    override fun set(key: String, value: String): Boolean = storeValue(key, value)

    override fun bool(forKey: String): Boolean? = sharedPreferences.getBoolean(forKey, true)
    override fun int(forKey: String): Int? = sharedPreferences.getInt(forKey, 0)
    override fun float(forKey: String): Float? = sharedPreferences.getFloat(forKey, 0f)
    override fun double(forKey: String): Double? = sharedPreferences.getDouble(forKey, 0.0)
    override fun long(forKey: String): Long? = sharedPreferences.getLong(forKey, 0)
    override fun string(forKey: String): String? = sharedPreferences.getString(forKey, "")

    override fun keys(): List<String> = listOf()
    override fun exists(forKey: String): Boolean = sharedPreferences.contains(forKey)
    override fun delete(forKey: String): Boolean = clearValue(forKey)
    override fun clear(): Boolean = false

    fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
        putLong(key, java.lang.Double.doubleToRawLongBits(double))

    fun SharedPreferences.getDouble(key: String, default: Double) =
        java.lang.Double.longBitsToDouble(
            getLong(
                key,
                java.lang.Double.doubleToRawLongBits(default)
            )
        )


    private fun storeValue(key: String, value: String): Boolean {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
        return true
    }

    private fun storeValue(key: String, value: Long): Boolean {
        with(sharedPreferences.edit()) {
            putLong(key, value)
            apply()
        }
        return true
    }

    private fun storeValue(key: String, value: Double): Boolean {
        with(sharedPreferences.edit()) {
            putDouble(key, value)
            apply()
        }
        return true
    }

    private fun storeValue(key: String, value: Float): Boolean {
        with(sharedPreferences.edit()) {
            putFloat(key, value)
            apply()
        }
        return true
    }

    private fun storeValue(key: String, value: Boolean): Boolean {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
        return true
    }

    private fun storeValue(key: String, value: Int): Boolean {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
        return true
    }


    private fun storeValue(key: String, value: Set<String>): Boolean {
        with(sharedPreferences.edit()) {
            putStringSet(key, value)
            apply()
        }
        return true
    }

    private fun clearValue(key: String): Boolean {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
        return true
    }
}