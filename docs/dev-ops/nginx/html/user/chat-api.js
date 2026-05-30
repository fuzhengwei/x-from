/**
 * 智能客服 API 接口封装
 */
var ChatApi = (function () {

    var API_BASE = (function () {
        var port = window.location.port;
        if (port === '80' || port === '8080' || port === '') {
            return '/api/chat';
        }
        return 'http://140.143.183.225:8091/api/chat';
    })();

    /**
     * 智能客服对话（支持多轮上下文）
     */
    function ask(message, sessionId, history) {
        var body = { message: message };
        if (sessionId) body.sessionId = sessionId;
        if (history && history.length > 0) body.history = history;

        return fetch(API_BASE + '/ask', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        })
        .then(function (res) { return res.json(); })
        .then(function (result) {
            if (result.code !== '0000') {
                throw new Error(result.info || '请求失败');
            }
            return result.data;
        });
    }

    return { ask: ask };
})();
