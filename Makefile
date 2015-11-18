
SHELL := /bin/bash

all: build1 build2 build3
	@printf '\nBuild complete.\n\n'
	@printf 'Phase 1: $ java -cp build:. com.cmput291p2.group2.Phase1.Main\n'
	@printf 'Phase 2: $ java -cp build:. com.cmput291p2.group2.Phase2.Main\n'
	@printf 'Phase 3: $ java -cp build:. com.cmput291p2.group2.Phase3.Main\n'

build1:
	mkdir -p build
	javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase1/*.java

build2:
	mkdir -p build
	javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase2/*.java

build3:
	mkdir -p build
	javac -d build src/com/cmput291p2/group2/common/*.java src/com/cmput291p2/group2/Phase3/*.java
