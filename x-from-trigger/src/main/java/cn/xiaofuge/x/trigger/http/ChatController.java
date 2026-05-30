package cn.xiaofuge.x.trigger.http;

import cn.xiaofuge.x.api.dto.ChatRequestDTO;
import cn.xiaofuge.x.api.dto.ChatResponseDTO;
import cn.xiaofuge.x.api.response.Response;
import cn.xiaofuge.x.domain.chat.adapter.port.ChatModelResult;
import cn.xiaofuge.x.domain.chat.model.entity.ChatMessageEntity;
import cn.xiaofuge.x.domain.chat.service.IChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 智能客服 Controller
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private IChatService chatService;

    /**
     * 智能客服对话接口
     * <p>
     * 支持单轮和多轮对话：
     * - 单轮：只传 message 即可
     * - 多轮：传入 sessionId 和 history 保持上下文
     */
    @PostMapping("/ask")
    public Response<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {
        try {
            // 参数校验
            if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
                return Response.error("消息内容不能为空");
            }

            // 生成或复用会话ID
            String sessionId = request.getSessionId();
            if (sessionId == null || sessionId.trim().isEmpty()) {
                sessionId = UUID.randomUUID().toString().replace("-", "");
            }

            // 转换历史消息
            List<ChatMessageEntity> history = convertHistory(request.getHistory());

            // 调用领域服务
            ChatModelResult result = chatService.chat(sessionId, request.getMessage(), history);

            // 构建响应
            ChatResponseDTO responseDTO = new ChatResponseDTO();
            responseDTO.setSessionId(sessionId);
            responseDTO.setReply(result.getContent());
            responseDTO.setModel(result.getModel());
            responseDTO.setDurationMs(result.getTotalDuration() != null ? result.getTotalDuration() / 1_000_000 : null);
            responseDTO.setEvalCount(result.getEvalCount());

            return Response.success(responseDTO);
        } catch (Exception e) {
            log.error("智能客服对话异常", e);
            return Response.error("智能客服暂时无法回复，请稍后再试：" + e.getMessage());
        }
    }

    /**
     * 简单对话接口（GET，方便测试）
     */
    @GetMapping("/ask")
    public Response<ChatResponseDTO> simpleChat(@RequestParam String message) {
        try {
            if (message == null || message.trim().isEmpty()) {
                return Response.error("消息内容不能为空");
            }

            String reply = chatService.chat(message);

            ChatResponseDTO responseDTO = new ChatResponseDTO();
            responseDTO.setSessionId(UUID.randomUUID().toString().replace("-", ""));
            responseDTO.setReply(reply);

            return Response.success(responseDTO);
        } catch (Exception e) {
            log.error("智能客服对话异常", e);
            return Response.error("智能客服暂时无法回复，请稍后再试：" + e.getMessage());
        }
    }

    /**
     * 转换历史消息 DTO → Entity
     */
    private List<ChatMessageEntity> convertHistory(List<ChatRequestDTO.ChatMessageDTO> historyDTOs) {
        if (historyDTOs == null || historyDTOs.isEmpty()) {
            return null;
        }

        List<ChatMessageEntity> history = new ArrayList<>();
        for (ChatRequestDTO.ChatMessageDTO dto : historyDTOs) {
            history.add(new ChatMessageEntity(dto.getRole(), dto.getContent()));
        }
        return history;
    }
}
