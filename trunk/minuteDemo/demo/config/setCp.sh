#!/bin/sh

MP_LIB=../../../minuteKernel/lib
#MP_LIB=../../application/lib
LOCALCLASSPATH=
for i in `ls $MP_LIB/*.jar` `ls $MP_LIB/**/*.jar`
do
  LOCALCLASSPATH=${LOCALCLASSPATH}:${i}
done
export $LOCALCLASSPATH
