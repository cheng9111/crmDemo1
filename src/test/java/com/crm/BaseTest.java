package com.crm;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //spring整合junit
@ContextConfiguration({"classpath:applicationContext.xml"})
public class BaseTest {
}
