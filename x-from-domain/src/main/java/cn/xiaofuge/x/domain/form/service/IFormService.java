package cn.xiaofuge.x.domain.form.service;

import cn.xiaofuge.x.domain.form.model.entity.FormEntity;
import cn.xiaofuge.x.domain.form.model.entity.FormSubmissionEntity;

import java.util.List;

/**
 * 表单服务接口
 */
public interface IFormService {

    /**
     * 创建表单
     */
    Long createForm(FormEntity formEntity);

    /**
     * 更新表单
     */
    void updateForm(FormEntity formEntity);

    /**
     * 根据ID查询表单
     */
    FormEntity getFormById(Long id);

    /**
     * 根据编码查询表单
     */
    FormEntity getFormByCode(String formCode);

    /**
     * 查询表单列表
     */
    List<FormEntity> getFormList();

    /**
     * 提交表单数据
     */
    Long submitForm(FormSubmissionEntity submissionEntity);

    /**
     * 查询表单提交记录
     */
    List<FormSubmissionEntity> getFormSubmissions(Long formId);
}
