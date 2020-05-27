package com.kok.sport.utils.constant;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kok.sport.utils.MybatisMapper;
@Component
public class CommandLineRunnerImp implements CommandLineRunner {

	public static void main(String[] args) {
		// SpringbootRunner

		// TopLaunchApplication.run(CommonConstant.APPLICATION_SERVICE_FS_BOOK_NAME,
		// TopFsApplication.class, args);
	}

	// @Autowired
	// private IResourceFeginClient resourceFeginClient;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void run(String... args) throws Exception {

		SqlSession session = sqlSessionFactory.openSession(true);
		MybatisMapper MybatisMapper1 = session.getMapper(MybatisMapper.class);
		System.out.println(MybatisMapper1.querySql("select 'frm CommandLineRunnerImp '"));
		System.out.println("______________=================******************atti::");
	//	System.exit(0);
//		  

	}
}
