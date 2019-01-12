import com.manjusaka.image.core.ImageAddLoge

/**
 * description:
 * ===>
 * company
 * @author manjusaka[manjusakachn@gmail.com] Created on 2019-01-12 14:04
 * @version V1.0.0
 */
fun main(args: Array<String>) {
    val yLj = "C:\\Users\\Lenovo\\Desktop\\temp\\snap"
    val logoPath = "C:\\Users\\Lenovo\\Desktop\\logo.jpg"
    val savePath = "C:\\Users\\Lenovo\\Desktop\\temp\\test"
    ImageAddLoge.findFileList(yLj, logoPath, savePath, 550, 550)
}