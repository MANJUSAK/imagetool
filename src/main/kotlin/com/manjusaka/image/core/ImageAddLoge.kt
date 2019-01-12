package com.manjusaka.image.core

import com.sun.image.codec.jpeg.JPEGCodec
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream
import javax.imageio.ImageIO

/**
 * description:
 * ===>图片批量添加logo工具程序
 *
 * @author manjusaka[manjusakachn@gmail.com] Created on 2019-01-12 12:12
 * @version V1.0.0
 */
object ImageAddLoge {
    /**
     * @param photopath : 原图存放的文件夹路径
     * @param logopath : logo图像存放的路径
     * @param savepath : 目标输出保存的文件夹路径
     * @param x : logo图像在合并到原图中的显示位置x座标
     * @param y : logo图像在合并到原图中的显示位置y座标
     */
    fun findFileList(photoFolderPath: String, logoPath: String, saveFolderPath: String, x: Int, y: Int) {
        if (!File(logoPath).exists()) throw FileNotFoundException("$logoPath notFind file")
        val file = File(photoFolderPath)
        if (!file.exists()) throw FileNotFoundException("$photoFolderPath notFind folder or not is folder")
        val saveFile = File(saveFolderPath)
        if (!saveFile.exists()) {
            saveFile.mkdirs()
        }
        val fileList = file.listFiles()
        val len = fileList.size
        val pfpath = photoFolderPath + "\\"
        val sfpath = saveFolderPath + "\\"
        var i = 0f
        fileList.forEach {
            //添加logo  原图片路径  logo路径 生成合成图片路径 x,y
            addImageLogo(pfpath + it.name, logoPath, sfpath + it.name, x, y)
            ++i
            println("图片处理中：" + ((i / len) * 100) + "%")
        }
        println("成功处理 $len 张图片")
        Runtime.getRuntime().exec("cmd /c start explorer $saveFolderPath")
    }

    /**
     * @param photopath : 原图存放的路径
     * @param logopath : logo图像存放的路径
     * @param savepath : 目标输出保存的路径
     * @param x : logo图像在合并到原图中的显示位置x座标
     * @param y : logo图像在合并到原图中的显示位置y座标
     */
    fun addImageLogo(photopath: String, logopath: String, savepath: String, x: Int, y: Int) {
        var os: OutputStream? = null
        try {
            val image = ImageIO.read(File(photopath))
            val pwidth = image.width
            val pheight = image.height
            val buffimage = BufferedImage(pwidth, pheight, BufferedImage.TYPE_INT_BGR)
            val g = buffimage.createGraphics()
            g.drawImage(image, 0, 0, pwidth, pheight, null)

            val logo = ImageIO.read(File(logopath))
            val lwidth = logo.width
            val lheight = logo.height
            g.drawImage(logo, pwidth - x, pheight - y, lwidth, lheight, null)

            g.dispose()
            os = FileOutputStream(savepath)
            val encoder = JPEGCodec.createJPEGEncoder(os)
            encoder.encode(buffimage)
        } catch (e: Exception) {
            throw Exception("Add image watermark exception:" + e.message)
        } finally {
            os?.close()
        }
    }
}
