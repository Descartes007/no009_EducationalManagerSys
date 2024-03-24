package com.example.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "成功"),

    PARAM_ERROR("400", "参数异常"),
    TOKEN_INVALID_ERROR("401", "无效的token"),
    TOKEN_CHECK_ERROR("401", "token验证失败，请重新登录"),
    PARAM_LOST_ERROR("4001", "参数缺失"),

    SYSTEM_ERROR("500", "系统异常"),
    USER_EXIST_ERROR("5001", "用户名已存在"),
    USER_NOT_LOGIN("5002", "用户未登录"),
    USER_ACCOUNT_ERROR("5003", "账号或密码错误"),
    USER_NOT_EXIST_ERROR("5004", "用户不存在"),
    PARAM_PASSWORD_ERROR("5005", "原密码输入错误"),
    COURSE_NUM_ERROR("5006", "该门课选课人数已满，请选择其他课程"),
    SCORE_ALREADY_ERROR("5007", "您已经录入了该学生该门课的成绩了"),
    COMMENT_ALREADY_ERROR("5008", "您已经对该门课的老师评教过了，请勿重复评教"),
    ATTENDANCE_ALREADY_ERROR("5009", "该学生当天的考勤已录入，请勿重复录入"),
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
