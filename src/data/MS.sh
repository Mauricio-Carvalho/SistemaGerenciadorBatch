#!/usr/bin/ksh
########################################################################
#Program Description
########################################################################
#DXC.technology
#All rights reserved
#Severity     : @@@Severity@@@
#Responsible  : Cards Utility
#Squad        : @@@Squad@@@
#JOB          : @@@Job@@@
#Description  : @@@Description@@@
#Client       : @@@Adquirente@@@
#Date         : @@@Date@@@
########################################################################

set -x

. /appl/CMS/cmsacqr/.profile.autosys

TASK_TO_RUN=@@@ap@@@  # name of the .task here

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

# Now test for the directory structure.
MISSINGDIR=0
ERRORDESC=""
if [ ! -d $CMSAP_ROOT ]
then
    MISSINGDIR=`expr $MISSINGDIR + 1`
    ERRORDESC="$ERRORDESC \t$CMSAP_ROOT\n"
fi
if [ ! -d $CMSAP_ROOT/cfg ]
then
    MISSINGDIR=`expr $MISSINGDIR + 1`
    ERRORDESC="$ERRORDESC \t$CMSAP_ROOT/cfg\n"
fi
if [ ! -d $CMSAP_ROOT/log ]
then
    MISSINGDIR=`expr $MISSINGDIR + 1`
    ERRORDESC="$ERRORDESC \t$CMSAP_ROOT/log\n"
fi
if [ ! -d $CMSAP_ROOT/log/general ]
then
    MISSINGDIR=`expr $MISSINGDIR + 1`
    ERRORDESC="$ERRORDESC \t$CMSAP_ROOT/log/general\n"
fi
if [ ! -d $CMSAP_ROOT/msg ]
then
    MISSINGDIR=`expr $MISSINGDIR + 1`
    ERRORDESC="$ERRORDESC \t$CMSAP_ROOT/msg\n"
fi
if [ ! -d $CMSAP_ROOT/temp ]
then
    MISSINGDIR=`expr $MISSINGDIR + 1`
    ERRORDESC="$ERRORDESC \t$CMSAP_ROOT/temp\n"
fi
if [ $MISSINGDIR != 0 ]
then
    clear
    echo '+-----------------------------------------------------+'
    echo 'ERROR:'
    echo "The following $MISSINGDIR directories does not exist:"
    echo $ERRORDESC
    echo '+-----------------------------------------------------+'
    echo ''
    exit 90
fi

#--------------------------------------
# include common functions
. cms_functions.sh
#--------------------------------------

#***********************************************************************
#                              LOCAL SETUP
#***********************************************************************
# in the next line, we get the script name without the extension
PROGRAM_ID=`s_progname`
START_TIME=`s_timestamp`
export DB_USER=""
export DATABASE=""
export CMSAPA_BUSDATE=""

# put the submodel indicator: A - acquirer, I - issuer
# if the name has the submodel in the 3rd position, leave it this way
export SUB_MODEL=`echo $PROGRAM_ID | cut -c3`

# next functions sets variables CMSAP_BUSDATE, DBUSER and DATABASE
f_set_data_values

CMSAP_DEBUG=${CMSAP_DEBUG:-'OFF'}

# Default values for some configuration variables, if not already defined
# These values can be overriden by setting them in the CMSAPF_CFG file
export CMSAP_BIN=${CMSAP_BIN:-'.'}          # Location of executables
export CMSAP_INP=${CMSAP_INP:-'./inp'}          # Input path
export CMSAP_OUT=${CMSAP_OUT:-'./out'}          # Output path
export CMSAP_DONE=${CMSAP_DONE:-'./done'}           # Done path
export CMSAP_POLL_INTERVAL=${CMSAP_POLL_INTERVAL:-'0'}  # Time between tries
export CMSAP_POLL_COUNTER=${CMSAP_POLL_COUNTER:-'1'}    # Number of tries before issuing an error

TEMPVAR=`f_get_task_status $PROGRAM_ID`
if [ $TEMPVAR = INIT ] || [ $TEMPVAR = START ]
then
    echo '******************************************************'
    echo "$PROGRAM_ID is already running."
    echo '******************************************************'
    exit 1
