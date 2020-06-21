package com.kok.sport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kok.base.vo.PageVo;
import com.kok.sport.dao.KokMatchStreamDao;
import com.kok.sport.entity.KokMatchStream;
import com.kok.sport.service.KokMatchStreamService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 比赛直播数据源表 服务实现类
 * </p>
 *
 * @author martin
 * @since 2020-03-11
 */
@Service
public class KokMatchStreamServiceImpl extends ServiceImpl<KokMatchStreamDao, KokMatchStream> implements KokMatchStreamService {

    /**
     * 比赛直播源表简单分页查询
     * @param kokMatchStream 比赛直播源表
     * @return
     */
    @Override
    public IPage<KokMatchStream> getKokMatchStreamPage(PageVo<KokMatchStream> pageVo, KokMatchStream kokMatchStream) {
        return baseMapper.getKokMatchStreamPage(pageVo, kokMatchStream);
    }

    /**
     * 比赛直播源删除
     * @param id
     * @return
     */
    @Override
    public boolean removeKokMatchStream(Long id) {
        return baseMapper.removeKokMatchStream(id) > 0 ? true : false;
    }
}
