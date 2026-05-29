package cn.xiaofuge.x.infrastructure.adapter.repository;

import cn.xiaofuge.x.domain.form.adapter.repository.IFormRepository;
import cn.xiaofuge.x.domain.form.model.entity.FormEntity;
import cn.xiaofuge.x.domain.form.model.entity.FormSubmissionEntity;
import cn.xiaofuge.x.domain.form.model.valobj.FormStatus;
import cn.xiaofuge.x.infrastructure.dao.IFormDao;
import cn.xiaofuge.x.infrastructure.dao.po.FormPO;
import cn.xiaofuge.x.infrastructure.dao.po.FormSubmissionPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单仓储实现
 */
@Repository
public class FormRepository implements IFormRepository {

    @Autowired
    private IFormDao formDao;

    @Override
    public Long createForm(FormEntity formEntity) {
        FormPO formPO = convertToPO(formEntity);
        formDao.insertForm(formPO);
        return formPO.getId();
    }

    @Override
    public void updateForm(FormEntity formEntity) {
        FormPO formPO = convertToPO(formEntity);
        formDao.updateForm(formPO);
    }

    @Override
    public FormEntity getFormById(Long id) {
        FormPO formPO = formDao.getFormById(id);
        return convertToEntity(formPO);
    }

    @Override
    public FormEntity getFormByCode(String formCode) {
        FormPO formPO = formDao.getFormByCode(formCode);
        return convertToEntity(formPO);
    }

    @Override
    public List<FormEntity> getFormList() {
        List<FormPO> formPOList = formDao.getFormList();
        List<FormEntity> formEntityList = new ArrayList<>();
        for (FormPO formPO : formPOList) {
            formEntityList.add(convertToEntity(formPO));
        }
        return formEntityList;
    }

    @Override
    public Long submitForm(FormSubmissionEntity submissionEntity) {
        FormSubmissionPO submissionPO = convertToSubmissionPO(submissionEntity);
        formDao.insertFormSubmission(submissionPO);
        return submissionPO.getId();
    }

    @Override
    public List<FormSubmissionEntity> getFormSubmissions(Long formId) {
        List<FormSubmissionPO> submissionPOList = formDao.getFormSubmissions(formId);
        List<FormSubmissionEntity> submissionEntityList = new ArrayList<>();
        for (FormSubmissionPO submissionPO : submissionPOList) {
            submissionEntityList.add(convertToSubmissionEntity(submissionPO));
        }
        return submissionEntityList;
    }

    private FormPO convertToPO(FormEntity entity) {
        if (entity == null) {
            return null;
        }
        return FormPO.builder()
                .id(entity.getId())
                .formCode(entity.getFormCode())
                .formName(entity.getFormName())
                .formDesc(entity.getFormDesc())
                .formConfig(entity.getFormConfig())
                .status(entity.getStatus() != null ? entity.getStatus().getCode() : null)
                .creator(entity.getCreator())
                .createTime(entity.getCreateTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

    private FormEntity convertToEntity(FormPO po) {
        if (po == null) {
            return null;
        }
        return FormEntity.builder()
                .id(po.getId())
                .formCode(po.getFormCode())
                .formName(po.getFormName())
                .formDesc(po.getFormDesc())
                .formConfig(po.getFormConfig())
                .status(FormStatus.getByCode(po.getStatus()))
                .creator(po.getCreator())
                .createTime(po.getCreateTime())
                .updateTime(po.getUpdateTime())
                .build();
    }

    private FormSubmissionPO convertToSubmissionPO(FormSubmissionEntity entity) {
        if (entity == null) {
            return null;
        }
        return FormSubmissionPO.builder()
                .id(entity.getId())
                .formId(entity.getFormId())
                .formCode(entity.getFormCode())
                .submissionData(entity.getSubmissionData())
                .submitter(entity.getSubmitter())
                .submitterIp(entity.getSubmitterIp())
                .createTime(entity.getCreateTime())
                .build();
    }

    private FormSubmissionEntity convertToSubmissionEntity(FormSubmissionPO po) {
        if (po == null) {
            return null;
        }
        return FormSubmissionEntity.builder()
                .id(po.getId())
                .formId(po.getFormId())
                .formCode(po.getFormCode())
                .submissionData(po.getSubmissionData())
                .submitter(po.getSubmitter())
                .submitterIp(po.getSubmitterIp())
                .createTime(po.getCreateTime())
                .build();
    }
}
