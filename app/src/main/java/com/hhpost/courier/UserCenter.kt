package com.hhpost.courier

import android.content.Context
import android.content.SharedPreferences
import com.hhpost.courier.entity.User

/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述: 这里是管理用户数据的中心
 */
class UserCenter private constructor() {

    private var mContext: Context? = null

    private var preferences: SharedPreferences? = null

    private var token: String? = null   //  用户令牌

    private val localToken: String?
        get() {
            val sharedPreferences = mContext!!.getSharedPreferences(
                TAG, Context.MODE_PRIVATE
            )
            return sharedPreferences.getString(KEY_TOKEN, null)
        }

    // CurrentUser 相关
    private var mCurrentUser: User? = null

    //uid
    var currentUser: User
        get() {
            if (mCurrentUser == null) {
                mCurrentUser = currentUserFromLocal
                if (mCurrentUser == null) {
                    throw IllegalStateException("CurrentUser has not been initialized")
                }

            }
            return mCurrentUser!!
        }
        set(user) {
            if (mCurrentUser == null)
                mCurrentUser = User()
            mCurrentUser!!.uid = user.uid
            saveCurrentLocalUser(mCurrentUser!!)
        }

    private val currentUserFromLocal: User
        get() {
            val user = User()
            user.uid = preferences!!.getLong("uid", 0)
            return user

        }

    fun getToken(): String? {
        if (token == null) {
            token = localToken
        }
        return token
    }

    fun setToken(token: String) {
        this.token = token
        saveLocalToken(token)
    }

    private fun saveLocalToken(token: String) {
        val sharedPreferences = mContext!!.getSharedPreferences(
            TAG, Context.MODE_PRIVATE
        )
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    private fun saveCurrentLocalUser(user: User) {
        preferences!!.edit()
            .putLong("uid", user.uid)
            .apply()
    }

    companion object {

        private const val TAG = "UserCenter"

        // ***单例的用户中心
        var instance: UserCenter? = null
            private set

        internal fun initInstance(context: Context): UserCenter {
            if (instance == null) {
                instance = UserCenter()
                instance!!.mContext = context
                instance!!.preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)

            }
            return instance!!
        }

        // *****Token相关
        private const val KEY_TOKEN = "tokens"
    }

}
