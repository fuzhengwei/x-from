package cn.xiaofuge.x.api.dto;

/**
 * 提交表单请求DTO
 */
public class SubmitFormRequest {

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
}
