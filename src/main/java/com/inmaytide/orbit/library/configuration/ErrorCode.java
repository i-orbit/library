package com.inmaytide.orbit.library.configuration;

/**
 * @author inmaytide
 * @since 2023/5/6
 */
public enum ErrorCode implements com.inmaytide.exception.web.domain.ErrorCode {

    E_0x00300001("0x00300001", "不支持的文件类型"),
    E_0x00300002("0x00300002", "无效的系统配置项或该配置项没有配置有效值"),
    ;

    private final String value;

    private final String description;

    ErrorCode(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public String description() {
        return this.description;
    }
}
