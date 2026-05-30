package cn.xiaofuge.x.infrastructure.adapter.port;

import cn.xiaofuge.x.domain.chat.adapter.port.ChatModelResult;
import cn.xiaofuge.x.domain.chat.adapter.port.IChatModelPort;
import cn.xiaofuge.x.domain.chat.model.entity.ChatMessageEntity;
import cn.xiaofuge.x.infrastructure.gateway.OllamaGateway;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 大模型端口实现 - Ollama
 * <p>
 * 实现 Domain 层定义的 IChatModelPort 接口，
 * 通过 OllamaGateway 调用 Ollama API
 */
@Component
public class ChatModelPort implements IChatModelPort {

    private static final Logger log = LoggerFactory.getLogger(ChatModelPort.class);

    @Resource
    private OllamaGateway ollamaGateway;

    @Override
    public String chat(List<ChatMessageEntity> messages) {
        return chat(messages, ollamaGateway.getDefaultModel());
    }

    @Override
    public String chat(List<ChatMessageEntity> messages, String model) {
        ChatModelResult result = chatDetail(messages, model);
        return result.getContent();
    }

    @Override
    public ChatModelResult chatDetail(List<ChatMessageEntity> messages, String model) {
        log.info("调用 Ollama 模型：{}，消息数：{}", model, messages.size());
        return ollamaGateway.chat(messages, model);
    }
}
