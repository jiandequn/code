#!/bin/bash
yarn_queue_dir=tmp/${dateType}
yarn_queue=default
function init_yarn_queue(){
	if [[ ! -z $yarn_queues ]]; then
		queue_arr=(${yarn_queues//\,/ })
		if [[ ! -z "$yarn_queue_weight" ]]; then
			if [ ! -d "$yarn_queue_dir" ];then
				mkdir -p $yarn_queue_dir
				touch $yarn_queue_dir/queue
				touch $yarn_queue_dir/queue.lock
			fi
			(
				flock -w 3 200
				> $yarn_queue_dir/queue
				for queue_key in ${queue_arr[*]}
				do
					echo "0" >>$yarn_queue_dir/queue
				done

			) 200>$yarn_queue_dir/queue.lock
		fi
	fi
}

function poll_yarn_queue(){
	#获取队列数据
	if [ -z $yarn_queues ]; then
		yarn_queue="default"
	else
		queue_arr=(${yarn_queues//\,/ })
		if [[ -z "$yarn_queue_weight" ]]; then
			echo "获取随机数"
			random_index=$(($RANDOM%${#queue_arr[@]}))
			yarn_queue=${queue_arr[$random_index]}
			echo "获取随机数$yarn_queue"
		else
		    if [ ! -f "$yarn_queue_dir/queue" ];then
		        init_yarn_queue
		    fi
			(
				flock -xw 5 200
				echo $yarn_queue_dir/queue
				num_arr=($(awk '{print $1}' $yarn_queue_dir/queue ))
				echo "数组${num_arr[@]}"
				min_num=${num_arr[0]}
				min_index=0;
				index_len=$((${#num_arr[@]}-1))
				for num_index in $(seq 1 $index_len)
				do
					if [ $min_num -gt ${num_arr[$num_index]} ]; then
						min_index=$num_index
						min_num=${num_arr[$num_index]}
					fi
				done
				# 找到最小的 ()代码块内变量无法全局
				# yarn_queue=${queue_arr[$min_index]}
				echo ${queue_arr[$min_index]} > $yarn_queue_dir/queue.name
				> $yarn_queue_dir/queue
				echo "最小位置$min_index,值$min_num,队列：${queue_arr[$min_index]}"
				for num_index in $(seq 0 $index_len)
				do
					if [ $num_index -eq $min_index ]; then
						echo "$(($min_num+1))">>$yarn_queue_dir/queue
					else
						echo ${num_arr[$num_index]} >> $yarn_queue_dir/queue
					fi
				done
			) 200>$yarn_queue_dir/queue.lock
			yarn_queue=`head -n 1 $yarn_queue_dir/queue.name`
		fi
	fi
}
function put_yarn_queue(){
	if [ ! -z $yarn_queues ]; then
		(
			flock -w 5 200
			queue_arr=(${yarn_queues//\,/ })
			if [[ ! -z "$yarn_queue_weight" ]]; then
			    echo "归入列表：$yarn_queue"
				update_index=0;
				for k in ${queue_arr[*]}
				do
					if [ "$k" = "$yarn_queue" ];then
						break;
					fi
					update_index=$(($update_index+1))
				done
				num_arr=(`cat $yarn_queue_dir/queue`)
				index_len=$((${#num_arr[@]}-1))
				> $yarn_queue_dir/queue
				for num_index in $(seq 0 $index_len)
				do
					c_num=${num_arr[$num_index]}
					if [ $num_index -eq $update_index ]; then
						echo $(($c_num-1)) >> $yarn_queue_dir/queue
					else
						echo $c_num >> $yarn_queue_dir/queue
					fi
				done
			fi

		) 200>$yarn_queue_dir/queue.lock
	fi
}