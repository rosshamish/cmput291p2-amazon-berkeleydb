# amazon-berkeleydb

CLI for building and querying a faux Amazon reviews database. Uses Berkeley DB.

> Authors: Ross Anderson, Daniel Shin, Andrew Bradshaw

---

### Build

`$ make`

### Demo

```
$ export CLASSPATH=$CLASSPATH:.:/usr/share/java/db.jar
$ cat <filename> | java -cp build:. com.cmput291p2.group2.Phase1.Main
Success. Files created:
	reviews.txt
	pterms.txt
	rterms.txt
	scores.txt
$ java -cp build:. com.cmput291p2.group2.Phase2.Main
Success. Files created:
	rw.idx
	pt.idx
	rt.idx
	sc.idx
$ java -cp build:.:$CLASSPATH com.cmput291p2.group2.Phase3.Main
Amazon Review Query Engine

>> camera
...output...

>> cam% rscore < 4
...output...
```

### Develop

TODO

### Test

`$ export JUNIT_PATH=/path/to/junit4.jar` (not needed on Comp Sci lab machines)
`$ make test`

OR

- Import project into IntelliJ (or Eclipse) as a Java project
- Phase 1: Right-click Phase1/test/ReviewFileWriterTest -> Run
- Phase 3: Right-click Phase3/test/QueryControllerTest -> Run

---

### Attribution

TODO

---

### Overview of the system

#### High-level intro

CLI for building and querying a faux Amazon reviews database. Uses Berkeley DB.

#### Data Flow Diagram

TODO

#### User Guide

TODO

### Detailed design of the software

Responsibilities and interfaces of primary classes, including relationships between them

Common:
- class Review models an Amazon review. It encapsulates all data about the review. It provides two constructors. The first constructor creates a Review object given a list of strings in the format given in the portion of the eClass specification regarding Phase 1 input. The second constructor creates a Review object given a comma separated string in the format given in the portion of the eClass specification regarding Phase 1 output.
- class Debugging is used to conditionally print output depending on the debug or release state of the build.
- class Differ is used to diff files. It is used to test Phase 1.

Phase 1:
- class Main is the entry point. It delegates to class ReviewFileWriter.
- class ReviewFileWriter reads input (either from stdin or a file) and writes appropriate files to disk. It models Amazon reviews using class Review.

Phase 2:
- class Main is the entry point. It delegates to class IndexBuilder.
- class IndexBuilder reads the files generated by ReviewFileWriter and writes appropriate BerkeleyDB indexes to disk.

Phase 3:
- class Main is the entry point. It delegates to class QueryCLI.
- class QueryCLI runs the main application loop. It uses an instance of QueryIO to retrieve input and display output. It uses an instance of QueryController to process queries and retrieve matching reviews.
- class QueryIO has two responsibilities. The first is to retrieve input from the user and return it back to the caller. The second is to display output to the user given information passed by the caller.
- class QueryController is responsible for parsing the query string into individual, atomic queries. It delegates query execution to the QueryEngine.
- class QueryEngine is responsible for using the BerkeleyDB indexes created by Phase 2 to execute queries and return Review objects back to the caller.

### Testing

#### Strategy

Phase 1:
- class ReviewFileWriterTest tests class ReviewFileWriter. It uses the 10-review input file given on eClass as input. It expects ReviewFileWriter's output files to be fully equal to the 10-review output files given on eClass as expected output.
- it is run as a JUnit4 test.

Phase 2:
- indirectly tested by Phase 3

Phase 3:
- class QueryCLI is tested indirectly as a result of testing class QueryController
- class QueryControllerTest tests class QueryController. It passes a query string to QueryController, and asserts the return value is as expected. Test cases were created based on query examples given on eClass. Additional test cases were created based on additional constraints (ie case-insensitivity) and additional details (ie testing both greater-than AND less-than).

#### Coverage

TODO

### Group work break-down strategy

Work item breakdown

- each program skeleton was treated as one work item
- each program's functionality was then broken down into work items as follows

