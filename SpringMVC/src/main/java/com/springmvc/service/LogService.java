package com.springmvc.service;

import com.springmvc.entity.Log;

public interface LogService {
    int insert(Log log);

    Log SelectByKey(Log log);
}
