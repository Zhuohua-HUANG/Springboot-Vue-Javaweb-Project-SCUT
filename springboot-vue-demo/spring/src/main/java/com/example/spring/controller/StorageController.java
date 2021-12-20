package com.example.spring.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spring.common.Result;
import com.example.spring.entity.Storage;
import com.example.spring.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.example.spring.mapper.StorageMapper;




@RestController
@RequestMapping("/storage")
public class StorageController {

    @Resource
    StorageMapper storageMapper;

    @PostMapping
    public Result<?> save(@RequestBody Storage Storage) {
        storageMapper.insert(Storage);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Storage Storage) {
        storageMapper.updateById(Storage);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        storageMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        storageMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(storageMapper.selectById(id));
    }


    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search ){
        return Result.success(storageMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Storage>lambdaQuery().like(Storage::getName, search).eq(Storage::getState,1)));
    }

    @GetMapping("/getStore")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search ,
                              @RequestParam Integer id){
        return Result.success(storageMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Storage>lambdaQuery().like(Storage::getName, search).eq(Storage::getStoreId,id)));
    }
}
