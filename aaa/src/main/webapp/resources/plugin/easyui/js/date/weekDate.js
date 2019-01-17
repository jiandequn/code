(function(){
	function createWeekDate(date){
			var myWeekDate = new Date();
			this.myYear = myWeekDate.getFullYear();//年
			this.myMonth = myWeekDate.getMonth()+1;//月
			this.myDate = myWeekDate.getDate();//日
			this.content;
			
			this.myDay = myWeekDate.getDay();//星期
			
			var mydate = getYearMonthDate(this.myYear,this.myMonth,this.myDate,this.myDay).split(",");
			var weekDate = new StringBuffer();
			for(var i=0;i<mydate.length-1;i++){
				weekDate.append('<option value="'+mydate[i]+'" >').append(mydate[i]).append("</option>");
			}
			this.content = document.getElementById(date["targetId"]).innerHTML=weekDate.toString();
			return this;
	}
	window['_G']['createWeekDate'] = createWeekDate;
})();

var _week = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
//获得这个月共有多少天
function getDatesByMonth(year_,month_){
	if(month_==1||month_==3||month_==5||month_==7||month_==8||month_==10||month_==12){
		return 31;
	}else if(month_==2){
		if ((year_ % 4 == 0 && year_ % 100 != 0) || year_ % 400 == 0) {
			return 29;
		} else {
			return 28;
		}	
	}else{
		return 30;
	}
}

function getYearMonthDate(year_,month_,date_,day_){
	var weekDate = new StringBuffer();
	
		if(day_==0){//星期天
			for(var j=0;j<=6;j++){
				weekDate.append(changeDate(year_,month_,date_-j,day_-j)).append(",");
			}
		}else if(day_==1){//星期一
			for(var j=0;j<=6;j++){
				weekDate.append(changeDate(year_,month_,date_+j,day_+j)).append(",");
			}
		}else if(day_==2){//星期二
			for(var j=day_-1;j>=0;j--){
				weekDate.append(changeDate(year_,month_,date_-j,day_-j)).append(",");
			}
			for(var j=1;j<=7-day_;j++){
				weekDate.append(changeDate(year_,month_,date_+j,day_+j)).append(",");
			}
		}else if(day_==3){//星期三
			for(var j=day_-1;j>=0;j--){
				weekDate.append(changeDate(year_,month_,date_-j,day_-j)).append(",");
			}
			for(var j=1;j<=7-day_;j++){
				weekDate.append(changeDate(year_,month_,date_+j,day_+j)).append(",");
			}
		}else if(day_==4){//星期四
			for(var j=day_-1;j>=0;j--){
				weekDate.append(changeDate(year_,month_,date_-j,day_-j)).append(",");
			}
			for(var j=1;j<=7-day_;j++){
				weekDate.append(changeDate(year_,month_,date_+j,day_+j)).append(",");
			}
		}else if(day_==5){//星期五
			for(var j=day_-1;j>=0;j--){
				weekDate.append(changeDate(year_,month_,date_-j,day_-j)).append(",");
			}
			for(var j=1;j<=7-day_;j++){
				weekDate.append(changeDate(year_,month_,date_+j,day_+j)).append(",");
			}
		}else if(day_==6){//星期六
			for(var j=day_-1;j>=0;j--){
				weekDate.append(changeDate(year_,month_,date_-j,day_-j)).append(",");
			}
			for(var j=1;j<=7-day_;j++){
				weekDate.append(changeDate(year_,month_,date_+j,day_+j)).append(",");
			}
		}
	
	return weekDate.toString();
}

function changeDate(year_,month_,date_,day_){
	var lastMonthDays=0;
	//初始化值
	this.mydate = date_;
	this.myMonth =month_;
	this.myYear = year_;
	
	if(month_-1<=0){
		lastMonthDays = getDatesByMonth(year_-1,12);
	}else{
		lastMonthDays = getDatesByMonth(year_,month_-1);
	}
	var currentMonthDays = getDatesByMonth(year_,month_);
	
//	var nextMonthDays = 0;
//	if(month_+1>=12){
//		nextMonthDays = getDatesByMonth(year_+1,1);
//	}else{
//		nextMonthDays = getDatesByMonth(year_,month_);
//	}
	
	
	if(date_>currentMonthDays){
		this.mydate = date_-currentMonthDays;
		if(month_+1>12){
			this.myMonth = 1;
			this.myYear = year_+1;
		}else{
			this.myMonth = month_+1;
		}
	}else if(date_<1){
		this.mydate = lastMonthDays+date_;
		if(month_-1<1){
			this.myMonth = 12;
			this.myYear = year_-1;
		}else{
			this.myMonth = month_-1;
		}
	}
	this.myMonth = this.myMonth<10 ? "0"+this.myMonth:this.myMonth;
	this.mydate = this.mydate<10 ? "0"+this.mydate:this.mydate;
	return new StringBuffer().append(this.myYear).append("-").append(this.myMonth).append("-").append(this.mydate).toString();
}