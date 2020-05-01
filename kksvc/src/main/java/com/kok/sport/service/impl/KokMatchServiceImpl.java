package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.sport.dao.KokMatchDao;
import com.kok.sport.dto.KokMatchDto;
import com.kok.sport.entity.KokMatch;
import com.kok.sport.service.KokMatchService;
import com.kok.sport.vo.KokMatchVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 比赛信息表 服务实现类
 * </p>
 *
 * @author martin
 * @since 2020-03-11
 */
@Service
public class KokMatchServiceImpl extends ServiceImpl<KokMatchDao, KokMatch> implements KokMatchService {

    /**
     * 简单分页查询
     * @param kokMatchDto 足球比赛查询对象
     * @return
     */
    @Override
    public IPage<KokMatchVO> getKokMatchPage(KokMatchDto kokMatchDto) {
        return baseMapper.getKokMatchPage(kokMatchDto);
    }

}
