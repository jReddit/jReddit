#!/bin/bash
#
# In order to run these examples, your classpath needs to include:
# ../deps/*.jar
# ../dist/*.jar
# 
# Either manually configure your classpath 
# Or you can use this script.
#
# Usage:
#   ./runExample.sh <example-class> [params ...]
#
# E.g.
#   jReddit/examples$ ./runExample.sh SubmissionLister
#

if [ $# -lt 1 ]; then
    echo "  Usage:  "
    echo "  ./runExample.sh <example-class> [params ...] "
    echo ""
    echo "  Specify an example class name. E.g. SubmissionLister "
    echo "  [jReddit/examples$] ./runExample.sh SubmissionLister foo bar baz "
    exit 1
fi

#
# Get the "examples" dir path
#
basedir=`dirname $0`

export CLASSPATH=`ls ./$basedir/../deps/*.jar | tr '\n' ':'`
export CLASSPATH=$CLASSPATH:`ls ./$basedir/../dist/*.jar | tr '\n' ':'`

# echo "INFO: CLASSPATH is $CLASSPATH"

java -classpath $CLASSPATH:$basedir/build $@

