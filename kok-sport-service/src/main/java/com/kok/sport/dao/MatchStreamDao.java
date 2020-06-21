package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.MatchStream;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 比赛视频源表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
public interface MatchStreamDao extends BaseMapper<MatchStream> {
  /**
    * 比赛视频源表简单分页查询
    * @param matchStream 比赛视频源表
    * @return
    */
  IPage<MatchStream> getMatchStreamPage(PageVo pagevo, @Param("matchStream") MatchStream matchStream);

  @Select("SELECT * FROM match_stream_t WHERE match_id = #{MatchId}")
  MatchStream findMatchStreamByMatchId(Long MatchId);


}
