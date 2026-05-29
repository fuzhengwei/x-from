/**
 * x-from API 接口封装
 * 统一管理所有后端接口调用，方便维护和对接
 */

var FormApi = (function () {

    // 智能检测 API 基础路径：
    // - 通过 Nginx 代理访问（端口 80/8080）→ 用相对路径 /api/form
    // - 其他场景（IDE 直开、文件协议等）→ 用绝对路径指向后端
    var API_BASE = (function () {
        var port = window.location.port;
        var protocol = window.location.protocol;
        // Nginx 代理场景：80 或 8080 端口，走相对路径
        if (port === '80' || port === '8080' || port === '') {
            return '/api/form';
        }
        // 其他场景（IDE 内置服务器、file:// 等），直连后端
        return 'http://localhost:8091/api/form';
    })();

    /**
     * 通用请求方法
     */
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
                if (result.code !== '0000') {
                    var error = new Error(result.info || '请求失败');
                    error.code = result.code;
                    throw error;
                }
                return result.data;
            });
    }

    // ========== 登录相关（模拟） ==========

    function login(username, password) {
        return new Promise(function (resolve, reject) {
            setTimeout(function () {
                if (username === 'admin' && password === 'admin123') {
                    var user = { id: 1, username: 'admin', nickname: '管理员', token: 'mock_token_' + Date.now() };
                    localStorage.setItem('x_from_user', JSON.stringify(user));
                    resolve(user);
                } else {
                    reject(new Error('用户名或密码错误'));
                }
            }, 500);
        });
    }

    function getCurrentUser() {
        try { var s = localStorage.getItem('x_from_user'); return s ? JSON.parse(s) : null; }
        catch (e) { return null; }
    }

    function logout() { localStorage.removeItem('x_from_user'); }

    function isLoggedIn() { return getCurrentUser() !== null; }

    // ========== 表单管理 ==========

    function getFormList() { return request('/list'); }

    function getForm(formCode) { return request('/get/' + formCode); }

    function createForm(data) { return request('/create', { method: 'POST', body: data }); }

    // ========== 表单提交 ==========

    function submitForm(data) { return request('/submit', { method: 'POST', body: data }); }

    function getSubmissions(formId) { return request('/submissions/' + formId); }

    // ========== 公开接口 ==========

    return {
        login: login, getCurrentUser: getCurrentUser, logout: logout, isLoggedIn: isLoggedIn,
        getFormList: getFormList, getForm: getForm, createForm: createForm,
        submitForm: submitForm, getSubmissions: getSubmissions
    };

})();
