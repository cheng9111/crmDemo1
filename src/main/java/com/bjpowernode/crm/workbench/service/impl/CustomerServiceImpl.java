/**
 * @项目名：crm-project
 * @创建人： Administrator
 * @创建时间： 2020-06-09
 * @公司： www.bjpowernode.com
 * @描述：
 */
package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>NAME: CustomerServiceImpl</p>
 * @author Administrator
 * @date 2020-06-09 15:57:58
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> queryCustomerByName(String name) {
        return customerMapper.selectCustomerByName(name);
    }
}
