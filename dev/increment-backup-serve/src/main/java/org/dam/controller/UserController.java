package org.dam.controller;

import org.dam.entity.User;
import org.dam.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author dam
 * @create 2024/1/18 20:37
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    /**
     * 增添数据
     */
    @GetMapping("/insert")
    public Object insert(String name) {
        User user = new User();
        user.setName(name);
        return userMapper.insert(user);
    }

    /**
     * 查询数据
     */
    @GetMapping("/show")
    public Object show() {
        return userMapper.selectList(null);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/delete")
    public Object delete(Integer id) {
        return userMapper.deleteById(id);
    }

    /**
     * 修改数据
     */
    @GetMapping("/update")
    public Object update(Integer id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return userMapper.updateById(user);
    }
}
