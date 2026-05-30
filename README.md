# x-from - 在线表单管理系统

> 一个基于 DDD 六边形架构的在线表单管理系统，支持表单可视化设计、动态渲染、数据收集，并内置 AI 智能客服。

## 📖 项目简介

x-from 是一个完整的在线表单解决方案，提供从**表单创建 → 分享 → 填写 → 数据查询**的全流程能力，并集成基于 Ollama 大模型的智能客服，帮助用户快速上手平台。

### 核心功能

| 功能 | 说明 |
|------|------|
| **表单可视化设计** | 支持 9 种字段类型（单行文本、多行文本、数字、邮箱、手机号、下拉选择、单选、多选、日期），拖拽式配置 |
| **动态表单渲染** | 根据管理端设计的字段配置，用户端自动生成填写界面 |
| **表单分享** | 一键生成分享链接，用户无需登录即可填写 |
| **数据查询** | 管理端查看表单提交数据详情，支持 JSON 预览 |
| **智能客服** | 基于 Ollama + qwen2.5:0.5b 的 AI 客服「小 X」，了解平台能力，为用户答疑解惑 |
| **多端入口** | 管理端（/admin/）、用户端（/user/）、独立客服页（/user/chat.html） |

---

## 🏗️ 技术架构

```
┌─────────────────────────────────────────────────────────────┐
│                     Trigger Layer (触发层)                    │
│          FormController / ChatController                     │
└─────────────────────────┬───────────────────────────────────┘
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                       API Layer (接口层)                      │
│            DTO / Request / Response                          │
└─────────────────────────┬───────────────────────────────────┘
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer (领域层)                    │
│   form: Entity / Service / Repository                       │
│   chat:  Entity / Service / Port (IChatModelPort)           │
└─────────────────────────┬───────────────────────────────────┘
                          ▲ 依赖倒置
┌─────────────────────────────────────────────────────────────┐
│                Infrastructure Layer (基础设施层)               │
│   FormRepository / ChatModelPort / OllamaGateway / DAO      │
└─────────────────────────────────────────────────────────────┘
```

**依赖规则**：`Trigger → API → Domain ← Infrastructure`

### 工程结构

```
x-from/
├── x-from-api/              # 接口层：DTO、Request、Response
├── x-from-app/              # 启动层：Spring Boot 主启动类、配置文件
├── x-from-case/             # 编排层：跨领域业务编排（预留）
├── x-from-domain/           # 领域层：核心业务逻辑
│   └── form/                # 表单领域
│       ├── adapter/repository/  # 仓储接口
│       ├── model/               # 实体、值对象
│       └── service/             # 领域服务
│   └── chat/                # 智能客服领域
│       ├── adapter/port/        # 端口接口（IChatModelPort）
│       ├── model/               # 实体、值对象
│       └── service/             # 领域服务
├── x-from-infrastructure/   # 基础设施层：技术实现
│   ├── adapter/repository/  # 仓储实现
│   ├── adapter/port/        # 端口实现（ChatModelPort）
│   ├── gateway/             # 外部网关（OllamaGateway）
│   ├── dao/                 # MyBatis DAO + PO
│   └── config/              # 基础设施配置
├── x-from-trigger/          # 触发层：HTTP Controller
├── x-from-types/            # 通用类型：异常、枚举、常量
└── docs/dev-ops/            # 部署配置
    ├── nginx/               # Nginx 配置 + 前端页面
    ├── mysql/               # MySQL 配置 + SQL
    ├── redis/               # Redis 配置
    └── docker-compose-*.yml # Docker Compose 编排
```

### 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言 | Java | 17 |
| 框架 | Spring Boot | 3.4.3 |
| ORM | MyBatis | 3.0.4 |
| 数据库 | MySQL | 8.0.32 |
| 缓存 | Redis | 6.2 |
| 容器 | Docker + Docker Compose | - |
| 反向代理 | Nginx | 1.25 |
| AI 模型 | Ollama + qwen2.5:0.5b | 0.5.10 |

---

## 🚀 部署指南

### 环境要求

- JDK 17+
- Maven 3.8+
- Docker & Docker Compose
- 服务器推荐：2 核 4G 以上

### 方式一：Docker Compose 一键部署（推荐）

#### 1. 克隆项目

```bash
git clone git@github.com:fuzhengwei/x-from.git
cd x-from
```

#### 2. 打包构建

```bash
mvn clean package -Dmaven.test.skip=true
```

#### 3. 一键启动（含 MySQL + Redis + 应用 + Nginx）

```bash
cd docs/dev-ops
docker-compose -f docker-compose-x-from.yml up -d
```

#### 4. 验证

| 服务 | 地址 | 说明 |
|------|------|------|
| 管理端 | http://你的IP:8080/admin/ | 默认账号：admin / admin123 |
| 用户端 | http://你的IP:8080/user/form.html?code=表单编码 | 填写表单 |
| 智能客服 | http://你的IP:8080/user/chat.html | AI 在线客服 |
| 后端 API | http://你的IP:8091 | Spring Boot 服务 |

### 方式二：分步部署

#### 1. 启动基础环境（MySQL + Redis）

```bash
cd docs/dev-ops
docker-compose -f docker-compose-environment-aliyun.yml up -d
```

#### 2. 初始化数据库

等待 MySQL 启动完成后，SQL 会自动通过 `docker-entrypoint-initdb.d` 执行。

如需手动初始化：

```bash
docker exec -i mysql mysql -uroot -p123456 < mysql/sql/x-from.sql
```

#### 3. 修改配置并启动应用

编辑 `x-from-app/src/main/resources/application-dev.yml`，修改数据库和 Redis 地址：

