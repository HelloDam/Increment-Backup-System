package org.dam.entity.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.dam.common.page.PageRequest;

import java.io.Serializable;

/**
 * @Author dam
 * @create 2024/1/20 23:10
 */
@Data
public class BackupSourceRequest extends PageRequest implements Serializable {

    /**
     * 根目录
     */
    private String rootPath;

    /**
     * 备份名称，可以总结一下是备份什么的
     */
    private String backupName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
