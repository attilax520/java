package com.kok.sport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.sport.dto.KokMatchDto;
import com.kok.sport.entity.KokMatch;
import com.kok.sport.vo.KokMatchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 比赛信息基础表 Mapper 接口
* </p>
*
* @author martin
* @since 2020-03-11
*/
@Mapper
public interface KokMatchDao extends BaseMapper<KokMatch> {

    /**
     * 简单分页查询
     * @param kokMatchDto 足球比赛查询对象
     * @return
     */
    IPage<KokMatchVO> getKokMatchPage(@Param("kokMatchDto") KokMatchDto kokMatchDto);

}
