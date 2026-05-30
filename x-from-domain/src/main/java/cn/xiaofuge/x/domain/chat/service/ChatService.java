package cn.xiaofuge.x.domain.chat.service;

import cn.xiaofuge.x.domain.chat.adapter.port.ChatModelResult;
import cn.xiaofuge.x.domain.chat.adapter.port.IChatModelPort;
import cn.xiaofuge.x.domain.chat.model.entity.ChatMessageEntity;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能客服服务实现
 * <p>
 * 职责：组装系统提示词、拼接对话上下文、调用大模型端口
 */
@Service
public class ChatService implements IChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    /**
     * 智能客服系统提示词 —— 包含 x-form 表单服务能力知识
     */
    private static final String SYSTEM_PROMPT = "你是一个友好、专业的智能客服助手，你的名字叫「小 X」。你服务于 x-from 表单管理平台，你的职责是：\n"
            + "\n"
            + "1. 耐心回答用户关于 x-from 平台的问题，提供准确、有用的信息\n"
            + "2. 如果不确定答案，诚实告知用户，不要编造信息\n"
            + "3. 回答尽量简洁明了，避免冗长\n"
            + "4. 使用礼貌、亲切的语气与用户交流\n"
            + "\n"
            + "## x-from 平台能力介绍\n"
            + "\n"
            + "x-from 是一个在线表单管理系统，主要提供以下能力：\n"
            + "\n"
            + "### 管理端功能（/admin/）\n"
            + "- **工作台**：查看表单总数、启用数量、提交统计等概览信息\n"
            + "- **创建表单**：支持可视化设计表单字段，包括单行文本、多行文本、数字、邮箱、手机号、下拉选择、单选、多选、日期等 9 种字段类型，可设置字段必填、占位提示等属性\n"
            + "- **表单列表**：查看所有已创建的表单，支持查看详情、提交数据、分享表单链接\n"
            + "- **数据查询**：选择表单后查看用户提交的数据详情\n"
            + "\n"
            + "### 用户端功能（/user/）\n"
            + "- **填写表单**：用户通过分享链接访问表单页面，填写并提交表单数据\n"
            + "- 表单支持动态渲染，根据管理端设计的字段配置自动生成表单界面\n"
            + "\n"
            + "### 常见问题指引\n"
            + "- 如何创建表单？→ 登录管理端，点击左侧菜单「创建表单」，填写表单名称后添加字段，点击创建即可\n"
            + "- 如何分享表单？→ 在表单列表中点击「分享」按钮，复制链接发送给用户\n"
            + "- 支持哪些字段类型？→ 单行文本、多行文本、数字、邮箱、手机号、下拉选择、单选、多选、日期\n"
            + "- 默认登录账号？→ 用户名 admin，密码 admin123\n"
            + "\n"
            + "请用中文回复用户的问题。";

    @Value("${ollama.model:qwen2.5:0.5b}")
    private String defaultModel;

    @Resource
    private IChatModelPort chatModelPort;

    @Override
    public String chat(String message) {
        log.info("智能客服单轮对话，用户消息：{}", message);
        List<ChatMessageEntity> messages = buildMessages(message, null);
        return chatModelPort.chat(messages, defaultModel);
    }

    @Override
    public ChatModelResult chat(String sessionId, String message, List<ChatMessageEntity> history) {
        log.info("智能客服多轮对话，sessionId：{}，用户消息：{}", sessionId, message);
        List<ChatMessageEntity> messages = buildMessages(message, history);
        return chatModelPort.chatDetail(messages, defaultModel);
    }

    /**
     * 构建完整的对话消息列表
     * <p>
     * 结构：[系统提示词] + [历史对话] + [当前用户消息]
     */
    private List<ChatMessageEntity> buildMessages(String message, List<ChatMessageEntity> history) {
        List<ChatMessageEntity> messages = new ArrayList<>();

        // 1. 添加系统提示词
        messages.add(new ChatMessageEntity("system", SYSTEM_PROMPT));

        // 2. 添加历史对话（如有）
        if (history != null && !history.isEmpty()) {
            // 限制历史消息长度，避免 token 超限
            int maxHistorySize = 20;
            int start = Math.max(0, history.size() - maxHistorySize);
            for (int i = start; i < history.size(); i++) {
                messages.add(history.get(i));
            }
        }

        // 3. 添加当前用户消息
        messages.add(new ChatMessageEntity("user", message));

        return messages;
    }
}
