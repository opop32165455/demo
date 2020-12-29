package com.fromZero.zeroShiro.consts;

/**
 * @author lin
 * @since 2017/4/7.
 */
public enum AuthResponse {

    SUCCESS_CODE(0, "SUCCESS"),
    FAILED_CODE(-1, "FAILED"),
    NO_AUTH(101, "NO_AUTH"),
    NO_LOGIN(102, "NO_LOGIN"),
    NO_PROMISSION(103, "NO_PROMISSION"),
    EMAIL_EXISTS(104, "EMAIL_EXISTS"),
    NO_USER(105, "NO_USER"),
    PASSWD_ERROR(106, "PASSWD_ERROR"),
    USER_LOCKED(107, "USER_LOCKED"),
    USERGROUP_CANNOT_DELETE(108, "USERGROUP_CANNOT_DELETE"),
    NAME_EXISTS(109, "NAME_EXISTS"),
    DIFF_ARELOGIN(110, "DIFF_ARELOGIN"),
    PROJECT_USED(111, "project.exit");

    public int code;

    private String msg;

    AuthResponse() {

    }

    AuthResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
