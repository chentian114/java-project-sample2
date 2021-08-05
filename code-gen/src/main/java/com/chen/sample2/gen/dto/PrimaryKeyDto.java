package com.chen.sample2.gen.dto;

/**
 * @desc
 * @Author Chentian
 * @date 2021/8/5
 */
public class PrimaryKeyDto {
    private String primaryType;
    private String primaryName;
    private boolean isUUID;

    public PrimaryKeyDto(){

    }

    public PrimaryKeyDto(String primaryType, String primaryName, boolean isUUID) {
        this.primaryType = primaryType;
        this.primaryName = primaryName;
        this.isUUID = isUUID;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public boolean isUUID() {
        return isUUID;
    }

    public void setUUID(boolean UUID) {
        isUUID = UUID;
    }
}
