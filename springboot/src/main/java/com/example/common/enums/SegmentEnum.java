package com.example.common.enums;

public enum SegmentEnum {

    FIRST("第一大节（08:00 ~ 09:30）"),
    SECOND("第二大节（09:40 ~ 12:00）"),
    THIRD("第三大节（14:00 ~ 15:30）"),
    FORTH("第四大节（15:40 ~ 17:00）"),
    FIFTH("第五大节（18:00 ~ 20:00）"),
    ;
    public String segment;

    SegmentEnum(String segment) {
        this.segment = segment;
    }
}
