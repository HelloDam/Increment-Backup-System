package org.dam.common.errorcode;

/**
 * 平台错误码
 * @Author dam
 * @create 2024/1/19 11:13
 */
public interface IErrorCode {

    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}