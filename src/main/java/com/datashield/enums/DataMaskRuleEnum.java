package com.datashield.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据脱敏规则枚举类
 */
@Getter
@AllArgsConstructor
public enum DataMaskRuleEnum {
    ;

    private final int code;

    /**
     * 根据 code 获取对应的数据脱敏规则枚举类对象
     *
     * @param code 数据脱敏规则代码
     *
     * @return 对应的数据脱敏规则枚举类对象, 如果不存在则返回 null
     */
    public static DataMaskRuleEnum getDataMaskRule(int code) {
        for (DataMaskRuleEnum dataMaskRuleEnum : DataMaskRuleEnum.values()) {
            if (dataMaskRuleEnum.getCode() == code) {
                return dataMaskRuleEnum;
            }
        }
        return null;
    }
}
