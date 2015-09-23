package com.alice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志文件生成
 */
public abstract class Logable {
    /**
     * 创建常量日志属性
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

}
