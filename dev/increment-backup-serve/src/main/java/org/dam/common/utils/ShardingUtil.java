package org.dam.common.utils;

/**
 * @Author dam
 * @create 2023/12/8 21:38
 */
public class ShardingUtil {

    /**
     * 表名后面添加 _%d
     */
    public static final String CREATE_SQL =
            "CREATE TABLE `file_message_%d` (\n" +
            "   id bigint NOT NULL AUTO_INCREMENT,\n" +
                    "  file_name varchar(300) COMMENT '文件名称',\n" +
                    "  backup_source_id bigint COMMENT '数据源id',\n" +
                    "  source_file_path varchar(4096) COMMENT '源文件路径',\n" +
                    "  target_file_path varchar(4096) COMMENT '备份目标目录根目录',\n" +
                    "  file_suffix varchar(30) COMMENT '文件后缀',\n" +
                    "  file_length bigint COMMENT '文件大小 byte',\n" +
                    "  file_length_after_compress bigint COMMENT '压缩后文件大小 byte',\n" +
                    "  father_id bigint COMMENT '父文件夹id',\n" +
                    "  is_compress tinyint COMMENT '是否压缩',\n" +
                    "  file_type tinyint COMMENT '文件类型 0：目录 1：文件',\n" +
                    "  is_contain_file tinyint COMMENT '是否包含子文件 0：不包含 1：包含',\n" +
                    "  create_time datetime,\n" +
                    "  update_time datetime,\n" +
                    "  PRIMARY KEY (id)\n" +
                    ");";

    public static final String REMOVE_SQL =
            "DROP TABLE IF EXISTS `backup_file_%d`;";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((CREATE_SQL) + "%n", i);
        }

//        for (int i = 0; i < 16; i++) {
//            System.out.printf((REMOVE_SQL) + "%n", i);
//        }
    }
}