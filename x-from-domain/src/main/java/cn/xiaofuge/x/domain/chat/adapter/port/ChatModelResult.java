package cn.xiaofuge.x.domain.chat.adapter.port;

import cn.xiaofuge.x.domain.chat.model.entity.ChatMessageEntity;

import java.util.List;

/**
 * 大模型对话结果（含元信息）
 */
public class ChatModelResult {

    /**
     * 模型回复内容
     */
    private String content;

    /**
     * 模型名称
     */
    private String model;

    /**
     * 生成耗时（纳秒）
     */
    private Long totalDuration;

    /**
     * 输出 token 数
     */
    private Integer evalCount;

    /**
     * Prompt token 数
     */
    private Integer promptEvalCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getEvalCount() {
        return evalCount;
    }

    public void setEvalCount(Integer evalCount) {
        this.evalCount = evalCount;
    }

    public Integer getPromptEvalCount() {
        return promptEvalCount;
    }

    public void setPromptEvalCount(Integer promptEvalCount) {
        this.promptEvalCount = promptEvalCount;
    }
}