```yaml
spring:
  datasource:
    url: jdbc:mysql://你的IP:13306/x_from?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 123456

ollama:
  base-url: http://你的IP:11434    # Ollama 服务地址
  model: qwen2.5:0.5b               # 模型名称
```

打包并启动：

```bash
cd x-from
mvn clean package -Dmaven.test.skip=true
java -jar x-from-app/target/x-from-app.jar --spring.profiles.active=dev
```

#### 4. 部署 Ollama（智能客服依赖）

```bash
# 拉取 Ollama 镜像
docker pull registry.cn-hangzhou.aliyuncs.com/xfg-studio/ollama:0.5.10

# 启动 Ollama
docker run -d --name ollama -p 11434:11434 \
  -v ollama_data:/root/.ollama \
  --restart always \
  registry.cn-hangzhou.aliyuncs.com/xfg-studio/ollama:0.5.10

# 拉取模型
docker exec ollama ollama pull qwen2.5:0.5b
```

#### 5. 部署 Nginx

```bash
# 将 docs/dev-ops/nginx 目录上传到服务器
# Nginx 配置已在 docker-compose-x-from.yml 中集成
# 或单独启动 Nginx 容器：

docker run -d --name x-from-nginx \
  -p 8080:80 \
  -v /path/to/nginx/html:/usr/share/nginx/html \
  -v /path/to/nginx/conf:/etc/nginx/conf.d \
  --network x-from-network \
  nginx:1.25.1-alpine
```

---

## 📖 使用指南

### 1. 登录管理端

访问 `http://你的IP:8080/admin/`，使用默认账号登录：

- 用户名：`admin`
- 密码：`admin123`

### 2. 创建表单

1. 点击左侧菜单 **「创建表单」**
2. 填写表单名称和描述
3. 点击 **「添加字段」** 设计表单字段：
   - 设置字段标识（key）、显示名称、字段类型
   - 支持 9 种字段类型：单行文本、多行文本、数字、邮箱、手机号、下拉选择、单选、多选、日期
   - 可设置必填、占位提示
4. 点击 **「创建表单」** 完成

### 3. 分享表单

1. 进入 **「表单列表」**，找到目标表单
2. 点击 **「分享」** 按钮，复制链接
3. 将链接发送给用户

### 4. 用户填写表单

用户通过分享链接访问表单页面，填写并提交。页面还提供浮动智能客服按钮，随时可以咨询。

### 5. 查看提交数据

1. 管理端点击 **「数据查询」**
2. 选择表单，查看提交数据详情

### 6. 使用智能客服

智能客服「小 X」了解 x-from 平台的所有功能，可以回答关于：

- 如何创建表单
- 支持哪些字段类型
- 如何分享表单
- 如何查看提交数据
- 登录账号等常见问题

**客服入口**：

| 入口 | 位置 | 形式 |
|------|------|------|
| 管理端菜单 | 左侧菜单「🤖 智能客服」 | 内嵌聊天页面 |
| 用户端浮动按钮 | 表单页右下角 💬 | 弹窗聊天 |
| 独立页面 | /user/chat.html | 全屏对话 |

---

## 📡 API 接口

### 表单接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/form/create` | 创建表单 |
| GET  | `/api/form/get/{formCode}` | 查询表单详情 |
| GET  | `/api/form/list` | 查询表单列表 |
| POST | `/api/form/submit` | 提交表单数据 |
| GET  | `/api/form/submissions/{formId}` | 查询提交记录 |

### 智能客服接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/chat/ask` | 智能客服对话（支持多轮上下文） |
| GET  | `/api/chat/ask?message=xxx` | 简单对话（方便测试） |

#### POST /api/chat/ask 请求示例

```json
{
  "sessionId": "可选，多轮对话会话ID",
  "message": "如何创建表单？",
  "history": [
    { "role": "user", "content": "你好" },
    { "role": "assistant", "content": "你好！有什么可以帮助你的吗？" }
  ]
}
```

#### 响应示例

```json
{
  "code": "0000",
  "info": "success",
  "data": {
    "sessionId": "662aa6fc91e046b7a8f17b0408ea14bc",
    "reply": "登录管理端，点击左侧菜单「创建表单」...",
    "model": "qwen2.5:0.5b",
    "durationMs": 3275,
    "evalCount": 62
  }
}
```

---

## 🔧 常见问题

### Q: MySQL 连接失败？
检查 MySQL 容器是否启动：`docker ps | grep mysql`，确认端口 13306 可访问。

### Q: 智能客服无法回复？
1. 确认 Ollama 容器已启动：`docker ps | grep ollama`
2. 确认模型已拉取：`docker exec ollama ollama list`
3. 测试 Ollama 是否正常：`curl http://你的IP:11434/`

### Q: 端口冲突？
修改 `docker-compose-x-from.yml` 中的端口映射。

### Q: 如何更换 AI 模型？
修改 `application-dev.yml` 中的 `ollama.model` 配置，例如更换为 `qwen2.5:7b`：
```yaml
ollama:
  base-url: http://你的IP:11434
  model: qwen2.5:7b
```

---

## 📚 参考资料

- Docker 使用文档：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html)
- DDD 教程：
  - [DDD 概念理论](https://bugstack.cn/md/road-map/ddd-guide-01.html)
  - [DDD 建模方法](https://bugstack.cn/md/road-map/ddd-guide-02.html)
  - [DDD 工程模型](https://bugstack.cn/md/road-map/ddd-guide-03.html)
  - [DDD 架构设计](https://bugstack.cn/md/road-map/ddd.html)
  - [DDD 建模案例](https://bugstack.cn/md/road-map/ddd-model.html)
