package com.attilax.dbutil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.attilax.date.DateUtil;
import com.attilax.db.PgsqlService;
import com.attilax.io.FileService;
import com.attilax.io.pathx;
import com.attilax.oplog.AsynUtil;
import com.attilax.util.timestampUtil;

public class partiTableUtil {

	public static void main(String[] args) throws IOException {
		
	String tab = "cbm1";
	String MainTableSql=	geneMainTableSql(tab,"Cbm16");
	System.out.println(MainTableSql);
	partiTableUtil.createPartByYearMonth("2018-01", "2018-05", tab);
	
	if("a".equals("a"))
		return;

		//StartVblPart();
	//	StartVakPart();
		
	//	StartVajPart();
	  
		System.out.println("---------");
	//	partiTableUtil.createPartByWeekInYearMonthRange("2018-01", "2018-03", "vaj1v3");
		
	 //	dropTable("vaj1v3_p_20180107_20180114,vaj1v3_p_20180121_20180128,vaj1v3_p_20180201_20180207,vaj1v3_p_20180207_20180214,vaj1v3_p_20180214_20180221,vaj1v3_p_20180221_20180228,vaj1v3_p_20180228_20180301, ");

		// droptable(" drop table vaf1_p",1,12);
		//
		// deattach("vaf1");
		//
	 	
	 	 String tbs="vaj1v3_p_201801,vaj1v3_p_201802,vaj1v3_p_20180301_20180307,vaj1v3_p_20180307_20180314,vaj1v3_p_20180314_20180321,vaj1v3_p_20180321_20180328,vaj1v3_p_20180328_20180401,vaj1v3_p_befor2018" ;

		//  addPriKey("vaj1v3_p_201801,vaj1v3_p_201802,vaj1v3_p_20180301_20180307,vaj1v3_p_20180307_20180314,vaj1v3_p_20180314_20180321,vaj1v3_p_20180321_20180328,vaj1v3_p_20180328_20180401,vaj1v3_p_befor2018","vaj01");
		  addPriKey(" vaf1_2_p_201001_201801,vaf1_2_p_201801,vaf1_2_p_201802,vaf1_2_p_201803,vaf1_2_p_201804","vaf01");

		  
	// 	addIndex("vaf1", "vaf01,vaf01a");
	 	
	 	
	 	// addIndex_multiTabs_cols(tbs, "vaf01,bck01d,bdn01,vaa01,vaa07,vai01,vaj46,vaj51");

		// addIndex("vaf1_p", FileUtils.readFileToString(new File(
		// pathx.classPath(partiTableUtil.class)+"/index.txt")));
		  
		  
		  
		  tbs="vaf1_2_p_201001_201801,vaf1_2_p_201801,vaf1_2_p_201802,vaf1_2_p_201803,vaf1_2_p_201804";
		  addIndex_multiTabs_cols(tbs, "vaf01,vaf01a,bda01,vaa01");
		  addIndex_multiTabs_cols(tbs, "vaf42");
		  

	}

	private static String geneMainTableSql(String tab, String patiCol) {
		String sqldir="C:\\0wkspc\\oploggerPrj";
		String outSqlFileName = tab+timestampUtil.gettimeStamp_millsec_filenameEncode()+".sql";
		
		AsynUtil.execMeth_Ays(new   Runnable() {
			public void run() {
				PgsqlService.exportDDL(tab, outSqlFileName);
			}
		}, "threadName");
	
		
		
		String sqlpath=sqldir+"\\"+outSqlFileName;
		
	   FileService.exists_waitforGeneFile(sqlpath,10000 );
		String ddl =PgsqlService.  parseDDL(sqlpath);
		String partsux="PARTITION BY RANGE (@ptcol@ ) ";
		partsux=partsux.replaceAll("@ptcol@", patiCol);
		String MainTableSql=ddl+partsux;
		//System.out.println( ddl );
		return MainTableSql;
		
	}

	

