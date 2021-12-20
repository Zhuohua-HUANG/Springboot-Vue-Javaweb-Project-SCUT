package com.example.spring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@TableName("user_storage")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User_Storage {
    @TableId(type= IdType.AUTO)
    private Integer userId;
    private Integer storageId;
    private Integer num;
}
