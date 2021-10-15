package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

/**
 * @项目名：crm-project
 * @创建人： Administrator
 * @创建时间： 2020-06-09
 * @公司： www.bjpowernode.com
 * @描述：
 */
public interface CustomerService {
    List<Customer> queryCustomerByName(String name);
}
