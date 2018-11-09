package com.elecredit.op.constants;

/**
 * Created by zhaoyou
 * Date:2018/10/10
 * Time:13:18
 * 批量查询任务每条记录的状态
 */
public class BatchPlanStatus {
    /**
     * 等待处理
     */
    public static int WAIT_PROCESS=1;
    /**
     * 正在处理
     */
    public static int BE_PROCESS=2;
    /**
     * 成功
     */
    public static int SUCCESS=3;
    /**
     * 无结果
     */
    public static int NO_RESULT=4;
    /**
     * 失败
     */
    public static int FAIL=5;



}
