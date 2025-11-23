package com.example.patient.registry.infrastructure.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务操作类型
 *
 * @author harry
 */
@Getter
@AllArgsConstructor
public enum BusinessType {

    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 清空数据
     */
    CLEAN,

}

