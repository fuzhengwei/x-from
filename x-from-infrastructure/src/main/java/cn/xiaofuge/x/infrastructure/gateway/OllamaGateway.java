package cn.xiaofuge.x.infrastructure.gateway;

import cn.xiaofuge.x.domain.chat.adapter.port.ChatModelResult;
import cn.xiaofuge.x.domain.chat.model.entity.ChatMessageEntity;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Ollama API 网关
 * <p>
 * 负责与 Ollama 服务进行 HTTP 通信，
 * 调用 /api/chat 接口完成大模型对话
 */
@Component
public class OllamaGateway {

    private static final Logger log = LoggerFactory.getLogger(OllamaGateway.class);

    @Value("${ollama.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${ollama.model:qwen2.5:0.5b}")
    private String defaultModel;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 调用 Ollama Chat API 进行对话
     *
     * @param messages 消息列表
     * @param model    模型名称
     * @return 对话结果
     */
    public ChatModelResult chat(List<ChatMessageEntity> messages, String model) {
        String url = baseUrl + "/api/chat";

        // 构建请求体
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("stream", false);

        JSONArray messagesArray = new JSONArray();
        for (ChatMessageEntity msg : messages) {
            JSONObject msgObj = new JSONObject();
            msgObj.put("role", msg.getRole());
            msgObj.put("content", msg.getContent());
            messagesArray.add(msgObj);
        }
        requestBody.put("messages", messagesArray);

        log.info("Ollama 请求 - model: {}, messages: {}", model, messagesArray.size());

        // 发起 HTTP 请求
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toJSONString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Ollama 调用失败，状态码：{}", response.getStatusCode());
            throw new RuntimeException("Ollama 调用失败：" + response.getStatusCode());
        }

        // 解析响应
        JSONObject responseBody = JSON.parseObject(response.getBody());
        ChatModelResult result = new ChatModelResult();
        result.setContent(responseBody.getString("content") != null
                ? responseBody.getString("content")
                : responseBody.getJSONObject("message").getString("content"));
        result.setModel(responseBody.getString("model"));
        result.setTotalDuration(responseBody.getLong("total_duration"));
        result.setEvalCount(responseBody.getInteger("eval_count"));
        result.setPromptEvalCount(responseBody.getInteger("prompt_eval_count"));

        log.info("Ollama 响应 - model: {}, evalCount: {}, duration: {}ms",
                result.getModel(), result.getEvalCount(),
                result.getTotalDuration() != null ? result.getTotalDuration() / 1_000_000 : 0);

        return result;
    }

    /**
     * 获取默认模型名称
     */
    public String getDefaultModel() {
        return defaultModel;
    }
}
