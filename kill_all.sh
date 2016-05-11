echo "--------------Kill All------------"
for i in `ps -ax |grep -v "grep" |grep "/stlb/load" | awk '{print $1}'` ;  do kill -9 $i ; done
for i in `ps -ax |grep -v "grep" |grep "/stlb/site" | awk '{print $1}'` ;  do kill -9 $i ; done

