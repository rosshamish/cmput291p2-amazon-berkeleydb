
SHELL := /bin/bash

ifndef JUNIT_PATH
	export JUNIT_PATH=/usr/share/java/junit4.jar
endif
export CLASSPATH=$CLASSPATH:.:/usr/share/java/db.jar:$(JUNIT_PATH)
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/oracle/lib

all: build1 build2 build3
	cp src/com/cmput291p2/group2/Phase2/scripts/break.pl .
	@printf '\nBuild complete.\n\n'
	
	@printf 'Usage:\n'
	@printf '$$ export CLASSPATH=$$CLASSPATH:.:/usr/share/java/db.jar\n'
	@printf '$$ cat <filename> | java -cp build:. com.cmput291p2.group2.Phase1.Main\n'
	@printf 'Success. Files created:\n'
	@printf '\treviews.txt\n'
	@printf '\tpterms.txt\n'
	@printf '\trterms.txt\n'
	@printf '\tscores.txt\n'
	@printf '$$ java -cp build:. com.cmput291p2.group2.Phase2.Main\n'
	@printf 'Success. Files created:\n'
	@printf '\trw.idx\n'
	@printf '\tpt.idx\n'
	@printf '\trt.idx\n'
	@printf '\tsc.idx\n'
	@printf '$$ java -cp build:.:$$CLASSPATH com.cmput291p2.group2.Phase3.Main\n'
	@printf 'Amazon Review Query Engine\n\n'
	
	@printf '>> camera\n'
	@printf '...output...\n\n'
	
	@printf '>> cam%% rscore < 4\n'
	@printf '...output...\n'

build1:
	@printf 'Building Phase 1...\n'
	mkdir -p build
	javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase1/*.java

build2:
	@printf 'Building Phase 2...\n'
	mkdir -p build
	javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase1/ReviewFileWriter.java src/com/cmput291p2/group2/Phase2/*.java

build3:
	@printf 'Building Phase 3...\n'
	mkdir -p build
	javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase3/*.java

test: test1 test3

test1: build1
	@printf '\n[Phase 1] Test Cases\n'
	@javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase1/test/*.java src/com/cmput291p2/group2/Phase1/*.java
	@java -cp build:.:$(CLASSPATH) \
		org.junit.runner.JUnitCore \
		com.cmput291p2.group2.Phase1.test.ReviewFileWriterTest

test3: build1 build2 build3
	@printf '\n\n[Phase 3] Test Cases\n'
	@printf '...running Phase1 with size=10k\n'
	@cat src/com/cmput291p2/group2/Phase1/test/10k/10k.txt | java -cp build:. com.cmput291p2.group2.Phase1.Main
	@printf '...running Phase2\n'
	@java -cp build:. com.cmput291p2.group2.Phase2.Main
	@printf '...running test cases\n'
	@javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase3/test/*.java src/com/cmput291p2/group2/Phase3/*.java
	@java -cp build:.:$(CLASSPATH) \
		org.junit.runner.JUnitCore \
		com.cmput291p2.group2.Phase3.test.QueryControllerTest

test3big: build1 build2 build3
	@printf '\n\n[Phase 3] Test Cases with BIG input\n'
	@printf '...running Phase1 with size=100k\n'
	@cat src/com/cmput291p2/group2/Phase1/test/100k/100k.txt | java -cp build:. com.cmput291p2.group2.Phase1.Main
	@printf '...running Phase2\n'
	@java -cp build:. com.cmput291p2.group2.Phase2.Main
	@printf '...running test cases\n'
	@javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase3/test/*.java src/com/cmput291p2/group2/Phase3/*.java
	@java -cp build:.:$(CLASSPATH) \
		org.junit.runner.JUnitCore \
		com.cmput291p2.group2.Phase3.test.QueryControllerTest

test3eclass:
	@printf '\n\n[Phase 3] Test Cases from eClass classmates\n'
	@printf '...running Phase1 with size=1k\n'
	@cat src/com/cmput291p2/group2/Phase1/test/1k/1k.txt | java -cp build:. com.cmput291p2.group2.Phase1.Main
	@printf '...running Phase2\n'
	@java -cp build:. com.cmput291p2.group2.Phase2.Main
	@printf '...running test cases\n'
	@javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase3/test/*.java src/com/cmput291p2/group2/Phase3/*.java
	@java -cp build:.:$(CLASSPATH) \
		org.junit.runner.JUnitCore \
		com.cmput291p2.group2.Phase3.test.eClassQueryControllerTest
