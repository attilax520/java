package com.kok.sport.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kok.base.exception.ApplicationException;
import com.kok.base.vo.PageVo;
import com.kok.sport.entity.FootballOdds;
import com.kok.sport.integration.SyncFootballNumberOddsHistoryService;
import com.kok.sport.integration.SyncFootballNumberOddsLiveHistoryService;
import com.kok.sport.integration.impl.SyncFootballNumberOddsHistoryServiceImpl;
import com.kok.sport.service.FootballOddsService;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.LogUtil;
import com.kok.sport.utils.db.MybatisUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.kok.base.utils.Result;

/**
 * 足球盘口指数表
 *
 * @author martin
 * @date 2020-03-28 00:59:47
 */
@RestController
@AllArgsConstructor
@Api(value = "足球盘口指数表", tags = "系统生成 - 足球盘口指数表")
public class FootballOddsController implements IFootballOddsController {

	private final FootballOddsService footballOddsService;

	private final SyncFootballNumberOddsHistoryService syncFootballNumberOddsHistoryService;

	private final SyncFootballNumberOddsLiveHistoryService syncFootballNumberOddsLiveHistoryService;
	static Logger logger2 = LoggerFactory.getLogger(FootballOddsController.class);
	String curindex="";
	@ApiOperation("odds数据落地")
	@GetMapping("insertFootballNumberOddsDataHistoryV2")
	public Object insertFootballNumberOddsDataV2(@RequestParam String where) throws Exception {
		// Football.Number.Odds_
		//select * from foot_match_exV3 where match_time between unix_timestamp(date_sub(now(), interval 8 day)) and unix_timestamp(now() ) order by match_time desc
		String sql = "select * from foot_match_exV3 "+where;
		insertFootballNumberOddsDataV4(sql);
		return "ok";
	}

	public void insertFootballNumberOddsDataV4(String sql) throws Exception {
		List<LinkedHashMap> matchids = MybatisUtil.query(sql );
		logger2.info("matchids:" + matchids.size());
		int i = 0;
		for (LinkedHashMap linkedHashMap : matchids) {

			try {
				i++;
			//	System.out.println();
				String mid =  linkedHashMap.get("id").toString();
				  curindex = "matchid order:" + i+"/"+matchids.size();
				logger2.info(curindex);
				logger2.info(JSON.toJSONString(linkedHashMap));

				SyncFootballNumberOddsHistoryServiceImpl syncFootballNumberOddsHistoryServiceImpl = new SyncFootballNumberOddsHistoryServiceImpl();
				syncFootballNumberOddsHistoryServiceImpl.  curindex = "matchid order:" + i+"/"+matchids.size();
				syncFootballNumberOddsHistoryServiceImpl. sqlSessionFactory = MybatisUtil.getSqlSessionFactoryRE();
				syncFootballNumberOddsHistoryServiceImpl.insertFootballNumberOddsData(Long.parseLong(mid));
			} catch (Exception e) {
				logger2.error("", e);
			}

		}
	}

	@Deprecated
	@ApiOperation("简单分页查询")
	@GetMapping("insertFootballNumberOddsDataHistory")
	public Object insertFootballNumberOddsData(@RequestParam String where) throws Exception {
		// Football.Number.Odds_
		List<String> matchids = CaptchData.matchids(where);
		logger2.info("matchids:" + matchids.size());
		int i = 0;
		for (String mid : matchids) {
			i++;

			logger2.info("matchid order:" + i);

			try {
				new SyncFootballNumberOddsHistoryServiceImpl().insertFootballNumberOddsData(Long.parseLong(mid));
			} catch (Exception e) {
				logger2.error("", e);
			}

		}
		return "ok";
	}

	/**
	 * 简单分页查询
	 * 
	 * @param pagevo       分页对象
	 * @param footballOdds 足球盘口指数表
	 * @return
	 */
	@Override
	@ApiOperation("简单分页查询")
	public Result<IPage<FootballOdds>> getFootballOddsPage(PageVo<FootballOdds> pagevo, FootballOdds footballOdds)
			throws ApplicationException {
		return new Result<>(footballOddsService.getFootballOddsPage(pagevo, footballOdds));
	}

	/**
	 * 通过id查询单条记录
	 * 
	 * @param id
	 * @return R
	 */
	@Override
	@ApiOperation("通过id查询单条记录")
	public Result<FootballOdds> getById(@PathVariable("id") Long id) throws ApplicationException {
		return new Result<>(footballOddsService.getById(id));
	}

	/**
	 * 新增记录
	 * 
	 * @param footballOdds
	 * @return R
	 */
	@Override
	@ApiOperation("通过id查询单条记录")
	public Result save(@RequestBody FootballOdds footballOdds) throws ApplicationException {
		return new Result<>(footballOddsService.save(footballOdds));
	}

	/**
	 * 修改记录
	 * 
	 * @param footballOdds
	 * @return R
	 */
	@Override
	@ApiOperation("通过id查询单条记录")
	public Result update(@RequestBody FootballOdds footballOdds) throws ApplicationException {
		return new Result<>(footballOddsService.updateById(footballOdds));
	}

	/**
	 * 通过id删除一条记录
	 * 
	 * @param id
	 * @return R
	 */
	@Override
	@ApiOperation("通过id查询单条记录")
	public Result removeById(@PathVariable Long id) throws ApplicationException {
		return new Result<>(footballOddsService.removeById(id));
	}

	/**
	 * 拉取单场指数数据
	 * 
	 * @param Id
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	@ApiOperation("拉取足球单场指数数据")
	public Result insertFootballNumberOddsData(Long Id) throws ApplicationException {
		return new Result<>(syncFootballNumberOddsHistoryService.insertFootballNumberOddsData(Id));
	}

	/**
	 * 拉取足球实时指数数据
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	@ApiOperation("拉取足球实时指数数据")
	public Result insertFootballLiveOdds() throws ApplicationException {
		return new Result<>(syncFootballNumberOddsLiveHistoryService.insertFootballLiveOdds());
	}

	public FootballOddsController() {
		this.footballOddsService = null;
		this.syncFootballNumberOddsHistoryService = null;
		this.syncFootballNumberOddsLiveHistoryService = null;
		// TODO Auto-generated constructor stub
	}

	public void insertFootballNumberOddsDataV3(String sql) {
		// TODO Auto-generated method stub
		
	}

}
