package com.example.spring.controller;

import com.example.spring.common.Result;
import com.example.spring.entity.Storage;
import com.example.spring.entity.User;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @site
 * @company
 * @create 2020-03-07 1:06
 */
@RestController
public class MailController {

    @Autowired
    JavaMailSender jsm;

    @Value("${spring.mail.from}")
    private String from;

    @PostMapping("/send")
    public Result<?> send( @RequestBody Storage storage){
        //建立邮箱消息
        SimpleMailMessage message = new SimpleMailMessage();
        //发送者
        message.setFrom(from);
        //接收者
        message.setTo(storage.getEmail());
        //发送标题
        message.setSubject("ABSURD购物平台确认发货");
        //发送内容
        message.setText("尊敬的"+storage.getUsername()+"：\n\t您好！" +
                "\n\t非常感谢您使用ABSURD购物平台，您购买的商品 "+
                storage.getName()+" 已经发货，请注意查收。");
        jsm.send(message);
        return Result.success();
    }
}