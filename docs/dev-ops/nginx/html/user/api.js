/**
 * x-from 用户端 API 接口封装
 */
var UserFormApi = (function () {

    // 智能检测 API 基础路径（与管理端一致）
    var API_BASE = (function () {
        var port = window.location.port;
        if (port === '80' || port === '8080' || port === '') {
            return '/api/form';
        }
        return 'http://localhost:8091/api/form';
    })();

    function request(url, options) {
        options = options || {};
        var defaults = { headers: { 'Content-Type': 'application/json' } };
        var config = {};
        for (var key in defaults) { config[key] = defaults[key]; }
        for (var key in options) { config[key] = options[key]; }
        if (config.body && typeof config.body === 'object') {
            config.body = JSON.stringify(config.body);
        }
        return fetch(API_BASE + url, config)
            .then(function (res) { return res.json(); })
            .then(function (result) {
                if (result.code !== '0000') { throw new Error(result.info || '请求失败'); }
                return result.data;
            });
    }

    function getForm(formCode) { return request('/get/' + formCode); }

    function submitForm(data) { return request('/submit', { method: 'POST', body: data }); }

    return { getForm: getForm, submitForm: submitForm };

})();
