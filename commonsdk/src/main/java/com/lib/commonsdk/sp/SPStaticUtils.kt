package com.lib.commonsdk.sp

/**
 * ================================================
 * setDefaultSPUtils: 设置默认 SP 实例
 * put              : SP 中写入数据
 * getString        : SP 中读取 String
 * getInt           : SP 中读取 int
 * getLong          : SP 中读取 long
 * getFloat         : SP 中读取 float
 * getBoolean       : SP 中读取 boolean
 * getAll           : SP 中获取所有键值对
 * contains         : SP 中是否存在该 key
 * remove           : SP 中移除该 key
 * clear            : SP 中清除所有数据
 * ================================================
 */
object SPStaticUtils {
    
    private var sDefaultSPUtils: SPUtils? = null

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or `""` otherwise
     */
    fun getString(key: String): String? {
        return getString(key, defaultSPUtils)
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or `defaultValue` otherwise
     */
    fun getString(key: String, defaultValue: String): String? {
        return getString(key, defaultValue, defaultSPUtils)
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or `-1` otherwise
     */
    fun getInt(key: String): Int {
        return getInt(key, defaultSPUtils)
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or `defaultValue` otherwise
     */
    fun getInt(key: String, defaultValue: Int): Int {
        return getInt(key, defaultValue, defaultSPUtils)
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or `-1` otherwise
     */
    fun getLong(key: String): Long {
        return getLong(key, defaultSPUtils)
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or `defaultValue` otherwise
     */
    fun getLong(key: String, defaultValue: Long): Long {
        return getLong(key, defaultValue, defaultSPUtils)
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or `-1f` otherwise
     */
    fun getFloat(key: String): Float {
        return getFloat(key, defaultSPUtils)
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or `defaultValue` otherwise
     */
    fun getFloat(key: String, defaultValue: Float): Float {
        return getFloat(key, defaultValue, defaultSPUtils)
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or `false` otherwise
     */
    fun getBoolean(key: String): Boolean {
        return getBoolean(key, defaultSPUtils)
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or `defaultValue` otherwise
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return getBoolean(key, defaultValue, defaultSPUtils)
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or `Collections.<String>emptySet()` otherwise
     */
    fun getStringSet(key: String): Set<String>? {
        return getStringSet(key, defaultSPUtils)
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or `defaultValue` otherwise
     */
    fun getStringSet(key: String,
                     defaultValue: Set<String?>?): Set<String>? {
        return getStringSet(key, defaultValue, defaultSPUtils)
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    val all: Map<String, *>
        get() = getAll(defaultSPUtils)
    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Put the string value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of [SPUtils].
     */
    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    @JvmOverloads
    fun put(key: String, value: String, spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value)
    }
    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: String?,
            isCommit: Boolean,
            spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value, isCommit)
    }

    /**
     * Return the string value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     * @return the string value if sp exists or `""` otherwise
     */
    fun getString(key: String, spUtils: SPUtils): String? {
        return spUtils.getString(key)
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of [SPUtils].
     * @return the string value if sp exists or `defaultValue` otherwise
     */
    fun getString(key: String,
                  defaultValue: String,
                  spUtils: SPUtils): String? {
        return spUtils.getString(key, defaultValue)
    }
    /**
     * Put the int value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of [SPUtils].
     */
    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    @JvmOverloads
    fun put(key: String, value: Int, spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value)
    }
    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: Int,
            isCommit: Boolean,
            spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value, isCommit)
    }

    /**
     * Return the int value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     * @return the int value if sp exists or `-1` otherwise
     */
    fun getInt(key: String, spUtils: SPUtils): Int {
        return spUtils.getInt(key)
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of [SPUtils].
     * @return the int value if sp exists or `defaultValue` otherwise
     */
    fun getInt(key: String, defaultValue: Int, spUtils: SPUtils): Int {
        return spUtils.getInt(key, defaultValue)
    }
    /**
     * Put the long value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of [SPUtils].
     */
    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    @JvmOverloads
    fun put(key: String, value: Long, spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value)
    }
    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: Long,
            isCommit: Boolean,
            spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value, isCommit)
    }

    /**
     * Return the long value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     * @return the long value if sp exists or `-1` otherwise
     */
    fun getLong(key: String, spUtils: SPUtils): Long {
        return spUtils.getLong(key)
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of [SPUtils].
     * @return the long value if sp exists or `defaultValue` otherwise
     */
    fun getLong(key: String, defaultValue: Long, spUtils: SPUtils): Long {
        return spUtils.getLong(key, defaultValue)
    }
    /**
     * Put the float value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of [SPUtils].
     */
    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    @JvmOverloads
    fun put(key: String, value: Float, spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value)
    }
    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: Float,
            isCommit: Boolean,
            spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value, isCommit)
    }

    /**
     * Return the float value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     * @return the float value if sp exists or `-1f` otherwise
     */
    fun getFloat(key: String, spUtils: SPUtils): Float {
        return spUtils.getFloat(key)
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of [SPUtils].
     * @return the float value if sp exists or `defaultValue` otherwise
     */
    fun getFloat(key: String, defaultValue: Float, spUtils: SPUtils): Float {
        return spUtils.getFloat(key, defaultValue)
    }
    /**
     * Put the boolean value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of [SPUtils].
     */
    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    @JvmOverloads
    fun put(key: String, value: Boolean, spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value)
    }
    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: Boolean,
            isCommit: Boolean,
            spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value, isCommit)
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     * @return the boolean value if sp exists or `false` otherwise
     */
    fun getBoolean(key: String, spUtils: SPUtils): Boolean {
        return spUtils.getBoolean(key)
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of [SPUtils].
     * @return the boolean value if sp exists or `defaultValue` otherwise
     */
    fun getBoolean(key: String,
                   defaultValue: Boolean,
                   spUtils: SPUtils): Boolean {
        return spUtils.getBoolean(key, defaultValue)
    }
    /**
     * Put the set of string value in sp.
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of [SPUtils].
     */
    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    @JvmOverloads
    fun put(key: String, value: Set<String?>?, spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value)
    }
    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun put(key: String,
            value: Set<String?>?,
            isCommit: Boolean,
            spUtils: SPUtils = defaultSPUtils) {
        spUtils.put(key, value, isCommit)
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     * @return the set of string value if sp exists
     * or `Collections.<String>emptySet()` otherwise
     */
    fun getStringSet(key: String, spUtils: SPUtils): Set<String>? {
        return spUtils.getStringSet(key)
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of [SPUtils].
     * @return the set of string value if sp exists or `defaultValue` otherwise
     */
    fun getStringSet(key: String,
                     defaultValue: Set<String?>?,
                     spUtils: SPUtils): Set<String>? {
        return spUtils.getStringSet(key, defaultValue)
    }

    /**
     * Return all values in sp.
     *
     * @param spUtils The instance of [SPUtils].
     * @return all values in sp
     */
    fun getAll(spUtils: SPUtils): Map<String, *> {
        return spUtils.all
    }
    /**
     * Return whether the sp contains the preference.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     * @return `true`: yes<br></br>`false`: no
     */
    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return `true`: yes<br></br>`false`: no
     */
    @JvmOverloads
    fun contains(key: String, spUtils: SPUtils = defaultSPUtils): Boolean {
        return spUtils.contains(key)
    }
    /**
     * Remove the preference in sp.
     *
     * @param key     The key of sp.
     * @param spUtils The instance of [SPUtils].
     */
    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    @JvmOverloads
    fun remove(key: String, spUtils: SPUtils = defaultSPUtils) {
        spUtils.remove(key)
    }
    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    @JvmOverloads
    fun remove(key: String, isCommit: Boolean, spUtils: SPUtils = defaultSPUtils) {
        spUtils.remove(key, isCommit)
    }
    /**
     * Remove all preferences in sp.
     *
     * @param spUtils The instance of [SPUtils].
     */
    @JvmOverloads
    fun clear(spUtils: SPUtils = defaultSPUtils) {
        spUtils.clear()
    }
    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     * @param spUtils  The instance of [SPUtils].
     */
    @JvmOverloads
    fun clear(isCommit: Boolean, spUtils: SPUtils = defaultSPUtils) {
        spUtils.clear(isCommit)
    }

    /**
     * Set the default instance of [SPUtils].
     *
     * @param spUtils The default instance of [SPUtils].
     */
    private var defaultSPUtils: SPUtils
        get() = if (sDefaultSPUtils != null) sDefaultSPUtils!! else SPUtils.getInstance()
        set(spUtils) {
            sDefaultSPUtils = spUtils
        }
}