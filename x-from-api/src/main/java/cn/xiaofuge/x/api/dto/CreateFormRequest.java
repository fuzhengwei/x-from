package cn.xiaofuge.x.api.dto;

/**
 * 创建表单请求DTO
 */
public class CreateFormRequest {

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单描述
     */
    private String formDesc;

    /**
     * 表单配置JSON
     */
    private String formConfig;

    /**
     * 创建人
     */
    private String creator;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormDesc() {
        return formDesc;
    }

    public void setFormDesc(String formDesc) {
        this.formDesc = formDesc;
    }

    public String getFormConfig() {
        return formConfig;
    }

    public void setFormConfig(String formConfig) {
        this.formConfig = formConfig;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