- Phase 1: Writing data files from stdin
  - [x] P1.1: Program reads reviews from stdin. Reviews are separated by `\n`.
  - [x] P1.2: Program escapes certain stdin characters: `"`->`&quot;` and `\`->`\\`
  - [x] P1.3: Program creates 4 files: `reviews.txt`, `pterms.txt`, `rterms.txt`, and `scores.txt`. If these files exist, they will be overwritten.
  - P1.4: `reviews.txt`
  	- [x] P1.4.1: File has one row for each review record
  	- [x] P1.4.2: Fields are ordered as given in the input
  	- [x] P1.4.3: The first field is the record id. Record ids are assigned sequentially starting at 1.
  	- [x] P1.4.4: Consecutive fields are separated by a comma: `,`
  	- [x] P1.4.5: Fields *product title*, *profile name*, *review summary*, and *review text* are placed inside quotation marks: `"..."`
  - P1.5: `pterms.txt`
  	- [x] P1.5.1: File includes terms from **product titles**.
  	- [x] P1.5.2: Terms are included only if they are length 3 or greater. (included:`the`, not included:`be`)
  	- [x] P1.5.3: Terms are defined as consecutive sequences of [0-9a-zA-Z_].
  	- [x] P1.5.4: For each term `T` in a product title with record id `I`, there exists a row in the file of the form `T',I` where `T'` is lowercase `T`.
  - P1.6: `rterms.txt`
  	- [x] P1.6.1: File includes terms from **review summaries** and **review text**.
  	- [x] P1.6.2: Terms are included only if they are length 3 or greater. (included:`the`, not included:`be`)
  	- [x] P1.6.3: Terms are defined as consecutive sequences of [0-9a-zA-Z_].
  	- [x] P1.6.4: For each term `T` in a review summary or review text with record id `I`, there exists a row in the file of the form `T',I` where `T'` is lowercase `T`.
  - P1.7: `scores.txt`
    - [x] P1.7.1: For each record id `I`, there exists a row in the file of the form `sc:I` where `sc` is the review score.

- Phase 2: Building indexes from data files
  - [x] P2.1: Program sorts all files from Phase 1 (except `reviews.txt`) using the linux `sort` command. Keep only unique rows. Write the sorted data back to the same filenames.
  - [x] P2.2: Program creates an index `rw.idx`. This is a hash index on `reviews.txt` with key = review id, data = full review record
  - [x] P2.3: Program creates an index `pt.idx`. This is a B+ tree index on `pterms.txt` with key = term, data = review id
  - [x] P2.4: Program creates an index `rt.idx`. This is a B+ tree index on `rterms.txt` with key = term, data = review id
  - [x] P2.5: Program creates an index `sc.idx`. This is a B+ tree index on `scores.txt` with key = score, data = review id
  - *NOTE* Use the `db_load` command to build indexes. Use the `db_dump` command to debug.

- Phase 3: Querying data from a CLI
  - [ ] P3.1: As a user, I can enter a query and receive human readable output
  - [x] P3.2: Program search must be case-insensitive
  - [ ] P3.3: As a user, I can use queries of form `p:query`
  - [ ] P3.4: As a user, I can use queries of form `r:query`
  - [ ] P3.5: As a user, I can use queries of form `query`
  - [ ] P3.6: As a user, I can use queries of form `que%`
  - P3.7: As a user, I can use queries of form `rscore|pprice|rdate < val`
  	- [ ] P3.7.1: As a user, I can use queries of form `rscore < score` (and `>`)
  	- [ ] P3.7.2: As a user, I can use queries of form `pprice < price` (and `>`)
  	- [ ] P3.7.3: As a user, I can use queries of form `rdate < date` (and `>`)
  - [x] P3.8: As a user, I can enter queries containing multiple query forms
  - *NOTE* Check the eClass specification for details on all query forms

- the report skeleton was treated as one work item
- the report skeleton was then broken down into work items as follows
  - REP1: general overview of system
    - REP1.1: incl. a small user guide (describing usage of Phases 1+2+3)
    - REP1.2: incl. a data flow diagram
  - REP2: description of query evaluation algorithm
    - specifically: multiple conditions, wild cards, range searches
    - specifically: analysis of algorithm efficiency
  - REP3: testing strategy
    - REP3.1: incl. scenarios being tested
    - REP3.2: incl. test case coverage
  - REP4: group work breakdown strategy

Estimate of time spent
- Ross Anderson (rhanders): TODO hrs
- Andrew Bradshaw (abradsha): TODO hrs
- Daniel Shin (dshin): TODO hrs

Functionality implemented by each member
- Ross Anderson (rhanders)
  - report skeleton
  - REP4: group work breakdown strategy
  - System design, class skeleton creation, Javadocs
  - REP3.1: scenarios being tested
  - Tests for Phase 1
  - Debugging and completion of Phase 1
  - Tests for Phase 3
- Andrew Bradshaw (abradsha)
  - P1.1 through P1.7 (Phase 1)
  - P2.1 through P2.5 (Phase 2)
- Daniel Shin (dshin)
  - TODO

Method of coordination for staying on track
- Facebook messenger group thread for communication
- Github repository for code sharing

### Any assumptions or design decision extraneous to the specification on eClass

TODO
