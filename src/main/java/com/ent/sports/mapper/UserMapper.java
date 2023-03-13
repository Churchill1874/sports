package com.ent.sports.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ent.sports.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select max(account) as maxAccount from user")
    int maxCount();

}
