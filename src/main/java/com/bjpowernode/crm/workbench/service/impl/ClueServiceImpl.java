package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;



    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    /*
（1）获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
（2）通过线索对象提取客户信息,保存客户表
（3）通过线索对象提取联系人信息，保存联系人
（4）线索备注转换到客户备注以及联系人备注
（5）“线索和市场活动”的关系转换到“联系人和市场活动”的关系
（6）如果有创建交易需求
（7）创建一条交易,还要将线索下的备注转到交易备注
（8） 删除线索备注
（9）删除线索和市场活动的关系
（10）删除线索
     */

    @Override
    public void saveConvert(Map<String, Object> map) {
        User user=(User)map.get("sessionUser");
        String isCreateTran=(String)map.get("isCreateTran");

        //（1）获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        String clueId=(String)map.get("clueId");
        Clue clue=clueMapper.selectClueById(clueId);

        //（2）通过线索对象提取客户信息,保存客户表(公司）
        Customer customer=new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setOwner(user.getId());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setContactSummary(clue.getContactSummary());
        customer.setAddress(clue.getAddress());
        customer.setDescription(clue.getDescription());
        customer.setNextContactTime(clue.getNextContactTime());

        customerMapper.insertCustomer(customer);

        //（3）通过线索对象提取联系人信息，保存联系人(个人）
        Contacts contacts=new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullName(clue.getFullName());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAddress(clue.getAddress());
        contacts.setDescription(clue.getDescription());
        contacts.setNextContactTime(clue.getNextContactTime());

        contactsMapper.insertContacts(contacts);

        //（4）线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList=clueRemarkMapper.selectClueRemarkByClueId(clueId);
        if(clueRemarkList!=null&&clueRemarkList.size()>=0){
            //客户备注
            CustomerRemark cur=null;
            //联系人备注
            ContactsRemark cor=null;

            //需要两个集合存放客户备注对象和联系人备注对象
            List<CustomerRemark> curList=new ArrayList<>();
            List<ContactsRemark> corList=new ArrayList<>();

            //循环取线索备注
            for(ClueRemark cr:clueRemarkList){
                //新客户备注对象
                cur=new CustomerRemark();
                cur.setId(UUIDUtils.getUUID());
                cur.setNoteContent(cr.getNoteContent());
                cur.setCreateBy(cr.getCreateBy());
                //可以用new Date()表示现在的时间做为客户备注时间，还可以用原来线索备注里的时间，旧时间（以业务为准）
                cur.setCreateTime(cr.getCreateTime());
                cur.setCustomerId(customer.getId());

                curList.add(cur);

                //新联系人备注对象
                cor=new ContactsRemark();
                cor.setId(UUIDUtils.getUUID());
                cor.setNoteContent(cr.getNoteContent());
                cor.setCreateBy(cr.getCreateBy());
                //可以用new Date()表示现在的时间做为客户备注时间，还可以用原来线索备注里的时间，旧时间（以业务为准）
                cor.setCreateTime(cr.getCreateTime());
                cor.setContactsId(contacts.getId());

                corList.add(cor);
            }

            //批量插入
            customerRemarkMapper.insertCustomerRemarkByList(curList);
            contactsRemarkMapper.insertContactsRemarkByList(corList);
        }

        //（5）“线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> carList=clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        if(carList!=null&&carList.size()>0){
            //联系人和市场活动的对象
            ContactsActivityRelation coar=null;
            List<ContactsActivityRelation> coarList=new ArrayList<>();

            for(ClueActivityRelation car:carList){
                coar=new ContactsActivityRelation();
                coar.setId(UUIDUtils.getUUID());
                coar.setContactsId(contacts.getId());
                coar.setActivityId(car.getActivityId());

                coarList.add(coar);
            }
            //敏捷开发-结对开发
            contactsActivityRelationMapper.insertContactsActivityRelationByList(coarList);

        }

        //（6）如果有创建交易需求，创建一条交易,还要将线索下的备注转到交易备注
        if("true".equals(isCreateTran)){
            //交易对象
            Tran tran=new Tran();
            tran.setId(UUIDUtils.getUUID());
            tran.setOwner(user.getId());
            tran.setMoney((String)map.get("amountOfMoney"));
            tran.setName((String)map.get("tradeName"));
            tran.setExpectedDate((String)map.get("expectedClosingDate"));
            tran.setCustomerId(customer.getId());
            tran.setStage((String)map.get("stage"));

            tranMapper.insertTran(tran);

            //（7）创建一条交易,还要将线索下的备注转到交易备注
            if(clueRemarkList!=null && clueRemarkList.size()>0){
                TranRemark tr=null; //交易备注
                List<TranRemark> tranList=new ArrayList<>();
                for(ClueRemark cr:clueRemarkList){
                    tr=new TranRemark();
                    tr.setId(UUIDUtils.getUUID());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setTranId(tran.getId());
                    tranList.add(tr);
                }

                tranRemarkMapper.insertTranRemarkByList(tranList);
            }

        }

        /*
        （8） 删除线索备注
        （9）删除线索和市场活动的关系
        （10）删除线索
         */
        //（8） 删除线索备注
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);

        //（9）删除线索和市场活动的关系
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
        //（10）删除线索
        clueMapper.deleteClueById(clueId);

    }
}
