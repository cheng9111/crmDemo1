package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    //保存创建的市场活动
    int saveCreateActivity(Activity activity);

    //查询市场活动列表按多条件和分页
    List<Activity> queryActivityForPageByCondition(Map<String,Object> map);

    //根据条件去查询市场活动的数量
    long queryCountOfActivityByCondition(Map<String,Object> map);

    //根据id去查询市场活动(用于编辑）
    Activity queryActivityById(String id);

    //保存修改的市场活动
    int saveEditActivity(Activity activity);

    //根据ids来删除市场活动
    int deleteActivityByIds(String[] ids);

    //导出时要抓取市场活动表中所有数据
    List<Activity> queryActivityForDetailByIds(String[] ids);

    //导入将excel中的多个市场活动导入到数据库市场活动表
    int saveCreateActivityByList(List<Activity> activityList);

    //进入详情页面（用于详细）
    Activity queryActivityForDetailById(String id);

    //在其它模块中需要市场模块的支持(全部导出)
    List<Activity> queryAllActivityForDetail();

    //根据市场活动名查询所有的市场活动
    List<Activity> queryActivityForDetailByName(String name);

    //根据clueId获取关联的市场活动
    List<Activity> queryActivityForDetailByClueId(String clueId);

    //与该线索无关的市场活动
    List queryActivityNoBoundById(Map<String,Object> map);

}
