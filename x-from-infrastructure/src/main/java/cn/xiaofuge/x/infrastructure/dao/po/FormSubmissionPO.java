package cn.xiaofuge.x.infrastructure.dao.po;

import java.util.Date;

/**
 * 表单提交记录PO
 */
public class FormSubmissionPO {

    private Long id;
    private Long formId;
    private String formCode;
    private String submissionData;
    private String submitter;
    private String submitterIp;
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

        public FormSubmissionPO build() {
            FormSubmissionPO po = new FormSubmissionPO();
            po.id = this.id;
            po.formId = this.formId;
            po.formCode = this.formCode;
            po.submissionData = this.submissionData;
            po.submitter = this.submitter;
            po.submitterIp = this.submitterIp;
            po.createTime = this.createTime;
            return po;
        }
    }
}
