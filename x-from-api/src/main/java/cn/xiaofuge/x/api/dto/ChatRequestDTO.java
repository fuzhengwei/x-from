package cn.xiaofuge.x.api.dto;

import java.util.List;

/**
 * 智能客服对话请求
 */
public class ChatRequestDTO {

    /**
     * 会话ID（首次对话可为空，后续传入以保持上下文）
     */
    private String sessionId;

    /**
     * 用户消息内容
     */
    private String message;

    /**
     * 历史对话消息（可选，用于多轮上下文）
     */
    private List<ChatMessageDTO> history;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChatMessageDTO> getHistory() {
        return history;
    }

    public void setHistory(List<ChatMessageDTO> history) {
        this.history = history;
    }

    /**
     * 对话消息DTO
     */
    public static class ChatMessageDTO {

        /**
         * 角色：user / assistant / system
         */
        private String role;

        /**
         * 消息内容
         */
        private String content;

        public ChatMessageDTO() {
        }

        public ChatMessageDTO(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
