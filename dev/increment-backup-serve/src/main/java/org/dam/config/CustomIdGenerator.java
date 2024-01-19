package org.dam.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.dam.common.utils.SnowflakeIdUtil;

/**
 * @Author dam
 * @create 2024/1/19 17:43
 */
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return SnowflakeIdUtil.nextId();
    }
}
