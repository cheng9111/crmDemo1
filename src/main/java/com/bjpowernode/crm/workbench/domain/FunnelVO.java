/**
 * @项目名：crm-project
 * @创建人： Administrator
 * @创建时间： 2020-06-11
 * @公司： www.bjpowernode.com
 * @描述：
 */
package com.bjpowernode.crm.workbench.domain;

/**
 * <p>NAME: FunnelVO</p>
 * @author Administrator
 * @date 2020-06-11 18:03:35
 */
public class FunnelVO {
    private String name;
    private long value;

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * <p>You can use getName() to get the value of name</p>
     *
     * @param name name
     */
    public void setName(String name) {
        this.name=name;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets the value
     * <p>You can use getValue() to get the value of value</p>
     *
     * @param value value
     */
    public void setValue(long value) {
        this.value=value;
    }
}
