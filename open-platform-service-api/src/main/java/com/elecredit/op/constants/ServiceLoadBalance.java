package com.elecredit.op.constants;

/**
 * 接口调用负载策略
 */
public class ServiceLoadBalance {
    /**
     * 按渠道配置负载
     */
    public static int BY_SUPPLIER = 1;
    /**
     * 按最高可用性
     */
    public static int BY_AVAILABLE = 2;
}
