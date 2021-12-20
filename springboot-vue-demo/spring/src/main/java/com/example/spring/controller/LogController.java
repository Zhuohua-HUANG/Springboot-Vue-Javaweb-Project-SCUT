package com.example.spring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.common.Result;
import com.example.spring.entity.Log;
import com.example.spring.entity.Storage;
import com.example.spring.mapper.LogMappper;
import com.example.spring.mapper.StorageMapper;
import com.example.spring.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/logController")
public class LogController {


    @Resource
    LogMappper LogMapper;
    @Resource
    StorageMapper StorageMapper;
    @Resource
    UserMapper UserMapper;

    @PostMapping
    public Result<?> save(@RequestBody Log log) {
        log.setLocalDateTime(LocalDateTime.now());
        LogMapper.insert(log);
        System.out.println("log插入成功:");
        System.out.println(log);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Log log) {
        UpdateWrapper<Log> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", log.getUserId());
        updateWrapper.eq("storage_id", log.getStorageId());
        LogMapper.update(log, updateWrapper);
        return Result.success();
    }

    @PostMapping("/deleteEntity")
    public Result<?> deleteEntity(@RequestBody Log log) {
        System.out.println("删除以下记录：");
        System.out.println(log);
        QueryWrapper<Log> wrapper = new QueryWrapper<>(log);
        LogMapper.delete(wrapper);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        LogMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        LogMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(LogMapper.selectById(id));
    }

    @GetMapping()
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam Integer id) {
        List<Storage> StorageList = StorageMapper.selectList(Wrappers.<Storage>lambdaQuery().like(Storage::getName, search).eq(Storage::getStoreId, id));
        System.out.println(StorageList);
        List<Integer> IL = new LinkedList<>();
        for (int i = 0; i < StorageList.size(); i++) {
            IL.add(StorageList.get(i).getId());
        }
        List<Log> LogList = LogMapper.selectList(Wrappers.<Log>lambdaQuery().in(Log::getStorageId, IL));
        List<Log> LogList2 = new LinkedList<>();
        for (int i = 0; i < LogList.size(); i++) {
            Log log = LogList.get(i);
            log.setStorageName(StorageMapper.selectById(log.getStorageId()).getName());
            log.setUsername(UserMapper.selectById(log.getUserId()).getUsername());
            LogList2.add(log);
        }
        Page<Log> logPage = new Page<>(pageNum, pageSize);
        logPage.setRecords(LogList2);
        return Result.success(logPage);
    }

    @GetMapping("/admin")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Log> LogList = LogMapper.selectList(Wrappers.<Log>lambdaQuery());
        List<Log> LogList2 = new LinkedList<>();
        for (int i = 0; i < LogList.size(); i++) {
            Log log = LogList.get(i);
            log.setStorageName(StorageMapper.selectById(log.getStorageId()).getName());
            log.setUsername(UserMapper.selectById(log.getUserId()).getUsername());
            LogList2.add(log);
        }
        Page<Log> logPage = new Page<>(pageNum, pageSize);
        logPage.setRecords(LogList2);
        return Result.success(logPage);
    }
}
