package io.meshware.rule.core.enums;

import java.util.Objects;

/**
 * Filter Enum
 *
 * @author Zhiguo.Chen
 */
public enum FilterEnum {

    VERSION("version", "版本过滤器", String.class),
    OTHER("other", "其他", null);

    private String code;
    private String name;
    private Class clazz;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * Filter Enum Constructor
     *
     * @param code
     * @param name
     */
    FilterEnum(String code, String name, Class clazz) {
        this.code = code;
        this.name = name;
        this.clazz = clazz;
    }

    public static FilterEnum getByCode(String code) {
        if (Objects.isNull(code)) {
            return OTHER;
        }
        for (FilterEnum filterEnum : values()) {
            if (filterEnum.getCode().equals(code)) {
                return filterEnum;
            }
        }
        return OTHER;
    }
}
