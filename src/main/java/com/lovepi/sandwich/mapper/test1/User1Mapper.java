package com.lovepi.sandwich.mapper.test1;

import com.lovepi.sandwich.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by cumt_cx on 2017/1/18.
 */
public interface User1Mapper {

    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);
}
