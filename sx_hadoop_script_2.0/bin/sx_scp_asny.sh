#! /bin/sh
. /etc/profile
filename=$1
pass=Sxjshyry64#Ed5#
expect -c "
    spawn scp -P 60220 -r $filename root@10.43.127.64:/data/mong_db/sx_python/data/
    expect {
        \"*assword\" {set timeout 300; send \"$pass\r\"; exp_continue;}
        \"yes/no\" {send \"yes\r\";}
    }
expect eof"