#!/usr/bin/ksh
########################################################################
#Program Description
########################################################################
#DXC.technology
#All rights reserved
#Severity     : #@@Severity@@#
#Responsible  : Cards Utility
#Squad        : #@@Squad@@#
#JOB          : #@@Job@@#
#Description  : #@@Description@@#
#Client       : #@@Client@@#
#Date         : #@@Date@@#
########################################################################

set -x

PROGRAM_TO_RUN="#@@PathVersionJava@@# #@@MaxMemoryJava@@# #@@Bin@@#"
ADQUIRNTE="#@@Client@@#"
MODELO="#@@Modelo@@#"
PROPERTIES="#@@Properties@@#"

# First check for mandatory variable CMSAP_ROOT
if [ "$CMSAP_ROOT" = "" ]
then
	clear
	echo '+-----------------------------------------------------+'
	echo '| ERROR:                                              |'
	echo '|        Mandatory variable CMSAP_ROOT is not defined |'
	echo '+-----------------------------------------------------+'
	echo ''
	exit 99
fi

#--------------------------------------
# include common functions
. cms_functions.sh
. cms_xfunctions.sh
#--------------------------------------

#***********************************************************************
#                              LOCAL SETUP
#***********************************************************************
# in the next line, we get the script name without the extension
PROGRAM_ID=`s_progname`
START_TIME=`s_timestamp`
f_set_data_values
CMSAP_DEBUG=${CMSAP_DEBUG:-'OFF'}

# put the submodel indicator: A - acquirer, I - issuer
# if the name has the submodel in the 3rd position, leave it this way
SUB_MODEL=`echo $PROGRAM_ID | cut -c3`

# Default values for some configuration variables, if not already defined
# These values can be overriden by setting them in the CMSAPF_CFG file
CMSAP_BIN=${CMSAP_BIN:-'.'}			# Location of executables
CMSAP_INP=${CMSAP_INP:-'./inp'}			# Input path
CMSAP_OUT=${CMSAP_OUT:-'./out'}			# Output path
CMSAP_POLL_INTERVAL=${CMSAP_POLL_INTERVAL:-'0'}	# Time between tries
CMSAP_POLL_COUNTER=${CMSAP_POLL_COUNTER:-'1'}	# Number of tries before issuing an error

# If log file variable is not defined, use a default value
CMSAP_MAINLOG=${CMSAP_MAINLOG:-$CMSAP_ROOT/log/general/$PROGRAM_ID.log}

# Define name of program output redirection log file
CMSAP_OUTPUTLOG=$CMSAP_OLP/$PROGRAM_ID.log

