package cn.xiaofuge.x.infrastructure.dao.po;

import java.util.Date;

/**
 * 表单PO
 */
public class FormPO {

    private Long id;
    private String formCode;
    private String formName;
    private String formDesc;
    private String formConfig;
    private Integer status;
    private String creator;
    private Date createTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        private Integer status;
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

        public Builder status(Integer status) {
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

        public FormPO build() {
            FormPO po = new FormPO();
            po.id = this.id;
            po.formCode = this.formCode;
            po.formName = this.formName;
            po.formDesc = this.formDesc;
            po.formConfig = this.formConfig;
            po.status = this.status;
            po.creator = this.creator;
            po.createTime = this.createTime;
            po.updateTime = this.updateTime;
            return po;
        }
    }
}
