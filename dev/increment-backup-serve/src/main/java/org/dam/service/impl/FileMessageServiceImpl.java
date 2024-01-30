package org.dam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dam.entity.FileMessage;
import org.dam.service.FileMessageService;
import org.dam.mapper.FileMessageMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dam
 * @description 针对表【file_message】的数据库操作Service实现
 * @createDate 2024-01-28 21:38:07
 */
@Service
public class FileMessageServiceImpl extends ServiceImpl<FileMessageMapper, FileMessage>
        implements FileMessageService {

    @Override
    public List<FileMessage> buildTree(Long sourceId) {
        List<FileMessage> fileMessageList = baseMapper.selectList(new QueryWrapper<FileMessage>().eq("backup_source_id", sourceId));

        // 找出所有fatherId是0的
        List<FileMessage> rootList = fileMessageList.stream().filter(fileMessage -> fileMessage.getFatherId() == 0).collect(Collectors.toList());

        for (FileMessage root : rootList) {
            searchSon(root, fileMessageList);
        }

        return rootList;
    }

    /**
     * 递归删除一个文件夹下面的所有文件
     *
     * @param removeFileMessageIdList
     */
    @Override
    public void recursionRemoveFileMessage(List<Long> removeFileMessageIdList) {
        // 待删除ID集合中
        List<Long> totalRemoveFileMessageIdList = new ArrayList<>();
        // 递归方法：将文件的子文件id添加到待删除ID集合中
        searchFileMessageSons(removeFileMessageIdList, totalRemoveFileMessageIdList);
        // 批量删除
        baseMapper.deleteBatchIds(totalRemoveFileMessageIdList);
    }

    /**
     * 递归方法：将文件的子文件id添加到待删除ID集合中
     *
     * @param removeFileMessageIdList
     * @param totalRemoveFileMessageIdList
     */
    private void searchFileMessageSons(List<Long> removeFileMessageIdList, List<Long> totalRemoveFileMessageIdList) {
        for (Long fileMessageId : removeFileMessageIdList) {
            totalRemoveFileMessageIdList.add(fileMessageId);
            List<Long> fileIdList = baseMapper.selectSonFileIdListByFatherId(fileMessageId);
            searchFileMessageSons(fileIdList, totalRemoveFileMessageIdList);
        }
    }

    /**
     * 找孩子
     *
     * @param father
     * @param fileMessageList
     */
    private void searchSon(FileMessage father, List<FileMessage> fileMessageList) {
        List<FileMessage> sonList = fileMessageList.stream().filter(item -> {
            if (item.getFatherId().equals(father.getId())) {
                return true;
            } else {
                return false;
            }
        }).map(son -> {
            //继续给儿子寻找孙子
            this.searchSon(son, fileMessageList);
            return son;
        }).collect(Collectors.toList());
        father.setChildren(sonList);
    }

}



