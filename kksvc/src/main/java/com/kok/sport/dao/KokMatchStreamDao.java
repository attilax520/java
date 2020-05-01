package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.KokMatch;
import com.kok.sport.entity.KokMatchStream;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 比赛直播数据源表 Mapper 接口
* </p>
*
* @author martin
* @since 2020-03-11
*/
@Mapper
public interface KokMatchStreamDao extends BaseMapper<KokMatchStream> {

    /**
     * 比赛直播源表简单分页查询
     * @param kokMatchStream 比赛直播源表
     * @return
     */
    IPage<KokMatchStream> getKokMatchStreamPage(PageVo pageVo, @Param("kokMatchStream") KokMatchStream kokMatchStream);

    List<KokMatchStream> getKokMatchStreamById(@Param("matchId") Long matchId);

    /**
     * 比赛直播源保存
     * @param kokMatchStream
     * @return
     */
    int saveKokMatchStream(@Param("kokMatchStream") KokMatchStream kokMatchStream);

    /**
     * 比赛直播源删除
     * @param id
     * @return
     */
    int removeKokMatchStream(@Param("id") Long id);
}
