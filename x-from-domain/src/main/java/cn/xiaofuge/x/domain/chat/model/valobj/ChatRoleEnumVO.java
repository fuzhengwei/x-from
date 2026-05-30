package cn.xiaofuge.x.domain.chat.model.valobj;

/**
 * 聊天消息角色枚举
 */
public enum ChatRoleEnumVO {

    SYSTEM("system", "系统提示"),
    USER("user", "用户消息"),
    ASSISTANT("assistant", "助手回复");

    private final String code;
    private final String info;

    ChatRoleEnumVO(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static ChatRoleEnumVO getByCode(String code) {
        for (ChatRoleEnumVO role : values()) {
            if (role.code.equals(code)) {
                return role;
            }
        }
        return USER;
    }
}
