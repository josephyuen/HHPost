package com.hhpost.courier.util

import java.io.*

/**
 * 创建者: (WangXu --- The Great God)
 * 功能描述: 一看就知道: 序列化与反序列化对象的工具类
 */
object SerializableUtils {

    fun <T : Serializable> readObject(file: File): Any? {
        var `in`: ObjectInputStream? = null
        var t: T? = null
        try {
            `in` = ObjectInputStream(FileInputStream(file))
            t = `in`.readObject() as T
        } catch (e: EOFException) {
            // ... this is fine
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } finally {
            try {
                `in`?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return t
    }

    fun <T : Serializable> writeObject(t: T, fileName: String): Boolean {
        var out: ObjectOutputStream? = null
        try {
            out = ObjectOutputStream(FileOutputStream(fileName))
            out.writeObject(t)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    @Throws(IOException::class)
    fun getSerializableFile(rootPath: String, fileName: String): File {
        val file = File(rootPath)
        if (!file.exists()) file.mkdirs()
        val serializable = File(file, fileName)
        if (!serializable.exists()) serializable.createNewFile()
        return serializable
    }


}
