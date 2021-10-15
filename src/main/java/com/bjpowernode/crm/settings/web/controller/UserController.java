package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(HttpServletRequest request){
        // /WEB-INF/pages/settings/qx/user/login.jsp
        Cookie[] cookies=request.getCookies();
        String loginAct=null;
        String loginPwd=null;
        for(Cookie cookie:cookies){
            String name=cookie.getName();
            if("loginAct".equals(name)){
                loginAct=cookie.getValue();
                continue;
            }

            if("loginPwd".equals(name)){
                loginPwd=cookie.getValue();
            }

        }

        if(loginAct!=null&&loginPwd!=null){
            Map<String,Object> map=new HashMap<>();
            map.put("loginAct",loginAct);
            map.put("loginPwd",MD5Util.getMD5(loginPwd));
            User user=userService.queryUserByLoginActAndPwd(map);
            request.getSession().setAttribute(Contants.SESSION_USER,user);
            return "redirect:/workbench/index.do";
        }else{
            return "settings/qx/user/login";
        }
    }


    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request,
                                      HttpServletResponse response, HttpSession session){

        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd", MD5Util.getMD5(loginPwd));

        User user=userService.queryUserByLoginActAndPwd(map);

        ReturnObject returnObject=new ReturnObject();
        String ip=request.getRemoteAddr();
        System.out.println(ip);

        if(user==null){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("用户名或密码错误");
        }else{
            //compareTo字符串比较，现在的时间大于数据里用户表的expireTime，过期
            if(DateUtils.formatDateTime(new Date()).compareTo(user.getExpireTime())>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
                returnObject.setMessage("账号已经过期");
            }else if("0".equals(user.getLockState())){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
                returnObject.setMessage("状态已经被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
                returnObject.setMessage("ip地址受限");
            } else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //只有在登录成功后才将user放到session中去
                session.setAttribute(Contants.SESSION_USER,user);

                //是否需要做免登录功能
                if("true".equals(isRemPwd)){
                    Cookie c1=new Cookie("loginAct",loginAct);
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);

                    Cookie c2=new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    //设置同名的cookie，将原来的cookie覆盖
                    Cookie c1=new Cookie("loginAct",null);
                    c1.setMaxAge(0); //不保存
                    response.addCookie(c1);

                    Cookie c2=new Cookie("loginPwd",null);
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }

        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //设置同名的cookie，将原来的cookie覆盖
        Cookie c1=new Cookie("loginAct",null);
        c1.setMaxAge(0); //不保存
        response.addCookie(c1);

        Cookie c2=new Cookie("loginPwd",null);
        c2.setMaxAge(0);
        response.addCookie(c2);

        session.invalidate();

        return "redirect:/";
    }
}
