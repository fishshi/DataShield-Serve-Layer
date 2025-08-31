package com.datashield.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型枚举类
 */
@Getter
@AllArgsConstructor
public enum DatabaseTypeEnum {
    MYSQL(1);

    @EnumValue
    private final int code;

    public static DatabaseTypeEnum getDatabaseType(int code) {
        for (DatabaseTypeEnum databaseType : DatabaseTypeEnum.values()) {
            if (databaseType.code == code) {
                return databaseType;
            }
        }
        return null;
    }
}
