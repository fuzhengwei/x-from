package cn.xiaofuge.x.domain.form.model.entity;

import cn.xiaofuge.x.domain.form.model.valobj.FormStatus;

import java.util.Date;

/**
 * 表单实体
 */
public class FormEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 表单编码（唯一标识）
     */
    private String formCode;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单描述
     */
    private String formDesc;

    /**
     * 表单配置JSON（字段定义）
     */
    private String formConfig;

    /**
     * 状态
     */
    private FormStatus status;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

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

    public FormStatus getStatus() {
        return status;
    }

    public void setStatus(FormStatus status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String formCode;
        private String formName;
        private String formDesc;
        private String formConfig;
        private FormStatus status;
        private String creator;
        private Date createTime;
        private Date updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder formCode(String formCode) {
            this.formCode = formCode;
            return this;
        }

        public Builder formName(String formName) {
            this.formName = formName;
            return this;
        }

        public Builder formDesc(String formDesc) {
            this.formDesc = formDesc;
            return this;
        }

        public Builder formConfig(String formConfig) {
            this.formConfig = formConfig;
            return this;
        }

        public Builder status(FormStatus status) {
            this.status = status;
            return this;
        }

        public Builder creator(String creator) {
            this.creator = creator;
            return this;
        }

        public Builder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public FormEntity build() {
            FormEntity entity = new FormEntity();
            entity.id = this.id;
            entity.formCode = this.formCode;
            entity.formName = this.formName;
            entity.formDesc = this.formDesc;
            entity.formConfig = this.formConfig;
            entity.status = this.status;
            entity.creator = this.creator;
            entity.createTime = this.createTime;
            entity.updateTime = this.updateTime;
            return entity;
        }
    }
}