# Number of programs that can run simultaneously, default is 3
export CMSAP_MAX_SIM_RUN=${CMSAP_MAX_SIM_RUN:=#@@Simultaneous@@#}
export CMSAP_DLY_BTW_RUN=${CMSAP_DLY_BTW_RUN:=#@@Delay@@#}

echo ''
echo '======================================================================='
echo '© DXC Technology Company #@@Date@@#'
echo 'Program name: ' $PROGRAM_ID
echo 'Start Time  : ' $START_TIME
echo '======================================================================='
echo ''

#***********************************************************************
#                            INITIALIZATION
#***********************************************************************
#  - Read configuration files
#  - Set environment variables
#-----------------------------------
# Call function to set particular environment variables
# from all SEV entries from CMSAPF_CFG
f_getconf $PROGRAM_ID
if [ $? != 0 ]
then
	f_showerror $?
fi

f_set_status 'INIT'
f_writelog 'Script started'

trap "f_set_status 'HUP'; f_writelog 'Script ended - HUP'; exit 1" 1
trap "f_set_status 'INT'; f_writelog 'Script ended - INTERRUPTED'; exit 2" 2
trap "f_set_status 'KILL'; f_writelog 'Script ended - KILLED'; exit 15" 15

f_extras

#***********************************************************************
#                          CHECK FOR CONDITIONS
#***********************************************************************
#  - Check for existence of mandatory files (TFE, TIF)
#  - Check for correct ending of previous processes (TTS)
#-----------------------------------
# If this sections is not necessary, set CHECK_PRECONDITIONS to 0
CHECK_PRECONDITIONS=1

# This variable used by function f_check_condition, do not change
CHECK_TERMINATE=0

if [ $CHECK_PRECONDITIONS = 1 ]
then
	f_chk_pre_conditions
fi

#***********************************************************************
#                          LOG START INFORMATION
#***********************************************************************
#  - Write starting data in log files
#  - Generate message files
#-----------------------------------
f_writelog 'Starting the main process'
f_set_status 'START'

#***********************************************************************
#                              MAIN PROCESS
#***********************************************************************
#  - Set parameters
#  - Write log information
#  - Call program
#  - Repeat loop if necessary
#-----------------------------------
FILE_PATTERN=''
FILE_MANDATORY=0
FILE_COUNTER=0

if [ $CMSAP_DEBUG = 'ON' ]
then
	echo 'Setting table parameters'
fi
f_writelog 'Setting table parameters'
f_set_table_parameters $PROGRAM_ID

#-----------------------------------
# Check if this script must be skipped
XS_SKIP=NO
XS_SKIP=`f_check_for_skip $PROGRAM_ID`

#-----------------------------------
# Get all instances from configuration file
XS_PROC_LIST=`f_get_processing_list $PROGRAM_ID`

if [ "$XS_PROC_LIST" = "EMPTY" -o $XS_SKIP = YES ]; then
	# Skip this program
	echo "Nothing to do here"
	f_writelog 'Skipping'
else
	XS_CONTROL_FILE=$CMSAP_ROOT/temp/${PROGRAM_ID}_CONTROL

	#-----------------------------------
	# If control file does not exist, create it
	if [ ! -f $XS_CONTROL_FILE ]; then
		XS_COUNTER=1
		for XS_PAR in $XS_PROC_LIST
		do
			if [ $XS_COUNTER -lt 100 ]; then
				if [ $XS_COUNTER -lt 10 ]; then
					XS_SUFFIX=00$XS_COUNTER
				else
					XS_SUFFIX=0$XS_COUNTER
				fi
			else
				XS_SUFFIX=$XS_COUNTER
			fi
			XS_LINE="${PROGRAM_ID}_${XS_SUFFIX}:$XS_PAR"
			echo $XS_LINE >> $XS_CONTROL_FILE
			XS_COUNTER=`expr $XS_COUNTER + 1`
		done
		chmod 666 $XS_CONTROL_FILE
	fi
	
	#-----------------------------------
	# and generate all necessary scripts
	for XS_LINE in `cat $XS_CONTROL_FILE`
	do
		XS_SCRIPT_NAME=`echo $XS_LINE | cut -f1 -d:`
		XS_PARAMS=`echo $XS_LINE | cut -f2- -d:`
		if [ ! -f $XS_SCRIPT_NAME.sh ]; then
			# Generate the script
			echo "Generating $XS_SCRIPT_NAME"
			echo $PROGRAM_TO_RUN
			f_gen_script $XS_SCRIPT_NAME "$PROGRAM_TO_RUN $ADQUIRENTE $MODELO $PROPERTIES $XS_PARAMS"
		fi
	done

	# Here starts the real launch of parallel execution
	#---------------------------------------------------------------
	# Now lunch all the configured tasks.
	
	XS_FIRSTTIME=1
	PER_START=`f_now`
	#----------------------
	# Loop forever
	while true
	do
		XS_TASKERR=0
		XS_RUNNING=0
		XS_TASKEND=0
		XS_NUMOFTASKS=0
		XS_RUNLIST=''
		XS_ERRLIST=''
		for XS_TASKTORUN in `cat $XS_CONTROL_FILE | cut -f1 -d:`
		do
			XS_TASKSTS=`f_get_task_status $XS_TASKTORUN`
			XS_NUMOFTASKS=`expr $XS_NUMOFTASKS + 1`

			#----------------------
			# Check for new tasks to run
			if [ "$XS_TASKSTS" = "UNDEFINED" ]; then
				if [ $XS_RUNNING -lt $CMSAP_MAX_SIM_RUN ]; then
					XS_RUNNING=`expr $XS_RUNNING + 1`
					XS_RUNLIST="$XS_RUNLIST $XS_TASKTORUN:(new)"
				fi
			fi
			#----------------------
			# Check for task with errors
			if [ "$XS_TASKSTS" = "ERROR" -o "$XS_TASKSTS" = "INT" -o "$XS_TASKSTS" = "HUP" -o "$XS_TASKSTS" = "KILL" ]; then
				if [ $XS_FIRSTTIME = 1 ]; then
					if [ $XS_RUNNING -lt $CMSAP_MAX_SIM_RUN ]; then
						XS_RUNNING=`expr $XS_RUNNING + 1`
						XS_RUNLIST="$XS_RUNLIST $XS_TASKTORUN:(retry)"
					fi
				else
					XS_TASKERR=`expr $XS_TASKERR + 1`
					XS_ERRLIST="$XS_ERRLIST $XS_TASKTORUN"
				fi
			fi
			#----------------------
			# Check for running process
			if [ "$XS_TASKSTS" = "START" ]; then
				XS_RUNNING=`expr $XS_RUNNING + 1`
			fi
			#----------------------
			# Check for ended tasks
			if [ "$XS_TASKSTS" = "END" ]; then
				XS_TASKEND=`expr $XS_TASKEND + 1`
			fi
		done

		#----------------------
		# If all tasks have ended, exit loop
		if [ $XS_TASKEND -ge $XS_NUMOFTASKS ]; then
			break
		fi
		
		#----------------------
		# If any task has errors, log and exit with error
		if [ $XS_TASKERR -gt 0 ]; then
			PER_END=`f_now`
			f_log_performance $PROGRAM_ID $CMSAP_BUSDATE $PER_START $PER_END
			f_set_status 'ERROR'
			f_writelog 'Script ended with error.'
			f_showerror 83 '(' $XS_ERRLIST ')'
		else
			#----------------------
			# If we need to launch programs, do it here.
			for XS_TASKTORUN in $XS_RUNLIST
			do
				XS_RUNSTATUS=`echo $XS_TASKTORUN | cut -f2 -d:`
				XS_TASKTORUN=`echo $XS_TASKTORUN | cut -f1 -d:`
				XS_STARTIME=`date "+%H:%M:%S"`
				echo "@ $XS_STARTIME $XS_TASKTORUN.sh launched $XS_RUNSTATUS"
				f_writelog "nohup $XS_TASKTORUN.sh in background $XS_RUNSTATUS"
				nohup $CMSAP_ROOT/$XS_TASKTORUN.sh >> $CMSAP_OUTPUTLOG &
				sleep $CMSAP_DLY_BTW_RUN
			done
		fi
		XS_FIRSTTIME=0
		sleep 1
	done
	
	PER_END=`f_now`
	f_log_performance $PROGRAM_ID $CMSAP_BUSDATE $PER_START $PER_END

	# At this point, all parameters have been processed correctly.
	for XS_TASKTORUN in `cat $XS_CONTROL_FILE | cut -f1 -d:`
	do
		rm -f $CMSAP_ROOT/$XS_TASKTORUN.sh
	done
	rm -f $XS_CONTROL_FILE
fi

# -----------------------------------------------------------------------
# Further checkings can be done here, as for example, analyzing an output
# file to see if is not empty when it is not supposed to be.
# -----------------------------------------------------------------------

f_post_process

#***********************************************************************
#                         LOG ENDING INFORMATION
#***********************************************************************
#  - Write ending data in log files
#  - Generate message files
#-----------------------------------
f_writelog 'Script ended'
f_set_status 'END'

exit 0
