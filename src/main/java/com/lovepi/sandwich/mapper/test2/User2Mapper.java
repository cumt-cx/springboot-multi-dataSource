
package com.lovepi.sandwich.mapper.test2;

        import com.lovepi.sandwich.entity.User;
        import org.apache.ibatis.annotations.Mapper;

        import java.util.List;

/**
 * Created by cumt_cx on 2017/1/18.
 */
@Mapper
public interface User2Mapper {

    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);
}
