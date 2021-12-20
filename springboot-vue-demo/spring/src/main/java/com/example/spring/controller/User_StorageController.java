package com.example.spring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.common.Result;
import com.example.spring.entity.Storage;
import com.example.spring.entity.User;
import com.example.spring.entity.User_Storage;
import com.example.spring.mapper.StorageMapper;
import com.example.spring.mapper.UserStorageMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/USC")
public class User_StorageController {


    @Resource
    UserStorageMapper UserStorageMapper;
    @Resource
    StorageMapper StorageMapper;

    @PostMapping
    public Result<?> save(@RequestBody User_Storage userstorage) {
        User_Storage res=UserStorageMapper.selectOne(Wrappers.<User_Storage>lambdaQuery().eq(User_Storage::getUserId,userstorage.getUserId()).eq(User_Storage::getStorageId,userstorage.getStorageId()));

        if(res!=null){
            userstorage.setNum(userstorage.getNum()+res.getNum());
            UpdateWrapper<User_Storage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id",userstorage.getUserId());
            updateWrapper.eq("storage_id",userstorage.getStorageId());
            UserStorageMapper.update(userstorage,updateWrapper);
        }else {
            UserStorageMapper.insert(userstorage);
        }
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User_Storage userstorage) {
        UpdateWrapper<User_Storage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userstorage.getUserId());
        updateWrapper.eq("storage_id",userstorage.getStorageId());
        UserStorageMapper.update(userstorage,updateWrapper);
        return Result.success();
    }

    @PostMapping("/deleteEntity")
    public Result<?> deleteEntity(@RequestBody User_Storage userstorage) {
        System.out.println("删除以下记录：");
        System.out.println(userstorage);
        QueryWrapper<User_Storage> wrapper= new QueryWrapper<>(userstorage);
        UserStorageMapper.delete(wrapper);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        UserStorageMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        UserStorageMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(UserStorageMapper.selectById(id));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search,@RequestParam Integer id){
//        Page<Storage> storagePage = StorageMapper.selectPage(new Page<>(1,100), Wrappers.<Storage>lambdaQuery().like(Storage::getName, search));
        Page<User_Storage> UserStoragePage = UserStorageMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<User_Storage>lambdaQuery().eq(User_Storage::getUserId, id));
        List<User_Storage> USlist=UserStoragePage.getRecords();
        List<Storage> Slist=new LinkedList<Storage>();
        for(int i=0;i<USlist.size();i++){
            Storage s;
            s=StorageMapper.selectById(USlist.get(i).getStorageId());
            s.setNum(USlist.get(i).getNum());
            s.setCustomerId(USlist.get(i).getUserId());
            Slist.add(s);
        }
        Page<Storage> storagePage=new Page<>(pageNum, pageSize);
        storagePage.setRecords(Slist);
        return Result.success(storagePage);
    }
}
