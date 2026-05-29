package cn.xiaofuge.x.domain.form.model.entity;

import java.util.Date;

/**
 * 表单提交记录实体
 */
public class FormSubmissionEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 提交数据JSON
     */
    private String submissionData;

    /**
     * 提交人（可选）
     */
    private String submitter;

    /**
     * 提交人IP
     */
    private String submitterIp;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(String submissionData) {
        this.submissionData = submissionData;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getSubmitterIp() {
        return submitterIp;
    }

    public void setSubmitterIp(String submitterIp) {
        this.submitterIp = submitterIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long formId;
        private String formCode;
        private String submissionData;
        private String submitter;
        private String submitterIp;
        private Date createTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder formId(Long formId) {
            this.formId = formId;
            return this;
        }

        public Builder formCode(String formCode) {
            this.formCode = formCode;
            return this;
        }

        public Builder submissionData(String submissionData) {
            this.submissionData = submissionData;
            return this;
        }

        public Builder submitter(String submitter) {
            this.submitter = submitter;
            return this;
        }

        public Builder submitterIp(String submitterIp) {
            this.submitterIp = submitterIp;
            return this;
        }

        public Builder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public FormSubmissionEntity build() {
            FormSubmissionEntity entity = new FormSubmissionEntity();
            entity.id = this.id;
            entity.formId = this.formId;
            entity.formCode = this.formCode;
            entity.submissionData = this.submissionData;
            entity.submitter = this.submitter;
            entity.submitterIp = this.submitterIp;
            entity.createTime = this.createTime;
            return entity;
        }
    }
}
