package com.ppfuns;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppfuns.report.entity.AppAreaVisitCountDay;
import com.ppfuns.report.service.IAppAreaVisitCountDayService;
import com.ppfuns.report.utils.QueryConditionsUtils;
import com.ppfuns.util.ResultPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartManagerApplicationTests {

	@Autowired
	private IAppAreaVisitCountDayService iAppAreaVisitCountDayService;
	@Test
	public void contextLoads() {
		QueryWrapper<AppAreaVisitCountDay> qw = new QueryWrapper();
		String orderby = "tDate desc,parentColumnId asc";
		qw.select("t_date","parent_column_id","user_type","sum(page_user_count) as page_user_count","sum(play_user_count) as play_user_count","sum(play_count) as play_count","sum(duration) as duration");
		qw.groupBy("t_date","parent_column_id","user_type");
		QueryConditionsUtils.formatOrderBy(qw,orderby,null);
		Page<AppAreaVisitCountDay> pg =new Page(1,10,true);
		ResultPage page =ResultPage.fromMybatisPlusPage(iAppAreaVisitCountDayService.page(pg,qw));
		System.out.println();
	}

}
