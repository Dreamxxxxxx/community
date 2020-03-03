package life.maijiang.community.mapper;

import life.maijiang.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id = #{parentId}")
    Comment selectById(@Param("parentId") Long parentId);

    @Select("select * from comment where parent_id = #{parentId} and type = 1")
    List<Comment> selectIdType(@Param("parentId") Integer parentId);

    @Select("select * from comment where parent_id = #{parentId} and type = 1 order by gmt_create desc")
    List<Comment> selectIdTypeByDesc(@Param("parentId") Integer parentId);
}
