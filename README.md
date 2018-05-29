# DBIndexPerfDemo
## test case
Demo: how high volume of H2 inserting (with or without index) impacts on DB performance

## test result
H2 has poor performance when inserting records after 2 million + (path varchar is indexed or path varchar is the primary key)
However if primary key is ID int and path is not indexed, 1000W records only take about 6 min in my x220i thinkpad.

This shows String primary key or index String take a lot of time during inserting; another important result is: index string 
takes more space (more than 2X at least) than no-index inserting.

## test data  
* the sql in demo takes about 600M space after 10 million inserting without index; 
* takes about 2G+ after 3 million inserting.