	private static void StartVajPart() {
		partiTableUtil.createPartByYearMonth("2018-01", "2018-05", "vaj1_2");
		
		  String tbs="vaj1_2_pbefor2018,vaj1_2_p201801,vaj1_2_p201802,vaj1_2_p201803,vaj1_2_p201804";
		  addPriKey(tbs,"vaj01");
		
		  /*
		   *  
CREATE INDEX "vaj1_2_bck01d_index" ON "public"."vaj1_2" USING btree ("bck01d");

CREATE INDEX "vaj1_2_bdn01_index" ON "public"."vaj1_2" USING btree ("bdn01");

CREATE INDEX "vaj1_2_vaa01_index" ON "public"."vaj1_2" USING btree ("vaa01");

CREATE INDEX "vaj1_2_vaa07" ON "public"."vaj1_2" USING btree ("vaa07");

CREATE INDEX "vaj1_2_vai01" ON "public"."vaj1_2" USING btree ("vai01");

CREATE INDEX "vaj1_2_vaj46_index" ON "public"."vaj1_2" USING btree ("vaj46");

CREATE INDEX "vaj1_2_vaj51_index" ON "public"."vaj1_2" USING btree ("vaj51");
		   * 
		   * */
		  addIndex_multiTabs_cols(tbs, "bck01d,bdn01,vaa01,vaa07,vai01,vaj46,vaj51");
		  addIndex_multiTabs_cols(tbs, "vaj64");
		  
		  
		
	}

	private static void StartVakPart() {
		partiTableUtil.createPartByYearMonth("2018-01", "2018-05", "vak2");
		
		  String tbs="vak2_pbefor2018,vak2_p201801,vak2_p201802,vak2_p201803,vak2_p201804";
		  addPriKey(tbs,"vak01");
		
		  /*
		   * CREATE INDEX "bce01a_vak2_index" ON "public"."vak2" USING btree ("bce01a");

CREATE INDEX "vak01_vak2_index" ON "public"."vak2" USING btree ("vak01");

CREATE INDEX "vak2_vaa01_index" ON "public"."vak2" USING btree ("vaa01");

CREATE INDEX "vak2_vaa07_index" ON "public"."vak2" USING btree ("vaa07");

CREATE INDEX "vak2_vak06_index" ON "public"."vak2" USING btree ("vak06");

CREATE INDEX "vak2_vak13_index" ON "public"."vak2" USING btree ("vak13");
		   * 
		   * */
		  addIndex_multiTabs_cols(tbs, "vak13,bce01a,vak01,vaa01,vaa07,vak06,vak13");
		
	}

	private static void StartVblPart() {
		partiTableUtil.createPartByYearMonth("2018-01", "2018-05", "vbl2");
		  addPriKey(" vbl2_p_before2018,vbl2_p_201801,vbl2_p_201802,vbl2_p_201803,vbl2_p_201804","vbl01");
		  String tbs="vbl2_p_before2018,vbl2_p_201801,vbl2_p_201802,vbl2_p_201803,vbl2_p_201804";
		  addIndex_multiTabs_cols(tbs, "vbl19");
		
	}

	private static void dropTable(String tbs) {
		String[] a=tbs.split(",");
		for (String t : a) {
			t=t.trim();
			if(t.length()>0)
			System.out.println("drop table "+t+";");
		}
		
	}

	private static void addIndex(String tab, String cols) {

		String[] cols_arr = cols.split(",");

		String idxTmplt = " CREATE INDEX  index_@col@_on_@tab@  ON @tab@ USING btree (@col@); ";
		for (int i = 1; i <= 12; i++) {

			for (String col : cols_arr) {

				String idxTmplt2 = idxTmplt.replaceAll("@tab@", tab);// +"_p"+String.valueOf(i)
																		// );

				idxTmplt2 = idxTmplt2.replaceAll("@ptnum@", String.valueOf(i));
				idxTmplt2 = idxTmplt2.replaceAll("@col@", col);
				idxTmplt2 = idxTmplt2.replaceAll("@tab_ptnum@", tab + "_p" + String.valueOf(i));

				// CREATE INDEX "index_vaf1_bda01" ON "public"."vaf1_ori" USING
				// btree ("bda01"); CREATE INDEX "index_vaf1_vaf01" ON
				// "public"."vaf1_ori" USING btree ("vaa01");
				System.out.println(idxTmplt2);
			}
			// String x = "alter table @tab@ ADD PRIMARY KEY(\"vaf01\"); ";

		}

	}
	
