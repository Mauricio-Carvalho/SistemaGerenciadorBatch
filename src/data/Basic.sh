#!/usr/bin/ksh
#############################################################################################################
#DXC.technology
#All rights reserved
#Severity     : @@@Severity@@@
#Responsible  : Cards Utility
#Squad        : @@@Squad@@@
#JOB          : @@@Job@@@
#Description  : @@@Description@@@
#Client       : @@@Client@@@
#############################################################################################################
# Changes Logs                                                                                              #
#############################################################################################################
#Release      : 1.0 - Initial version
#Date         : @@@Date@@@
#Author       : @@@Author@@@
#Comments     : @@@Coments@@@
#Ticket       : @@@Ticket@@@
#############################################################################################################

set -x

#############################################################################################################
#                                        COMMON VARIABLES                                                   #
#############################################################################################################

V_JOB=${AUTO_JOB_NAME}                                              # NOME DO JOB NO AUTOSYS
V_HOME=$(echo $0 | awk -F"/" ' { print $NF }')                    # CAMINHO DO SCRIPT
V_PROPERTIES="{V_HOME}/Properties"                                # CAMINHO DO PROPERTIES
V_DATA=$(date +"%d%m%Y")                                          # DATA
V_HORA=$(date +"%H%M%S")                                          # HORA
V_HOST=$(uname -n)                                                # HOSTNAME
V_AUX=$(echo ${V_EXEC} | cut -d . -f1)                            # AUX
V_PROGRAM_ID=`s_progname`                                         # NOME DO SCRIPT SEM EXTENSÃO
V_DATA_HORA=`s_timestamp`                                         # DATA E HORA


#############################################################################################################
#                                        SYSTEM VARIABLES                                                   #
#############################################################################################################

V_DIRETORIO_ORIGEM=""
V_DIRETORIO_DESTINO=""
V_DIRETORIO_BACKUP=""
V_ARQUIVO=""

#############################################################################################################
#                                      FUNCTION TO DATE THE LOG LINES                                       #
#############################################################################################################

function logdata {
  echo "$(date +"%d/%m/%Y-%H:%M:%S") ---: ${1}"
  echo "$(date +"%d/%m/%Y-%H:%M:%S") ---: ${1}" >>${V_LOGFILE}
}


#############################################################################################################
#                                         START OF THE PROCESS                                              #
#############################################################################################################

logdata ""
logdata "=============================================================================================================================================="
logdata "DXC Technology "
logdata "Program name: ${V_PROGRAM_ID} "
logdata "Start Time  : ${V_DATA_HORA}"
logdata "=============================================================================================================================================="
logdata ""
logdata ""
logdata ""


logdata "VERIFICANDO A EXISTENCIA DO DIRETORIO DE ORIGEM"
logdata ""

if [ ! -d ${V_DIRETORIO_ORIGEM} ]
	then
	  logdata "DIRETORIO DE ORIGEM NÃO ENCONTRADO ${V_DIRETORIO_ORIGEM}"
    exit 5
else
  logdata "DIRETORIO DE ORIGEM ENCONTRADO ${V_DIRETORIO_ORIGEM}"
fi

logdata '=============================================================================================================================================='
logdata ""

logdata "VERIFICANDO A EXISTENCIA DO DIRETORIO DE DESTINO"
logdata ""

if [ ! -d ${V_DIRETORIO_DESTINO} ]
	then
	  logdata "DIRETORIO DE DESTINO NÃO ENCONTRADO ${V_DIRETORIO_DESTINO}"
    exit 10
else
  logdata "DIRETORIO DE DESTINO ENCONTRADO ${V_DIRETORIO_DESTINO}"
fi

logdata '=============================================================================================================================================='
logdata ""

logdata "VERIFICANDO A EXISTENCIA DO DIRETORIO DE BACKUP"
logdata ""

if [ ! -d ${V_DIRETORIO_BACKUP} ]
	then
	  logdata "DIRETORIO DE BACKUP NÃO ENCONTRADO ${V_DIRETORIO_BACKUP}"
    exit 15
else
  logdata "DIRETORIO DE BACKUP ENCONTRADO ${V_DIRETORIO_BACKUP}"
fi

logdata '=============================================================================================================================================='
logdata ""

logdata "VERIFICANDO A EXISTENCIA DOS ARQUIVOS"
logdata ""

V_LISTA_ARQUIVOS=$(ls ${V_DIRETORIO_ORIGEM}/${V_ARQUIVO})

if [ ${#V_LISTA_ARQUIVOS[@]} -eq 0 ]
  then
	  logdata "ARQUIVOS ${V_ARQUIVO} NAO ENCONTRADOS"
	  exit 20;
else
  logdata "LISTA DE ARQUIVOS:"
fi

for V_ARQ in ${V_LISTA_ARQUIVOS[@]}
do
	logdata "${V_ARQ}"
done

logdata '=============================================================================================================================================='
logdata ""

logdata "REALIZANDO BACKUP E MOVENDO PARA O DIRETORIO DESTINO"
logdata ""

for V_ARQ in ${V_LISTA_ARQUIVOS[@]}
do

  logdata "${V_ARQ}"

  cp ${V_DIRETORIO_ORIGEM}/${V_ARQ} ${V_DIRETORIO_BACKUP}

  if [ $? -ne 0 ]
  	then
  	  logdata "ERRO AO COPIAR ARQUIVO"
      exit 25
  else
    logdata "ARQUIVO COPIADO COM SUCESSO"
  fi

  mv ${V_DIRETORIO_ORIGEM}/${V_ARQ} ${V_DIRETORIO_DESTINO}

  if [ $? -ne 0 ]
  	then
  	  logdata "ERRO AO MOVER ARQUIVO"
      exit 30
  else
    logdata "ARQUIVO MOVIDO COM SUCESSO"
  fi

  logdata '=============================================================================================================================================='

done

logdata "=========================================================="
logdata ""

exit 0

