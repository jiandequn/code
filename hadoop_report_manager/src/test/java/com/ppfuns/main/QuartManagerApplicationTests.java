package com.ppfuns.main;

import org.apache.xmlbeans.impl.xb.xsdschema.PatternDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QuartManagerApplicationTests {

	@Test
	public void contextLoads() {
		//String pattern = "^.*eventsType=(.*);eventsName=(.*);area=(.*);mac=(.*);sn=(.*);userId=(.*);ca=(.*);userType=([0-9A-Za-z]{1,3});parentColumnId=([0-9]+);(.*);createTime=(.*):END";
		String pattern = "^.*Collection events:eventsType=(.*);eventsName=(.*);area=(.*);mac=(.*);sn=(.*);userId=(.*);ca=(.*);userType=(OTT|VOD|ott|vod);parentColumnId=([0-9]+);(.*);createTime=(.*):END$";
		// String pattern = "^.*eventsType=(.*);mac=(.*);sn=(.*);userId=(.*);userType=([VODvod12]{1,3});(.*);createTime=(.*):END";
		Pattern inputPattern = Pattern.compile(pattern, Pattern.DOTALL
				+ (false ? Pattern.CASE_INSENSITIVE : 0));
		String text = "2019-11-01 00:59:53.665 INFO [resin-port-9003-55759][CommonAuthService.java:162] - Collection events:eventsType=COLUMN_TYPE_CONTENT;eventsName=内容二级分类;area=0;mac=mac;sn=sn;userId=uid;ca=null;userType=ott;parentColumnId=13187;columnId=14323;nowSpm=13187.PAGE_MOVIE.14323.0.1587456691382;afterSpm=13187.PAGE_MOVIE.14323.0.1587456675818;columnCodeName=电影Tab;pos=PAGE_MOVIE;posName=电影Tab;createTime=2020-04-21 16:11:31:END";
		//String text = "2019-11-01 00:59:53.665 INFO [resin-port-9003-55759][CommonAuthService.java:162] - Collection events:eventsType=auth_product;mac=208B3774BB30;sn=12022160605760208B3774BB30;userId=12050066;userType=VOD;contentId=13619;contentType=1;parentColumnId=6029;code=S100000;message=鉴权通过;operateType=auth_product;createTime=2019-11-01 00:59:53:END";
		Matcher m = inputPattern.matcher(text);
		System.out.println(" dsfsdf="+m.groupCount()+";sdfsd"+m.matches());
		for(int i=0;i<m.groupCount();i++){
			System.out.println(m.group(i+1));
		}
	}

}
