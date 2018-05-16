package com.qiancheng.om.common.utils;

/**
 * 密码对象
 *
 * @author Brendan Lee
 */
public class Password {
    private String salt;
    private String hash;

    /**
     * Getter for property 'salt'.
     *
     * @return Value for property 'salt'.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Setter for property 'salt'.
     *
     * @param salt Value to set for property 'salt'.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Getter for property 'hash'.
     *
     * @return Value for property 'hash'.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Setter for property 'hash'.
     *
     * @param hash Value to set for property 'hash'.
     */
    public void setHash(String hash) {
        this.hash = hash;
    }
}
