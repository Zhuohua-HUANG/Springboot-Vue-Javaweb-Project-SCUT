package com.example.spring.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.common.Result;
import com.example.spring.entity.Order;
import com.example.spring.entity.Storage;
import com.example.spring.entity.User;
import com.example.spring.mapper.StorageMapper;
import com.example.spring.mapper.OrderMapper;
import com.example.spring.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;







@RestController
@RequestMapping("/Order")
public class OrderController {


    @Resource
    OrderMapper OrderMapper;
    @Resource
    StorageMapper StorageMapper;
    @Resource
    UserMapper userMapper;

    //提交订单
    @PostMapping
    public Result<?> save(@RequestBody Order order,@RequestParam Integer id,@RequestParam Integer price) {
        //插入
        OrderMapper.insert(order);
        //修改账户金额
        User user=userMapper.selectById(id);
        System.out.println("用户账户余额："+user.getAccount());
        user.setAccount(user.getAccount().subtract( new BigDecimal(order.getNum() * price+"")));
        userMapper.updateById(user);
        User user2=userMapper.selectById(id);
        System.out.println("用户账户余额："+user2.getAccount());
        //销量添加
        Storage storage=StorageMapper.selectById(order.getStorageId());
        storage.setSalesVolume(storage.getSalesVolume()+order.getNum());
        StorageMapper.updateById(storage);
        System.out.println("销量添加成功");
        return Result.success();
    }
    //订单状态改变
    @PutMapping
    public Result<?> update(@RequestBody Order order,@RequestParam Integer id,@RequestParam Integer price){
        System.out.println(order);
        OrderMapper.updateById(order);
        if(order.getOstate()==2){
            User user=userMapper.selectById(id);
            System.out.println("商家账户余额："+user.getAccount());
            user.setAccount(user.getAccount().add( new BigDecimal(order.getNum() * price+"")));
            userMapper.updateById(user);
            User user2=userMapper.selectById(id);
            System.out.println("商家账户余额："+user2.getAccount());
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        OrderMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        OrderMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(OrderMapper.selectById(id));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search,@RequestParam Integer id){
//        Page<Storage> storagePage = StorageMapper.selectPage(new Page<>(1,100), Wrappers.<Storage>lambdaQuery().like(Storage::getName, search));
        Page<Order> OrderPage = OrderMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Order>lambdaQuery().eq(Order::getCustomerId, id));
        List<Order> Olist=OrderPage.getRecords();
        List<Storage> Slist=new LinkedList<Storage>();
        for(int i=0;i<Olist.size();i++){
            Storage s;
            s=StorageMapper.selectById(Olist.get(i).getStorageId());
            s.setNum(Olist.get(i).getNum());
            s.setCustomerId(Olist.get(i).getCustomerId());
            s.setOstate(Olist.get(i).getOstate());
            s.setOrderId(Olist.get(i).getId());
            Slist.add(s);
        }
        Page<Storage> storagePage=new Page<>(pageNum, pageSize);
        storagePage.setRecords(Slist);
        return Result.success(storagePage);
    }

    @GetMapping("/store")
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search,@RequestParam Integer id){
//        Page<Storage> storagePage = StorageMapper.selectPage(new Page<>(1,100), Wrappers.<Storage>lambdaQuery().like(Storage::getName, search));
        Page<Order> OrderPage = OrderMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Order>lambdaQuery().eq(Order::getStoreId, id));
        List<Order> Olist=OrderPage.getRecords();
        List<Storage> Slist=new LinkedList<Storage>();
        for(int i=0;i<Olist.size();i++){
            Storage s;
            s=StorageMapper.selectById(Olist.get(i).getStorageId());
            s.setNum(Olist.get(i).getNum());
            s.setCustomerId(Olist.get(i).getCustomerId());
            s.setOstate(Olist.get(i).getOstate());
            s.setOrderId(Olist.get(i).getId());
            Slist.add(s);
        }
        Page<Storage> storagePage=new Page<>(pageNum, pageSize);
        storagePage.setRecords(Slist);
        return Result.success(storagePage);
    }



}
