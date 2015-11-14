# amazon-berkeleydb

CLI for building and querying a faux Amazon reviews database. Uses Berkeley DB.

> Authors: Ross Anderson, Daniel Shin, Andrew Bradshaw

---

### Build

`$ make`

### Demo

TODO

### Develop

TODO

### Test

TODO

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
- TODO 

### Testing

#### Strategy

TODO

#### Coverage

TODO

### Group work break-down strategy

Work item breakdown

- each program skeleton was treated as one work item
- each program's functionality was then broken down into work items as follows

- Phase 1: Writing data files from stdin
  - P1.1: Program reads reviews from stdin. Reviews are separated by `\n`.
  - P1.2: Program escapes certain stdin characters: `"`->`&quot;` and `\`->`\\`
  - P1.3: Program creates 4 files: `reviews.txt`, `pterms.txt`, `rterms.txt`, and `scores.txt`. If these files exist, they will be overwritten.
  - P1.4: `reviews.txt`
  	- P1.4.1: File has one row for each review record
  	- P1.4.2: Fields are ordered as given in the input
  	- P1.4.3: The first field is the record id. Record ids are assigned sequentially starting at 1.
  	- P1.4.4: Consecutive fields are separated by a comma: `,`
  	- P1.4.5: Fields *product title*, *profile name*, *review summary*, and *review text* are placed inside quotation marks: `"..."`
  - P1.5: `pterms.txt`
  	- P1.5.1: File includes terms from **product titles**.
  	- P1.5.2: Terms are included only if they are length 3 or greater. (included:`the`, not included:`be`)
  	- P1.5.3: Terms are defined as consecutive sequences of [0-9a-zA-Z_].
  	- P1.5.4: For each term `T` in a product title with record id `I`, there exists a row in the file of the form `T',I` where `T'` is lowercase `T`.
  - P1.6: `rterms.txt`
  	- P1.6.1: File includes terms from **review summaries** and **review text**.
  	- P1.6.2: Terms are included only if they are length 3 or greater. (included:`the`, not included:`be`)
  	- P1.6.3: Terms are defined as consecutive sequences of [0-9a-zA-Z_].
  	- P1.6.4: For each term `T` in a review summary or review text with record id `I`, there exists a row in the file of the form `T',I` where `T'` is lowercase `T`.
  - P1.7: `scores.txt`
    - P1.7.1: For each record id `I`, there exists a row in the file of the form `sc:I` where `sc` is the review score.

- Phase 2: Building indexes from data files
  - P2.1: Program sorts all files from Phase 1 (except `reviews.txt`) using the linux `sort` command. Keep only unique rows. Write the sorted data back to the same filenames.
  - P2.2: Program creates an index `rw.idx`. This is a hash index on `reviews.txt` with key = review id, data = full review record
  - P2.3: Program creates an index `pt.idx`. This is a B+ tree index on `pterms.txt` with key = term, data = review id
  - P2.4: Program creates an index `rt.idx`. This is a B+ tree index on `rterms.txt` with key = term, data = review id
  - P2.5: Program creates an index `sc.idx`. This is a B+ tree index on `scores.txt` with key = score, data = review id
  - *NOTE* Use the `db_load` command to build indexes. Use the `db_dump` command to debug.

- Phase 3: Querying data from a CLI
  - P3.1: As a user, I can enter a query and receive human readable output
  - P3.2: Program search must be case-insensitive
  - P3.3: As a user, I can use queries of form `p:query`
  - P3.4: As a user, I can use queries of form `r:query`
  - P3.5: As a user, I can use queries of form `query`
  - P3.6: As a user, I can use queries of form `que%`
  - P3.7: As a user, I can use queries of form `rscore|pprice|rdate < val`
  	- P3.7.1: As a user, I can use queries of form `rscore < score` (and `>`)
  	- P3.7.2: As a user, I can use queries of form `pprice < price` (and `>`)
  	- P3.7.3: As a user, I can use queries of form `rdate < date` (and `>`)
  - P3.8: As a user, I can enter queries containing multiple query forms
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
  - TODO
- Andrew Bradshaw (abradsha)
  - TODO
- Daniel Shin (dshin)
  - TODO

Method of coordination for staying on track
- Facebook messenger group thread for communication
- Github repository for code sharing

### Any assumptions or design decision extraneous to the specification on eClass

TODO
