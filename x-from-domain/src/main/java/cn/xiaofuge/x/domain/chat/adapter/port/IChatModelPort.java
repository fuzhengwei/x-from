package cn.xiaofuge.x.domain.chat.adapter.port;

import cn.xiaofuge.x.domain.chat.model.entity.ChatMessageEntity;

import java.util.List;

/**
 * 大模型端口接口（领域层定义，基础设施层实现）
 * <p>
 * 支持简单对话和详细对话（含元信息返回）
 */
public interface IChatModelPort {

    /**
     * 调用大模型进行对话（使用默认模型）
     *
     * @param messages 对话消息列表（包含历史上下文）
     * @return 模型回复内容
     */
    String chat(List<ChatMessageEntity> messages);

    /**
     * 调用大模型进行对话（指定模型）
     *
     * @param messages 对话消息列表
     * @param model    模型名称
     * @return 模型回复内容
     */
    String chat(List<ChatMessageEntity> messages, String model);

    /**
     * 调用大模型进行对话（返回详细结果，含元信息）
     *
     * @param messages 对话消息列表
     * @param model    模型名称
     * @return 对话结果（含 token 数、耗时等元信息）
     */
    ChatModelResult chatDetail(List<ChatMessageEntity> messages, String model);
}
