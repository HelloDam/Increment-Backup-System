package org.dam.common.constant;

/**
 * @Author dam
 * @create 2024/2/2 22:28
 */
public enum SystemParamEnum {
    /**
     * 忽略文件名称
     */
    IGNORE_FILE_NAME("ignore_file_name","忽略文件名称"),
    /**
     * 忽略文件名称
     */
    IGNORE_DIRECTORY_NAME("ignore_directory_name","忽略文件夹名称"),
    ;

    private String paramName;
    private String detail;

    SystemParamEnum(String paramName, String detail) {
        this.paramName = paramName;
        this.detail = detail;
    }

    public String getParamName() {
        return paramName;
    }

    public String getDetail() {
        return detail;
    }
}
