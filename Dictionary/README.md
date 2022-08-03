# Programming Assignment 7


This program utilizes the Dictionary ADT in order to store input strings as keys, with the line numbers of each input string as the corresponding value. The Dictionary ADT stores the keys and values as Nodes in a Binary Search Tree. Furthermore, The Dictionary also enforces that all keys are unique, while values may repeat. Additionally, the Dictionary has a current iterator which allows the client to iterate through the dictionary in alphabetical order. The Dictionary can be printed either through an in-order traversal or a pre-order traversal.

## Building

This program generates the executable Order using the makefile included in the assignment folder

Use the following command to run the makefile
```
make
```

### Running

This program can be run with the following command:
```
./Order <input file> <output file>
```

### Files

- README 
- Makefile 
- Dictionary.h
- Dictionary.cpp 
- DictionaryTest.cpp 
- Order.cpp 

### Error Handling

In the event that a member function of the Dictionary ADT is used incorrectly, the program will throw an exception which specifies the function in which the exception was triggered, and the cause of it. The program will also throw an exception and exit if more than or less than 2 command-line arguments are entered.

### Remarks

The Order Test 5 of the grading script times out depending on the timeshare server and time of the day. It passed the test at one point yesterday, but its inconsistent. The test runs and passes in around 6 seconds.
