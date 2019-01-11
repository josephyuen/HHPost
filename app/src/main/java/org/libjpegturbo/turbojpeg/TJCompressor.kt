package org.libjpegturbo.turbojpeg

/**
 * Creator: 李佳胜
 * FuncDesc:
 * copyright  ©2018-2020 艾戴特 Corporation. All rights reserved.
 */

import java.io.Closeable

/**
 * TurboJPEG compressor
 */
open class TJCompressor @Throws(TJException::class)
constructor() : Closeable {

    private var handle: Long = 0
    private var srcBuf: ByteArray? = null
    private var srcBufInt: IntArray? = null
    private var srcWidth = 0
    private var srcHeight = 0
    private var srcX = -1
    private var srcY = -1
    private var srcPitch = 0
    private var srcPixelFormat = -1
    private val subsamp = TJ.SAMP_440
    private val jpegQuality = 90

    init {
        init()
        handle = 1
    }

    @Throws(TJException::class)
    override fun close() {
        destroy()
        handle = 0
    }

    @Throws(TJException::class)
    protected fun finalize() {
        try {
            close()
        } catch (e: TJException) {
        }
    }

    @Throws(TJException::class)
    fun setSourceImage(
        srcImage: ByteArray?, x: Int, y: Int, width: Int,
        pitch: Int, height: Int, pixelFormat: Int
    ) {
        if (handle == 0L) init()
        if (srcImage == null || x < 0 || y < 0 || width < 1 || height < 1 ||
            pitch < 0 || pixelFormat < 0 || pixelFormat >= TJ.NUMPF
        )
            throw IllegalArgumentException("Invalid argument in setSourceImage()")
        srcBuf = srcImage
        srcWidth = width
        if (pitch == 0)
            srcPitch = width * TJ.getPixelSize(pixelFormat)
        else
            srcPitch = pitch
        srcHeight = height
        srcPixelFormat = pixelFormat
        srcX = x
        srcY = y
        srcBufInt = null
    }


    /**
     *
     * @param dstBuf  buffer that will receive the JPEG image.
     * @param flags   the bitwise OR of one or more of TJ.FLAG_*
     * @return  Image size
     */
    @Throws(Exception::class)
    fun compress(dstBuf: ByteArray?, flags: Int): Int {
        if (dstBuf == null || flags < 0)
            throw IllegalArgumentException("Invalid argument in compress()")
        if (srcBuf == null && srcBufInt == null)
            throw IllegalStateException("NO_ASSOC_ERROR")
        if (jpegQuality < 0)
            throw IllegalStateException("JPEG Quality not set")

        return compress(
            srcBuf, srcX, srcY, srcWidth, srcPitch,
            srcHeight, srcPixelFormat, dstBuf, subsamp,
            jpegQuality, flags
        )
    }


    @Throws(TJException::class)
    private external fun init()

    @Throws(TJException::class)
    private external fun destroy()


    @Throws(Exception::class)
    private external fun compress(
        srcBuf: ByteArray?, x: Int, y: Int, width: Int,
        pitch: Int, height: Int, pixelFormat: Int, jpegBuf: ByteArray, jpegSubsamp: Int,
        jpegQual: Int, flags: Int
    ): Int

    companion object {

        init {
            System.loadLibrary("turbojpeg")

        }
    }


}
