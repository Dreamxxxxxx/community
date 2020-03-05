package life.maijiang.community.mapper;

import life.maijiang.community.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(notifier,receiver,outerId,type,gmt_create,status) values (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status})")
    void insert(Notification notification);

    @Select("select * from notification")
    List<Notification> findAll();
}
