package org.dam.common.page;

import lombok.Data;

/**
 * @Author dam
 * @create 2024/1/19 16:12
 */
@Data
public class PageRequest {

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页显示条数
     */
    private Long size = 10L;
}
