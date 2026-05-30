package cn.xiaofuge.x.domain.chat.service;

import cn.xiaofuge.x.domain.chat.adapter.port.ChatModelResult;
import cn.xiaofuge.x.domain.chat.model.entity.ChatMessageEntity;

import java.util.List;

/**
 * 智能客服服务接口
 */
public interface IChatService {

    /**
     * 智能客服对话（单轮，无上下文）
     *
     * @param message 用户消息
     * @return 模型回复内容
     */
    String chat(String message);

    /**
     * 智能客服对话（多轮，带历史上下文）
     *
     * @param sessionId 会话ID
     * @param message   用户消息
     * @param history   历史对话消息
     * @return 对话详细结果（含元信息）
     */
    ChatModelResult chat(String sessionId, String message, List<ChatMessageEntity> history);
}
