#!/usr/bin/ksh
########################################################################
#Program Description
########################################################################
#DXC.technology
#All rights reserved
#Severity     : @@@Severity@@@
#Responsible  : Cards Utility -
#Squad        : @@@Squad@@@
#JOB          : @@@Job@@@
#Description  : @@@Description@@@
#Client       : @@@Client@@@
#Date         : @@@Date@@@
########################################################################

set -x

PROGRAM_TO_RUN=@@@bin@@@
ADQUIRENTE=@@@Client@@@
MODELO="exemple"
PROPERTIES="/appl/CMS/cmsap/script/Adquirente/Canais/Portal/Properties/gerador_arquivos_exemple_acqio.properties"


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
CMSAP_BIN=${CMSAP_BIN:-'.'}         # Location of executables
CMSAP_INP=${CMSAP_INP:-'./inp'}         # Input path
CMSAP_OUT=${CMSAP_OUT:-'./out'}         # Output path
CMSAP_POLL_INTERVAL=${CMSAP_POLL_INTERVAL:-'0'} # Time between tries
CMSAP_POLL_COUNTER=${CMSAP_POLL_COUNTER:-'1'}   # Number of tries before issuing an error

# If log file variable is not defined, use a default value
CMSAP_MAINLOG=${CMSAP_MAINLOG:-$CMSAP_ROOT/log/general/$PROGRAM_ID.log}

# Define name of program output redirection log file
CMSAP_OUTPUTLOG=$CMSAP_OLP/$PROGRAM_ID.log

echo 'Command  : ' $PROGRAM_TO_RUN

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
# Call function to set particular environment variables
# from all SEV entries from CMSAPF_CFG
f_getconf $PROGRAM_ID
if [ $? != 0 ]
then
    f_showerror $?
fi

f_set_status 'INIT'
f_writelog 'Script started'

trap "f_set_status 'HUP'; f_writelog 'Script ended - HUP'; exit" 1
trap "f_set_status 'INT'; f_writelog 'Script ended - INTERRUPTED'; exit" 2
trap "f_set_status 'KILL'; f_writelog 'Script ended - KILLED'; exit" 15

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

if [ "$XS_SKIP" = "YES" ]; then
    echo 'Skipping because of configuration'
    f_writelog 'Skipping because of configuration.'
else
        f_get_pattern $PROGRAM_ID
        
        if [ $? != 0 ]
        then
            f_set_status 'ERROR'
            f_showerror $?
        fi
        
        # Log start of processing of file nnn
        f_writelog 'Running ' $PROGRAM_TO_RUN
        
        if [ $CMSAP_DEBUG = 'ON' ]
        then
            echo 'Executing' $CMSAP_BIN/$PROGRAM_TO_RUN
        fi
        
        PER_START=`f_now`
        # Call the corresponding program
        /app/jvm/jdk8/bin/java -jar $CMSAP_BIN/$PROGRAM_TO_RUN $ADQUIRENTE $MODELO $PROPERTIES >> $CMSAP_OUTPUTLOG 2>&1
        END_STATUS=$?
        
        # Check final status
        if [ $END_STATUS != 0 ]
        then
            f_set_status 'ERROR'
            f_showerror $END_STATUS 'Program: ' $CMSAP_BIN/$PROGRAM_TO_RUN
        fi
        
        # Check final status
        if [ $END_STATUS != 0 ]
        then
            f_set_status 'ERROR'
            f_showerror $END_STATUS 'Program: ' $CMSAP_BIN/$PROGRAM_TO_RUN
        fi
        PER_END=`f_now`
        f_log_performance $PROGRAM_ID $CMSAPA_BUSDATE $PER_START $PER_END
        
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
