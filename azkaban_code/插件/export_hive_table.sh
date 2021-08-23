#！/bin/bash
#验证数据库是否存在
if [[ ! -x hive ]]; then
   echo -e "\033[31m无执行hive权限功能\033[0m"
   exit 1;
fi
echo "正在加载hive数据库信息。。。"
db_arr=(`hive -e "show databases;" 2>/dev/null`)
echo "可选择数据库:${db_arr[*]}"
DATABASE=${db_arr[0]}
function show_database(){
   hive -e "show databases;"  2>/dev/null
}
function use_database(){
  local flag=0
  for db in ${db_arr[@]}
  do
    if [ "$db" = "$1" ];then
       echo "正在切换数据库"
       DATABASE=$1
       flag=1
    fi
  done
  if [[ $flag -eq 0 ]]; then
    echo -e "\033[31m数据库不存在，请重新设置\033[0m"
  fi
}
function show_cli(){
   local arr=($@)
   local param_len=${#arr[@]}
   if [[ $param_len -eq 1 ]]; then
              if [[ "${arr[0]}" = "db" ]]; then
                show_database
              elif [[ "${arr[0]}" = "tables" ]]; then
                show_tables  
              else  
                 echo -e "\033[31m错误输入，请重新输入，输入格式\033[0m" 
              fi
    elif [[ $param_len -eq 2 ]]; then
            if [ "${arr[0]}" = "table" ] && [ ! -z "${arr[1]}" ]; then
                  show_create_table ${arr[1]}
            else
              echo -e "\033[31m参数输入错误\033[0m"
            fi
          else
            echo -e "\033[31m参数输入错误\033[0m"  
    fi
}
function show_tables(){
  hive --database=${DATABASE} -e "show tables;" 2>/dev/null
}
function show_create_table(){
   hive -S --database=${DATABASE} -e "show create table $1;"
}
function dump_table(){
   local arr=($@)
   local all_struct_table=0
   local all_data_table=0
   local struct_tables=""
   local data_tables=""
   local outfile=""
   local db_tmp=${DATABASE}
   for p in ${arr[@]}; do
     if [[ "$p" = "--all_struct_table" ]]; then
        all_struct_table=1
     elif [[ "$p" = "--all_data_table" ]]; then
        all_data_table=1
     elif [[ "$p" =~ ^struct_tables=.* ]]; then
        struct_tables=${p:14}
     elif [[ "$p" =~ ^data_tables=.* ]]; then
        data_tables=${p:12}
     elif [[ "$p" =~ ^out=.* ]]; then
          outfile=${p:4}
          if [ ! -d "${outfile%/*}" ] && [ "$outfile" != "${outfile%/*}" ] ; then
              echo -e "\033[31m文件目录:${outfile%/*} 不存在\033[0m"
              return 0
          fi
     elif [[ "$p" =~ ^database=.* ]]; then
            db_temp=${p:9}  
     else
        echo -e "\033[31m错误参数 $p \033[0m"  
        return 0
     fi
   done
   if [ ! -z "$struct_tables" ] && [ $all_struct_table -eq 1 ]; then
          echo -e "\033[31m不能--all_struct_table 参数与 struct_tables 搭配使用\033[0m"
          return 0
   fi
   if [ ! -z "$data_tables" ] && [ $all_data_table -eq 1 ]; then
          echo -e "\033[31m不能--all_data_table 参数与 data_tables 搭配使用\033[0m" 
          return 0
   fi
   if [[ -z "$outfile" ]]; then
       echo -e "\033[31m存储参数out=不能为空\033[0m"
       return 0
   fi
   if [[ ! -f "$outfile" ]]; then
     touch $outfile
   else
      > $outfile  
   fi
   if [ $? -ne 0 ] || [ ! -w "$outfile" ]; then
     echo "执行错误或没有写权限"
     return 0;
   fi
   #获取表
   all_tables_arr=(`hive --database=${DATABASE} -e "show tables;"`)
   local struct_arr
   local data_arr
   if [[ $all_struct_table -eq 1 ]]; then
     struct_arr=${all_tables_arr[@]}
   fi
   if [[ $all_data_table -eq 1 ]]; then
     data_arr=${all_tables_arr[@]}
   fi
   #校验表
   if [ ! -z "$struct_tables" ]; then
     local part_struct_tables=(${struct_tables//\,/ })
     local error_tab=""
     for i in ${part_struct_tables[@]}; do
        for k in ${all_tables_arr[@]}; do
          if [ "$i" = "$k" ]; then
            break 2
          fi
        done
        error_tab="$error_tab $i"
     done
     if [[ ! -z "$error_tab" ]]; then
       echo -e "\033[31m struct_tables的错误表：$error_tab,在数据库$db_tmp中不存在\033[0m"
       return
     fi
     struct_arr=${part_struct_tables[@]}
   fi
   if [ ! -z "$data_tables" ]; then
     local part_data_tables=(${data_tables//\,/ })
     local error_tab=""
     for i in ${part_data_tables[@]}; do
        for k in ${all_tables_arr[@]}; do
          if [ "$i" = "$k" ]; then
            break 2
          fi
        done
        error_tab="$error_tab $i"
     done
     if [[ ! -z "$error_tab" ]]; then
       echo -e "\033[31mdata_tables的错误表：$error_tab,在数据库$db_tmp中不存在\033[0m"
       return
     fi
     data_arr=${part_data_tables[@]}
   fi
   if [ -z "$struct_arr" ] && [ -z "$data_arr" ]; then
     echo -e "\033[31m缺失导出参数，无法确认是导出数据还是格式\033[0m"
     return
   fi
   #表结构输出到文件
   local exec_script=""
   if [[ ! -z "$struct_arr" ]]; then
     for i in ${struct_arr[@]}; do
        #hive -S --database=${db_tmp} -e "show create table ${i};" >> $outfile
        exec_script="${exec_script}show create table ${i};"
        echo ";" >> ${outfile}
     done
   fi
   #表数据输出到文件
   if [[ ! -z "$data_arr" ]]; then
     for i in ${data_arr[@]}; do
        #"select concat('insert into test(id,name)values(\"',id,'\",\"',name,'\");') from test;"
      
        fields_arr=(`hive --database=${DATABASE} -e "desc $i;" |awk '{print $1}'`)
        fields_str="${fields_arr[@]}"
        before_f="${fields_str//[[:space:]]/,}"
        after_f="${fields_str//[[:space:]]/,'\",\"',}"
        query_sql="select concat('insert into $i($before_f)values(\"',$after_f,'\");') from $i;"
        echo "查询语句:$query_sql"
        #echo "------------- 表$i的数据 开始-----------------" >> $outfile
        #hive -S --database=${db_tmp} -e "$query_sql;" >> $outfile
        exec_script="${exec_script} select \"------------- 表$i的数据 开始-----------------\";"
        exec_script="${exec_script} ${query_sql}"
        exec_script="${exec_script} select \"------------- 表$i的数据 结束-----------------\";"
        #echo "------------- 表$i的数据 结束-----------------" >> $outfile
     done
   fi

   if [[ ! -z "$exec_script" ]]; then
      hive -S --database=${db_tmp} -e "$exec_script;" >> $outfile
      if [[ ! -z "$data_arr" ]]; then
           #检查是否带有NULL行的数据
         cat $outfile||grep -n "^NULL$"
         err_data=(`cat $outfile |grep -n "^NULL$"`)
         if [[ ! -z $err_data ]]; then
              echo -e "\033[31m存在异常导出数据信息；造成原因为hive表中数据存在NULL的值，无法通过concat表达式生成插入语句\033[0m"
              for i in ${err_data[@]}; do
                echo -e " \033[31m存在异常导出数据信息在行 $i \033[0m"
              done
         fi
      fi
   fi


}


function source_file(){
  local arr=($@)
  local param_len=${#arr[@]}
  local flag=1
  db_temp=$DATABASE
  for sql_file in ${arr[@]}
  do
     if [ ! -f "$sql_file" ]; then
        if [[ "$sql_file" =~ "^database=.*" ]]; then
          db_temp=${${arr[0]}:3}
          continue
        fi
        flag=0
        echo "文件$sql_file不存在"
     fi
  done
  if [[ $flag -eq 1 ]]; then
    for sql_file in ${arr[@]}; do
      hive --database=${db_temp} -f $sql_file
    done
  fi

}
function help(){
  echo "命令参数："
  echo " show 显示信息包括数据库列表，表列表，表结构,可选参数如下："
  echo "   db 显示数据库列表信息；show db"
  echo "   tables 显示数据库下表列表；show tables"
  echo "   table <tableName> 显示数据库列表信息；show table test"
  echo " "
  echo " use  切换数据库；如 use default"
  echo " "
  echo " dump 导出hive数据结构或数据"
  echo "   --all_struct_table    启用导出所在数据库所有表结构信息"
  echo "   --all_data_table      启用导出所在数据库所有表数据信息"
  echo "   data_tables=<t1,t2>   启用导出指定表的数据信息"
  echo "   struct_tables=<t1,t2> 启用导出指定表结构信息"
  echo "   database=<dbname>     指定导出数据库"
  echo "   out=<file>            指定导出输出文件"
  echo "   注意：--all_struct_table和struct_tables不能一起使用，--all_data_table和data_tables不能一起使用"
  echo "   例子：dump database=testdb --all_struct_table data_tables=t1,t2 out=/home/app/1.sql   #导出数据库testdb下所有表结构，并且导出表t1,t2的数据，输出到/home/app/1.sql 文件"
  echo " "
  echo " exit|quit  退出命令窗口"
}
echo "欢迎来到hive数据库导出平台"
echo "如需使用命令，请输入help 查看"
echo -n "数据库 $DATABASE >"
while read hive_value
do

  if [ ! -z "$hive_value" ];then
        param_arr=(${hive_value})
        key="${param_arr[0]}"
        unset param_arr[0]
        if [[ "$key" = "show" ]]; then
          show_cli  "${param_arr[@]}"
        elif [[ "$key" = "use" ]]; then
          use_database "${param_arr[@]}"
        elif [[ "$key" = "dump" ]]; then
          dump_table "${param_arr[@]}"
        elif [[ "$key" = "source" ]]; then
          source_file "${param_arr[@]}"
        elif [ "$key" = "exit" ] || [ "$key" = "quit" ]; then
            exit 1 
        elif [[ "$key" = "help" ]]; then
            help    
        else
          echo -e "\033[31m命令无法识别\033[0m"       
        fi
  fi
  echo -n "数据库 $DATABASE >"
done

