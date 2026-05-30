package cn.xiaofuge.x.api.dto;

/**
 * 智能客服对话响应
 */
public class ChatResponseDTO {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 模型回复内容
     */
    private String reply;

    /**
     * 使用的模型名称
     */
    private String model;

    /**
     * 生成耗时（毫秒）
     */
    private Long durationMs;

    /**
     * 输出 token 数量
     */
    private Integer evalCount;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }

    public Integer getEvalCount() {
        return evalCount;
    }

    public void setEvalCount(Integer evalCount) {
        this.evalCount = evalCount;
    }
}
