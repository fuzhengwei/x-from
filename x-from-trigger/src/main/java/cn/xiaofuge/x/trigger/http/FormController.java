package cn.xiaofuge.x.trigger.http;

import cn.xiaofuge.x.api.dto.CreateFormRequest;
import cn.xiaofuge.x.api.dto.SubmitFormRequest;
import cn.xiaofuge.x.api.response.Response;
import cn.xiaofuge.x.domain.form.model.entity.FormEntity;
import cn.xiaofuge.x.domain.form.model.entity.FormSubmissionEntity;
import cn.xiaofuge.x.domain.form.service.IFormService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单Controller
 */
@RestController
@RequestMapping("/api/form")
@CrossOrigin(origins = "*")
public class FormController {

    private static final Logger log = LoggerFactory.getLogger(FormController.class);

    @Autowired
    private IFormService formService;

    /**
     * 创建表单
     */
    @PostMapping("/create")
    public Response<Map<String, Object>> createForm(@RequestBody CreateFormRequest request) {
        try {
            FormEntity formEntity = FormEntity.builder()
                    .formName(request.getFormName())
                    .formDesc(request.getFormDesc())
                    .formConfig(request.getFormConfig())
                    .creator(request.getCreator() != null ? request.getCreator() : "admin")
                    .build();

            Long formId = formService.createForm(formEntity);

            Map<String, Object> data = new HashMap<>();
            data.put("formId", formId);
            data.put("formCode", formEntity.getFormCode());

            return Response.success(data);
        } catch (Exception e) {
            log.error("创建表单失败", e);
            return Response.error("创建表单失败：" + e.getMessage());
        }
    }

    /**
     * 获取表单详情
     */
    @GetMapping("/get/{formCode}")
    public Response<FormEntity> getForm(@PathVariable String formCode) {
        try {
            FormEntity formEntity = formService.getFormByCode(formCode);
            if (formEntity == null) {
                return Response.error("表单不存在");
            }
            return Response.success(formEntity);
        } catch (Exception e) {
            log.error("查询表单失败", e);
            return Response.error("查询表单失败：" + e.getMessage());
        }
    }

    /**
     * 获取表单列表
     */
    @GetMapping("/list")
    public Response<List<FormEntity>> getFormList() {
        try {
            List<FormEntity> formList = formService.getFormList();
            return Response.success(formList);
        } catch (Exception e) {
            log.error("查询表单列表失败", e);
            return Response.error("查询表单列表失败：" + e.getMessage());
        }
    }

    /**
     * 提交表单
     */
    @PostMapping("/submit")
    public Response<Map<String, Object>> submitForm(@RequestBody SubmitFormRequest request, HttpServletRequest httpRequest) {
        try {
            // 获取表单ID
            FormEntity formEntity = formService.getFormByCode(request.getFormCode());
            if (formEntity == null) {
                return Response.error("表单不存在");
            }

            FormSubmissionEntity submissionEntity = FormSubmissionEntity.builder()
                    .formId(formEntity.getId())
                    .formCode(request.getFormCode())
                    .submissionData(request.getSubmissionData())
                    .submitter(request.getSubmitter())
                    .submitterIp(getClientIp(httpRequest))
                    .build();

            Long submissionId = formService.submitForm(submissionEntity);

            Map<String, Object> data = new HashMap<>();
            data.put("submissionId", submissionId);

            return Response.success(data);
        } catch (Exception e) {
            log.error("提交表单失败", e);
            return Response.error("提交表单失败：" + e.getMessage());
        }
    }

    /**
     * 获取表单提交记录
     */
    @GetMapping("/submissions/{formId}")
    public Response<List<FormSubmissionEntity>> getFormSubmissions(@PathVariable Long formId) {
        try {
            List<FormSubmissionEntity> submissions = formService.getFormSubmissions(formId);
            return Response.success(submissions);
        } catch (Exception e) {
            log.error("查询表单提交记录失败", e);
            return Response.error("查询表单提交记录失败：" + e.getMessage());
        }
    }

    /**
     * 获取客户端IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
