package cn.xiaofuge.x.domain.form.model.valobj;

/**
 * 表单状态枚举
 */
public enum FormStatus {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    private final Integer code;
    private final String info;

    FormStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public static FormStatus getByCode(Integer code) {
        for (FormStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
