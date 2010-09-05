#!/bin/sh

./setCp.sh

CP=-cp "$LOCALCLASSPATH"

"$JAVA_HOME/bin/java" $CP net.sf.minuteProject.application.ModelViewGenerator $1

