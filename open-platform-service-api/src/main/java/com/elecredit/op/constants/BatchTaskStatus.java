package com.elecredit.op.constants;

/**
 * Created by zhaoyou
 * Date:2018/10/10
 * Time:13:18
 * 批量查询任务状态
 */
public class BatchTaskStatus {
    /**
     * 等待处理
     */
    public static int WAIT_PROCESS=1;
    /**
     * 正在处理
     */
    public static int BE_PROCESS=2;
    /**
     * 已暂停
     */
    public static int PAUSE=3;
    /**
     * 取消
     */
    public static int CANCEL=4;
    /**
     * 正在导出
     */
    public static int EXPORTING = 6;
    /**
     * 完成
     */
    public static int COMPLETE=5;



}
