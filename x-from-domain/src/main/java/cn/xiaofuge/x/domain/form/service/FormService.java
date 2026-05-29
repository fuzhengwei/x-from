package cn.xiaofuge.x.domain.form.service;

import cn.xiaofuge.x.domain.form.adapter.repository.IFormRepository;
import cn.xiaofuge.x.domain.form.model.entity.FormEntity;
import cn.xiaofuge.x.domain.form.model.entity.FormSubmissionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 表单服务实现
 */
@Service
public class FormService implements IFormService {

    private static final Logger log = LoggerFactory.getLogger(FormService.class);

    @Autowired
    private IFormRepository formRepository;

    @Override
    public Long createForm(FormEntity formEntity) {
        log.info("创建表单，表单名称：{}", formEntity.getFormName());
        // 生成表单编码
        if (formEntity.getFormCode() == null || formEntity.getFormCode().isEmpty()) {
            formEntity.setFormCode("form_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));
        }
        return formRepository.createForm(formEntity);
    }

    @Override
    public void updateForm(FormEntity formEntity) {
        log.info("更新表单，表单ID：{}", formEntity.getId());
        formRepository.updateForm(formEntity);
    }

    @Override
    public FormEntity getFormById(Long id) {
        log.info("查询表单，表单ID：{}", id);
        return formRepository.getFormById(id);
    }

    @Override
    public FormEntity getFormByCode(String formCode) {
        log.info("查询表单，表单编码：{}", formCode);
        return formRepository.getFormByCode(formCode);
    }

    @Override
    public List<FormEntity> getFormList() {
        log.info("查询表单列表");
        return formRepository.getFormList();
    }

    @Override
    public Long submitForm(FormSubmissionEntity submissionEntity) {
        log.info("提交表单数据，表单编码：{}", submissionEntity.getFormCode());
        return formRepository.submitForm(submissionEntity);
    }

    @Override
    public List<FormSubmissionEntity> getFormSubmissions(Long formId) {
        log.info("查询表单提交记录，表单ID：{}", formId);
        return formRepository.getFormSubmissions(formId);
    }
}
