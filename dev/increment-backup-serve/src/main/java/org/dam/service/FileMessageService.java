package org.dam.service;

import org.dam.entity.FileMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author dam
* @description 针对表【file_message】的数据库操作Service
* @createDate 2024-01-28 21:38:07
*/
public interface FileMessageService extends IService<FileMessage> {

    List<FileMessage> buildTree(Long sourceId);

    void recursionRemoveFileMessage(List<Long> removeFileMessageIdList);

    List<FileMessage> listChildrenBySourceIdAndFatherId(Long sourceId, Long fatherId);

    void unCompress(Long fileMessageId);
}
