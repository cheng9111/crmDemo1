package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeService {

    //查询所有的字典类型
    List<DicType> queryAllDicTypes();
    //按code查询一个字典类型
    DicType queryDicTypeByCode(String code);
    //保存
    int savetCreateDicType(DicType dicType);
    //批量删除
    int deleteDicTypeByCodes(String[] codes);
    //更新
    int saveEditDicType(DicType dicType);
}
