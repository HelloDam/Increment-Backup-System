package org.dam.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.dam.common.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author dam
 * @create 2024/1/19 17:43
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @Override
    public Number nextId(Object entity) {

        return snowFlakeUtil.nextId();
    }

}