	private static void addIndex_multiTabs_cols(String tabs, String cols) {

		String[] cols_arr = cols.split(",");

		String idxTmplt = " CREATE INDEX  index_@col@_on_@tab@  ON @tab@ USING btree (@col@); ";

		String[] a = tabs.split(",");
		for (String t : a) {

			for (String col : cols_arr) {

				String idxTmplt2 = idxTmplt.replaceAll("@tab@", t);// +"_p"+String.valueOf(i)
																		// );

			 
				idxTmplt2 = idxTmplt2.replaceAll("@col@", col);
			 

				// CREATE INDEX "index_vaf1_bda01" ON "public"."vaf1_ori" USING
				// btree ("bda01"); CREATE INDEX "index_vaf1_vaf01" ON
				// "public"."vaf1_ori" USING btree ("vaa01");
				System.out.println(idxTmplt2);

				// String x = "alter table @tab@ ADD PRIMARY KEY(\"vaf01\"); ";

			}
		}

	}

	private static void addPriKey(String tabs,String prikeyCol) {

		String[] a=tabs.split(",");
		for (String t : a) {
			
		 
	 
			String x = "alter table @tab@ ADD PRIMARY KEY(@pkcol@);  ";
			x = x.replaceAll("@tab@",  t);
			 	x = x.replaceAll("@pkcol@",prikeyCol);

			// CREATE INDEX "index_vaf1_bda01" ON "public"."vaf1_ori" USING
			// btree ("bda01"); CREATE INDEX "index_vaf1_vaf01" ON
			// "public"."vaf1_ori" USING btree ("vaa01");
			System.out.println(x);
		}

	}

	private static void deattach(String string) {
		for (int i = 1; i <= 12; i++) {
			String x = "alter table @tab@ DETACH PARTITION @tab@_p@pt@;";
			x = x.replaceAll("@tab@", string);
			x = x.replaceAll("@pt@", String.valueOf(i));
			System.out.println(x);
		}

	}

	private static void droptable(String string, int i, int j) {
		for (int m = i; m < j; m++) {
			System.out.println(" drop table vaf1_p" + String.valueOf(m) + ";");
		}

	}

	private static void createPartByYearMonth(String month_start, String month_end, String tab) {

		String bef = "CREATE TABLE @tab@_pbefor2018 PARTITION OF @tab@   FOR VALUES FROM ('2000-01-01 00:00:00') TO ('2018-01-01 00:00:00');";
		bef = bef.replaceAll("@tab@", tab);
		System.out.println(bef);

		List<String> li = DateUtil.getMonthrangleInInYearMonthRange(month_start, month_end);
		for (String y_month : li) {

			String crtratPartTableTmplt = "CREATE TABLE @tab@_p@ptnum@ PARTITION OF 	 @tab@ FOR VALUES FROM ('@startdate@') TO ('@endtdate@');";
			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@tab@", tab);

			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@ptnum@", indexEncode(y_month));

			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@startdate@", y_month + "-01");
			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@endtdate@",
					DateUtil.getFirstday_nextmonth(y_month));
			System.out.println(crtratPartTableTmplt);

		}

	}

	/*
	 * 2017-01-----2018-5 CREATE TABLE public.vaj1V2_p_befor2018 PARTITION OF
	 * public.vaj1V2 FOR VALUES FROM ('2000-01-01 00:00:00') TO ('2018-01-01
	 * 00:00:00');
	 * 
	 */
	private static void createPartByWeekInYearMonthRange(String month_start, String month_end, String tab) {

		String bef = "CREATE TABLE @tab@_p_befor2018 PARTITION OF @tab@   FOR VALUES FROM ('2000-01-01 00:00:00') TO ('2018-01-01 00:00:00');";
		bef = bef.replaceAll("@tab@", tab);
		System.out.println(bef);

		List<String> li = DateUtil.getWeekrangleInInYearMonthRange(month_start, month_end);
		for (String week_range : li) {
			String week_start = week_range.split(",")[0];
			String week_end = week_range.split(",")[1];
			String crtratPartTableTmplt = "CREATE TABLE @tab@_p_@startdate2@_@endtdate2@ PARTITION OF @tab@ FOR VALUES FROM ('@startdate@') TO ('@endtdate@');";
			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@tab@", tab);

			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@tab_maintab@", tab);
			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@startdate@",  ( week_start));
			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@endtdate@",   (week_end));
		
			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@startdate2@", indexEncode( week_start));
			crtratPartTableTmplt = crtratPartTableTmplt.replaceAll("@endtdate2@",  indexEncode(week_end));
			System.out.println(crtratPartTableTmplt);
		}

	}

	private static String indexEncode(String week_start) {
		// TODO Auto-generated method stub
		return week_start.replaceAll("-", "");
	}

}
