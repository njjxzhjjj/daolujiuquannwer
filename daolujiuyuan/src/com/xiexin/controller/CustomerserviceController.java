package com.xiexin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiexin.bean.Customerservice;
import com.xiexin.bean.CustomerserviceExample;
import com.xiexin.service.CustomerserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customerservice")
public class CustomerserviceController{
@Autowired(required = false)
private CustomerserviceService customerserviceService;

//登录
    @RequestMapping("/login") //  /api/customerservice/login
    public Map login(Customerservice customerservice, HttpSession session){
        Map codeMap = new HashMap();
        //登录  select * from customerservice where account=? and password=?
        CustomerserviceExample customerserviceExample = new CustomerserviceExample();
        CustomerserviceExample.Criteria criteria = customerserviceExample.createCriteria();
        criteria.andAccountEqualTo(customerservice.getAccount());
        criteria.andPasswordEqualTo(customerservice.getPassword());

        List<Customerservice> accounts = customerserviceService.selectByExample(customerserviceExample);
        if(accounts !=null&&accounts.size()>0){
            Customerservice dbAccount=accounts.get(0);
            //把这个账号信息放到session中
            session.setAttribute("dbAccount",dbAccount);
            codeMap.put("code",0);
            codeMap.put("msg","登录成功");
            codeMap.put("account",dbAccount.getAccount());
            return codeMap;
        }else{
            codeMap.put("code",4001);
            codeMap.put("msg","账号或者密码错误");
            return codeMap;
        }
    }


//增
// 后端订单增加 -- 针对layui的 针对前端传 json序列化的
@RequestMapping("/insert")
public Map insert(@RequestBody Customerservice customerservice){ // orders 对象传参, 规则: 前端属性要和后台的属性一致!!!
Map map = new HashMap();
int i =  customerserviceService.insertSelective(customerservice);
if(i>0){
map.put("code",200);
map.put("msg","添加成功");
return map;
}else{
map.put("code",400);
map.put("msg","添加失败,检查网络再来一次");
return map;
}
}


// 删
// 删除订单  根据 主键 id 删除
@RequestMapping("/deleteById")
public Map deleteById(@RequestParam(value = "id") Integer id) {
Map responseMap = new HashMap();
int i = customerserviceService.deleteByPrimaryKey(id);
if (i > 0) {
responseMap.put("code", 200);
responseMap.put("msg", "删除成功");
return responseMap;
} else {
responseMap.put("code", 400);
responseMap.put("msg", "删除失败");
return responseMap;
}
}

// 批量删除
@RequestMapping("/deleteBatch")
public Map deleteBatch(@RequestParam(value = "idList[]") List<Integer> idList) {
    for (Integer integer : idList) {
    this.deleteById(integer);
    }
    Map responseMap = new HashMap();
    responseMap.put("code", 200);
    responseMap.put("msg", "删除成功");
    return responseMap;
    }


// 改
    // 修改订单
    @RequestMapping("/update")
    public Map update(@RequestBody Customerservice customerservice) {
    Map map = new HashMap();
    int i = customerserviceService.updateByPrimaryKeySelective( customerservice);
    if (i > 0) {
    map.put("code", 200);
    map.put("msg", "修改成功");
    return map;
    } else {
    map.put("code", 400);
    map.put("msg", "修改失败,检查网络再来一次");
    return map;
    }
    }

// 查--未分页
    // 全查
    @RequestMapping("/selectAll")
    public Map selectAll(){
    List<Customerservice> customerservices =  customerserviceService.selectByExample(null);
        Map responseMap = new HashMap();
        responseMap.put("code", 0);
        responseMap.put("msg", "查询成功");
        responseMap.put("customerservices", customerservices);
        return responseMap;
        }

// 查-- 查询+自身对象的查询 + 分页
// 分页查询
    @RequestMapping("/selectAllByPage")
    public Map selectAllByPage(Customerservice customerservice, @RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
                               @RequestParam(value = "limit", required = true) Integer pageSize) {
    // 调用service 层   , 适用于 单表!!!
    PageHelper.startPage(page, pageSize);
    CustomerserviceExample example = new CustomerserviceExample();
    CustomerserviceExample.Criteria criteria = example.createCriteria();
/*

    if (null!=customerservice.getYYYYYYYY()&&!"".equals(customerservice.getYYYYYYY())){
         criteria.andPhoneEqualTo(customerservice.getPhone());   // 1
    }
*/

    List<Customerservice> customerservicesList = customerserviceService.selectByExample(example);
        PageInfo pageInfo = new PageInfo(customerservicesList);
        long total = pageInfo.getTotal();
        Map responseMap = new HashMap();
        responseMap.put("code", 0);
        responseMap.put("msg", "查询成功");
        responseMap.put("pageInfo", pageInfo);
        responseMap.put("count", total);
        return responseMap;
        }




    }
