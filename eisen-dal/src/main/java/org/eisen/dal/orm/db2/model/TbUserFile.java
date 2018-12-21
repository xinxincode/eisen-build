package org.eisen.dal.orm.db2.model;

import java.util.Date;

/**
 * @Author: eisen
 * @DateTime: 2018-12-21 10:54:37
 * 开发描述:
 *   用户文件关联表
 */
public class TbUserFile {
    //id tb_user_file.data_id
    private Integer dataId;

    //user_info的用户id tb_user_file.user_id
    private Integer userId;

    //file_detail的文件id tb_user_file.file_id
    private Integer fileId;

    //用户所见文件名称 tb_user_file.file_name
    private String fileName;

    //用户所见文件路径 tb_user_file.file_path
    private String filePath;

    //用户获取文件的时间 tb_user_file.file_date
    private Date fileDate;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }
}