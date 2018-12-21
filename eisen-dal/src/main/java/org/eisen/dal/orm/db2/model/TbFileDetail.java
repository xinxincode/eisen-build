package org.eisen.dal.orm.db2.model;

import java.util.Date;

/**
 * @Author: eisen
 * @DateTime: 2018-12-21 10:54:37
 * 开发描述:
 *   文件存储明细表
 */
public class TbFileDetail {
    //文件id 主键 tb_file_detail.file_id
    private Integer fileId;

    //存储在服务器的文件名 tb_file_detail.file_name
    private String fileName;

    //存储在服务器的相对文件路径且不包含文件名 如: /file/path/ tb_file_detail.file_path
    private String filePath;

    //文件的sha-512值 非空 不重索引 tb_file_detail.sha_512
    private String sha512;

    //文件的sha-256值 可为空 tb_file_detail.sha_256
    private String sha256;

    //文件的sha1值 可为空 tb_file_detail.sha_1
    private String sha1;

    //文件的md5值 可为空 tb_file_detail.md5
    private String md5;

    //文件存到服务器的时间 默认当前时间 tb_file_detail.create_datetime
    private Date createDatetime;

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

    public String getSha512() {
        return sha512;
    }

    public void setSha512(String sha512) {
        this.sha512 = sha512;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
}