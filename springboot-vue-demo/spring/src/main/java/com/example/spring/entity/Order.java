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

@TableName("`order`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer customerId;
    private Integer storageId;
    private Integer storeId;
    private Integer num;
    private Integer ostate;
}
