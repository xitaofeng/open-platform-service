package com.elecredit.op.model;


import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 对账单
 * @author yangfei
 */
@Table(name = "op_statement")
public class Statement extends BaseModel implements Serializable {
    /**
     * 对账单ID
     */
    @Id
    @Column(name = "statement_id")
    private Long statementId;
    /**
     * 账单日
     */
    @Column(name = "statement_date")
    private LocalDate statementDate;
    /**
     * 结算周期起始日
     */
    @Column(name = "cycle_start")
    private LocalDate cycleStart;
    /**
     * 结算周期结束日
     */
    @Column(name = "cycle_end")
    private LocalDate cycleEnd;
    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;
    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private Long customerId;

}
