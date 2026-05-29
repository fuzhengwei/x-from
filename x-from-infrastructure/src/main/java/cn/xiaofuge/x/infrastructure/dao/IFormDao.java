package cn.xiaofuge.x.infrastructure.dao;

import cn.xiaofuge.x.infrastructure.dao.po.FormPO;
import cn.xiaofuge.x.infrastructure.dao.po.FormSubmissionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表单DAO
 */
@Mapper
public interface IFormDao {

    /**
     * 插入表单
     */
    void insertForm(FormPO formPO);

    /**
     * 更新表单
     */
    void updateForm(FormPO formPO);

    /**
     * 根据ID查询表单
     */
    FormPO getFormById(@Param("id") Long id);

    /**
     * 根据编码查询表单
     */
    FormPO getFormByCode(@Param("formCode") String formCode);

    /**
     * 查询表单列表
     */
    List<FormPO> getFormList();

    /**
     * 插入表单提交记录
     */
    void insertFormSubmission(FormSubmissionPO submissionPO);

    /**
     * 查询表单提交记录
     */
    List<FormSubmissionPO> getFormSubmissions(@Param("formId") Long formId);
}
