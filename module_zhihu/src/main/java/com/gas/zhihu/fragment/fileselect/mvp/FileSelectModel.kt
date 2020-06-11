package com.gas.zhihu.fragment.fileselect.mvp

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.VOLUME_EXTERNAL
import android.util.Log
import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.bean.FileItemBean
import com.lib.commonsdk.utils.AppUtils
import kotlinx.android.synthetic.main.zhihu_fragment_add_paper.*
import java.io.File
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/24/2020 11:32
 * ================================================
 */
@FragmentScope
class FileSelectModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), FileSelectContract.Model {

    override fun onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取手机文档数据
     *
     * @param
     */
    override fun getDocumentData(type: String?): List<FileItemBean> {

        if(type.isNullOrBlank()){
            return emptyList()
        }
        var select =""
        when (type) {
            ZhihuConstants.FILE_TYPE_WORD -> {
                select = "(_data LIKE '%.docx'or _data LIKE '%.doc')"
            }
            ZhihuConstants.FILE_TYPE_PDF -> {
                select = "(_data LIKE '%.pdf')"
            }
        }

        val fileItems = mutableListOf<FileItemBean>()
        val columns = arrayOf(MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.DATA)
        val contentResolver: ContentResolver = AppUtils.getApp().contentResolver
        val cursor: Cursor? = contentResolver.query(MediaStore.Files.getContentUri(VOLUME_EXTERNAL),
                columns,
                select,
                null,
                 "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC")
        var columnIndex = 0
        if (cursor != null) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val path: String = cursor.getString(columnIndex)
                val document: FileItemBean = FileItemBean.createFromFile(File(path))
                fileItems.add(document)
            }
        }
        cursor?.close()
        return fileItems
    }
}
