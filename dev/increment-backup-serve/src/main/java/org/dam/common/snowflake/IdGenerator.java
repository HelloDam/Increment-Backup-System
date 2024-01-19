package org.dam.common.snowflake;

/**
 * @Author dam
 * @create 2024/1/19 17:36
 */
public interface IdGenerator {

    /**
     * 下一个 ID
     */
    default long nextId() {
        return 0L;
    }

    /**
     * 下一个 ID 字符串
     */
    default String nextIdStr() {
        return "";
    }
}
