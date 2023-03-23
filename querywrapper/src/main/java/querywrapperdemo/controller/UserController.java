package querywrapperdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import querywrapperdemo.dao.UserMapper;
import querywrapperdemo.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.*;

/**
 * @author lj
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    /**
     * 案例一：根据name模糊查看未删除的用户列表信息
     * @param name
     * @return
     * SQL实现
     * select * from t_user
     * where del_flag = 0
     * <if test ="name!=null and ''!=name">
     *     and name like concat('%',#{name},'%')
     * </if>
     * order by create_time desc
     */
    @RequestMapping("/list")
    public Map<String,Object> getList(@RequestParam String name){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //name不为空时，组装模糊查询条件
        wrapper.
                like(StringUtils.isNotBlank(name),"name",name);
        //未删除的数据
        wrapper.
                eq("del_flag",0);
        //按创建时间降序
        wrapper.
                orderByDesc("creat_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;

    }

    /**
     * 案例二：查看姓张的并且邮箱不为空的用户列表
     * @return
     * SQL实现
     * select * from t_user
     * where del_flag = 0
     * and name like concat('李','%')
     * and email is not null
     * order by create_time desc
     */
    @RequestMapping("/list2")
    public Map<String,Object> getList2(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //姓张的
        wrapper.likeRight("name","张");
        //邮箱不为空
        wrapper.isNotNull("email");
        //未删除的
        wrapper.eq("del_flag",0);
        //按创建时间降序
        wrapper.
                orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例三：年龄范围查询（20-30之间的）
     * @return
     * SQL实现
     * select * from t_user
     * where del_flag=0
     * and age between 20 and 30
     * order by create_time desc
     */


    @RequestMapping("/list3")
    public Map<String,Object> getList3(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //年龄范围20-30
        wrapper.between("age",20,30);
        //未删除的
        wrapper.eq("del_flag",0);
        //按创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例四：根据createTime查看当日的用户列表
     * @return
     * SQL实现
     * select * from t_user
     * where del_flag=0
     * and DATE(create_time) = STR_TO_DATE('2023-03-14','%Y-%m-%d')
     * order by create_time desc
     */
    @RequestMapping("/list4")
    public Map<String,Object> getList4(@RequestParam String createTime) {
        Map<String, Object> result = new HashMap<>();
        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //查询条件为创建时间
        wrapper.apply(StringUtils.isNotBlank(createTime), "DATE(create_time) = STR_TO_DATE('" + createTime + "','%Y-%m-%d')");
        //未删除
        wrapper.eq("del_flag", 0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data", list);
        return result;
    }

    /**
     * 案例五：查看某个时间段内的用户列表
     * @return
     * SQL实现
     * select * from t_user
     * where del_flag=0
     * and DATE(create_time) >= STR_TO_DATE('2023-03-14 ','%Y-%m-%d')
     * AND DATE(create_time) <= STR_TO_DATE('2023-03-21 ','%Y-%m-%d')
     * order by create_time desc
     */
    @RequestMapping("/list5")
    public Map<String,Object> getList5(@RequestParam String startTime,@RequestParam String endTime){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //查询条件为创建时间
        wrapper.apply(StringUtils.isNotBlank(startTime),"DATE(create_time) >= STR_TO_DATE('"+startTime+"','%Y-%m-%d')");
        wrapper.apply(StringUtils.isNotBlank(endTime),"DATE(create_time) <= STR_TO_DATE('"+endTime+"','%Y-%m-%d')");
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例六：查询姓李的并且邮箱不为空或者是年龄大于16的用户
     * @return
     * SQL实现
     * select * from t_user
     * where del_flag = 0;
     * and name like concat(‘李’,'%')
     * and (email is not null or age > 16)
     * order by create_time desc;
     */
    @RequestMapping("/list6")
    public Map<String,Object> getList6(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //and方法嵌套
        wrapper.likeRight("name","李").and(
                userQueryWrapper -> userQueryWrapper.isNotNull("email")
                .or().lt("age",16)
        );
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例七：根据ids查看用户列表信息
     * @return
     */
    @RequestMapping("/list7")
    public Map<String,Object> getList7(@RequestParam String ids){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        if(StringUtils.isNotBlank(ids)){
            //字符串转数组再转List
            Collection<String> collection = Arrays.asList(ids.split(","));
            //in方法
            wrapper.in(collection.size()>0,"id",collection);
        }

        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;
    }

    /**
     * 案例八：根据条件查看用户列表信息----自己编写sql
     * @return
     */
    @RequestMapping("/list8")
    public Map<String,Object> getList8(){
        Map<String,Object> result = new HashMap<>();

        //构建一个查询的wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //未删除
        wrapper.eq("del_flag",0);
        //创建时间降序
        wrapper.orderByDesc("create_time");

        List<User> list = userMapper.getUserList(wrapper);
        result.put("data",list);
        return result;
    }
    //LambdaWrapper
    public Map<String,Object> getList9(){
        Map<String,Object> result = new HashMap<>();
        //QueryWrapper
//        QueryWrapper<User> wrapper = new QueryWrapper<User>();
//        wrapper.eq("id",1);
//        List<User> list = userMapper.selectList(wrapper);
//        result.put("data",list);
//        return result;

        //引入lambda
//        QueryWrapper<User> wrapper = new QueryWrapper<User>();
//        wrapper.lambda().eq(User::getId,1);
//        List<User> list = userMapper.selectList(wrapper);
//        result.put("data",list);
//        return result;

        //LambdaQueryWrapper构造器
//        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda();
//        wrapper.eq(User::getId, 1);
//        List<User> list = userMapper.selectList(wrapper);

        //LambdaQueryWrapper简化
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getId,1);
        List<User> list = userMapper.selectList(wrapper);
        result.put("data",list);
        return result;

    }

}
