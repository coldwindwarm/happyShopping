package com.happyShopping.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_specification_option")
public class SpecificationOption implements Serializable {
    /**
     * 规格项ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 规格项名称
     */
    @Column(name = "option_name")
    private String optionName;

    /**
     * 规格ID
     */
    @Column(name = "spec_id")
    private Long specId;

    /**
     * 排序值
     */
    @Column(name = "orders")
    private Integer orders;

    private static final long serialVersionUID = 1L;

    /**
     * 获取规格项ID
     *
     * @return id - 规格项ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置规格项ID
     *
     * @param id 规格项ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取规格项名称
     *
     * @return option_name - 规格项名称
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * 设置规格项名称
     *
     * @param optionName 规格项名称
     */
    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
    }

    /**
     * 获取规格ID
     *
     * @return spec_id - 规格ID
     */
    public Long getSpecId() {
        return specId;
    }

    /**
     * 设置规格ID
     *
     * @param specId 规格ID
     */
    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    /**
     * 获取排序值
     *
     * @return orders - 排序值
     */
    public Integer getOrders() {
        return orders;
    }

    /**
     * 设置排序值
     *
     * @param orders 排序值
     */
    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}