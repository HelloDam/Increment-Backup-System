package org.dam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.dam.entity.base.BaseEntity;

/**
 * 
 * @TableName sys_param
 */
@TableName(value ="sys_param")
@Data
public class SysParam extends BaseEntity implements Serializable {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数值
     */
    private String value;

    /**
     * 参数描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}