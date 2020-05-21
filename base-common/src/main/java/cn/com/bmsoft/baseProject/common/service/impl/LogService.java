package cn.com.bmsoft.baseProject.common.service.impl;

import cn.com.bmsoft.baseProject.common.model.log.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Boxing
 * @version: 1.0
 **/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogService extends BaseService<Log> {
}
