package com.ppfuns.report.entity.base;

public class PpfunsEntity {
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String userTypeSelect(String userType) {
        return this.userType.equalsIgnoreCase(userType)?"selected":"";
    }
}
