package org.eisen.dao.orm.model;

import java.util.Date;

/**
 * @Author: eisen
 * @DateTime: 2018-12-17 20:07:28
 * 开发描述:
 *   用户文件关联表
 */
public class TbUserFile {
    // id tb_user_file.data_id
    private Integer dataId;

    // user_info的用户id tb_user_file.user_id
    private Integer userId;

    // file_detail的文件id tb_user_file.file_id
    private Integer fileId;

    // 用户所见文件名称 tb_user_file.file_name
    private String fileName;

    // 用户所见文件路径 tb_user_file.file_path
    private String filePath;

    // 用户获取文件的时间 tb_user_file.file_date
    private Date fileDate;

    //获取 id
    public Integer getDataId() {
        return dataId;
    }

    //设置 id
    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    //获取 user_info的用户id
    public Integer getUserId() {
        return userId;
    }

    //设置 user_info的用户id
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    //获取 file_detail的文件id
    public Integer getFileId() {
        return fileId;
    }

    //设置 file_detail的文件id
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    //获取 用户所见文件名称
    public String getFileName() {
        return fileName;
    }

    //设置 用户所见文件名称
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    //获取 用户所见文件路径
    public String getFilePath() {
        return filePath;
    }

    //设置 用户所见文件路径
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //获取 用户获取文件的时间
    public Date getFileDate() {
        return fileDate;
    }

    //设置 用户获取文件的时间
    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }
}