#!/bin/bash

# run every interval in seconds
timeinterval=5;
echo "time intervall: $timeinterval"

jarfolder='./exec'
envfolder='./env'
logfolder='./log'

echo "####################################"
echo "#  SPRING BOOT STARTER            ##"
echo "####################################"

echo "Folder to deploy=\"$jarfolder\""
echo "Folder envfolder=\"$envfolder\""

echo "#####################################"
echo "#  JAVA RUN  PROPERTIES            ##"
echo "#####################################"
appEnv=($(< "./$envfolder/app.env"))

printf -v app_env ' %s' "${appEnv[@]}" # yields "|1|2|3|4"
app_env=${app_env:1}                  # remove the leading '|'
echo $app_env

echo "#####################################"
echo "#  WATCH FOLDER SPRING BOOT APP    ##"
echo "#####################################"
#chksum1="${chksum1:-new instance}"
filename=""
while [[ true ]]; do
    chksum2=`find $jarfolder -type f -printf "%T@ %p\n" | md5sum | cut -d " " -f 1`;
    #echo $chksum2
	if [[ $chksum1 != $chksum2 ]] ; then 
		
		chksum1="${chksum1:-$chksum2}"
		
       	for entry in "$jarfolder"/*
		do
		  ext=$(basename "$entry" | cut -d. -f2)
		  #echo "estensione: $ext"
		  fbname=$(basename "$entry" | cut -d. -f1)
		  
		  runfile="$jarfolder/$fbname.run"

		  if [[ (${entry: -4} == ".war" || ${entry: -4} == ".jar") && (! -f $runfile) ]]; then
			  commandToExec="java -jar $app_env"

			  
			  fileEnv="./$envfolder/$fbname.env"
			  echo "###################################"
	
			  echo "search for env file $fileEnv"
	
			  echo "###################################"
			  if [ -f $fileEnv ] ; then
					app1Env=($(< $fileEnv))
	
					printf -v app1_env ' %s' "${app1Env[@]}" # yields "|1|2|3|4"
					app1_env=${app1_env:1}                  # remove the leading '|'
					
					echo $app1_env
			
	                commandToExec="${commandToExec} ${app1_env}"
					#echo $commandToExec
			  fi
	 		  echo "###################################"
			  baseNameWar=`basename $entry`
			  
			 # prima di eseguire controlla la directory dei log
			  
			  printf -v date '%(%Y-%m-%d.%H%M%S)T' -1 
			  echo "current execution date:  $date"
			  logFile="${logfolder}/${fbname}.${date}.log"
			  
	          commandToExec="$commandToExec $entry"
			  
			  echo "executing $commandToExec > $logFile"
			 
			  $commandToExec >$logFile&
			  
	          touch $runfile > $runfile
				
			  echo "### wait for next entry ###"
		fi
		done
    fi
    #echo "chksum2 = $chksum2  chksum1 =$chksum1";
    sleep $timeinterval;
done