fi

echo ''
echo '======================================================================='
echo 'DXC Technology. (c) 2019'
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
# If last run was successful, empty messages folder
RESUMING=0

MYSTATUS=`f_get_task_status $PROGRAM_ID`
if [ $MYSTATUS = DONE ] || [ $MYSTATUS = UNDEFINED ]
then
    if [ $CMSAP_DEBUG = 'ON' ]
    then
        echo 'Erasing message folder'
    fi
    rm -f $CMSAP_ROOT/msg/O_@@@MS@@@
    rm -f $CMSAP_ROOT/msg/O_@@@TS@@@
    
    f_writelog 'Erasing message folder'
	echo $CMSAPA_BUSDATE > $CMSAP_ROOT/cfg/CMSAPF_IBD
    echo $PROGRAM_ID > $CMSAP_ROOT/cfg/CMSAPF_IAS
    RESUMING=0
else
    if [ $CMSAP_DEBUG = 'ON' ]
    then
        echo 'Recovering... message folder kept intact'
    fi
	CMSAPA_BUSDATE=`cat $CMSAP_ROOT/cfg/CMSAPF_IBD`
    RESUMING=1
fi

# Create date log directory if it doesn't exist
if [ ! -d $CMSAP_ROOT/log/$CMSAPA_BUSDATE ]
then
	mkdir $CMSAP_ROOT/log/$CMSAPA_BUSDATE
    if [ $? != 0 ]
    then
        clear
        echo '+-----------------------------------------------------+'
        echo 'ERROR:'
		echo 'Could not create' "$CMSAP_ROOT/log/$CMSAPA_BUSDATE"
        echo '+-----------------------------------------------------+'
        echo ''
        exit 93
    fi
	chmod 777 $CMSAP_ROOT/log/$CMSAPA_BUSDATE
fi

# If log file variable is not defined, use a default value
export CMSAP_MAINLOG=$CMSAP_ROOT/log/general/ml${SUB_MODEL}_${CMSAPA_BUSDATE}.log

export CMSAP_OLP="$CMSAP_ROOT/log/$CMSAPA_BUSDATE"

#-----------------------------------------------------------------------
# Define name of program output redirection log file
CMSAP_OUTPUTLOG=$CMSAP_OLP/$PROGRAM_ID.log

# Call function to set particular environment variables
# from all SEV entries from CMSAPF_CFG
f_getconf $PROGRAM_ID
if [ $? != 0 ]
then
    f_showerror $?
fi
#-----------------------------------------------------------------------

f_set_status 'INIT'
f_writelog 'Script started'
if [ $RESUMING = 1 ]
then
    f_writelog 'Resuming previous run'
fi

trap "f_set_status 'HUP'; f_writelog 'Script HUP'; exit" 1
trap "f_set_status 'INT'; f_writelog 'Script INTERRUPED'; exit" 2
trap "f_set_status 'KILL'; f_writelog 'Script KILLED'; exit" 15

f_extras

#***********************************************************************
#                          CHECK FOR CONDITIONS
#***********************************************************************
#  - Check for existence of mandatory files (TFE, TIF)
#  - Check for correct ending of previous processes (TTS)
#-----------------------------------

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
#-----------------------------------
if [ $CMSAP_DEBUG = 'ON' ]
then
    echo 'Setting table parameters'
fi
f_writelog 'Setting table parameters'
f_set_table_parameters $PROGRAM_ID
    
#---------------------------------------------
# If necessary, move files here
# mv <source files> <destination location>
#---------------------------------------------

# Log start of processing of file nnn
f_writelog 'Running pc_run' $TASK_TO_RUN

if [ $CMSAP_DEBUG = 'ON' ]
then
    echo 'Executing pc_run' $TASK_TO_RUN
fi

# Call the corresponding program
pc_run $TASK_TO_RUN
END_STATUS=$?

# Check final status
if [ $END_STATUS != 0 ]
then
    f_set_status 'ERROR'
    f_showerror $END_STATUS 'Program pc_run' $TASK_TO_RUN
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
f_set_status 'DONE'

f_move_processed

exit 0

