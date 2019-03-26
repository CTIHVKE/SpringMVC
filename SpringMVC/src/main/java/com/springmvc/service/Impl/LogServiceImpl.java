package com.springmvc.service.Impl;

import com.springmvc.dao.LogMapper;
import com.springmvc.entity.Log;
import com.springmvc.service.LogService;
import org.springframework.stereotype.Service;


@Service
public class LogServiceImpl implements LogService {

    private LogMapper mapper;

    @Override
    public int insert(Log log) {
        return mapper.insert(log);
    }

    @Override
    public Log SelectByKey(Log log) {
        return mapper.selectByPrimaryKey(log.getLogGuid());
    }
}
