# CSX42: Assignment 2
## Name: Nicholas Vallejos

-----------------------------------------------------------------------
-----------------------------------------------------------------------


Following are the commands and the instructions to run ANT on your project.
#### Note: build.xml is present in numberPlay/src folder.

-----------------------------------------------------------------------
## Instruction to clean:

```commandline
ant -buildfile numberPlay/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

-----------------------------------------------------------------------
## Instruction to compile:

```commandline
ant -buildfile numberPlay/src/build.xml all
```

Description: Compiles your code and generates .class files inside the BUILD folder.

-----------------------------------------------------------------------
## Instruction to run:

#### Use the below command to run the program.

```commandline
ant run -buildfile numberPlay/src/build.xml \
-DinputNumStream="<input file path>" \
-DrunAvgWindowSize="<size of the window for running average calculations>" \
-DrunAvgOutFile="<output file path to which running averages are written>" \
-Dk="<max size of the list containing the top K numbers>" \
-DtopKNumOutFile="<path of output file to which the top K numbers are written>" \
-DnumPeaksOutFile="<path of output file to which the peaks in the number stream are written>"
```

-----------------------------------------------------------------------
## Description:
Data Structures:
- HashMap:
	- Used in NumberProcessor class to store observers with their associated filters (HashMap <FilterI, ArrayList<ObserverI> >)
	- Look-up time is constant O(1)
	- Inserting new values is O(1)
	- Removal of a Value from my particular HashMap is tricky since an Observer can be registered under multiple filters (please see deregisterObserver() in NumberProcessor.java)
- ArrayList
	- Used throughout the project to store variable amounts of data
	- Accessing elements takes constant time (O(1))

Quick note on my FilterI Class:
- The 'FilterI' interface is located in numberPlay/src/numberPlay/filter/FilterI.java
- The filter implementation is in a class called 'FilterImpl', it is located in the 'util' folder

Quick rundown on my Driver Class:
- I instantiate three data objects - one per Data class (RunningAverageData, etc.)
- I instantiate three FilterImpl objects, each containing a String "INTEGER_EVENT", or "FLOATING_POINT_EVENT", or "PROCESSING_COMPLETE".
- I instantiate three observers - one per Observer class (RunningAverageObserver, etc.)
- Each observer stores a corresponding data object
- I instantiate a single Subject object (a NumberProcessor object)
- I manually register each observer with their corresponding filters
- Finally, I call subject.processInputFile() inside a try-catch block
- Note: I also made a custom test in the Driver class thats commented out (See below for details on custom test)

Quick notes on RunningAverageObserver:
- In RunningAverageObserver I use an ArrayList called 'fifoQueue'
- The size of 'fifoQueue' is limited by the 'avgWindowSize'
- I also keep the sum of the numbers read so far in 'runningTotal'
- I remove elements from 'fifoQueue' using first-in first-out principle
- See update() method for running average calculation

Quick notes on TopKNumbersObserver:
- I use an ArrayList called 'fifoQueue'
- The size of 'fifoQueue' is limited by 'k' value
- I remove elements from 'fifoQueue' using first-in first-out principle
- See update() method for TopKNumbers calculation

(NumberPeaksObserver doesn't use a list so its more self-explanatory (see update() method))

An aside on the FilterImpl test() method:
- All observers associated with a particular filter can be obtained using HashMap.get(new FiliterImpl("EVENT_TAG"))
- It seems that there is no need to call FilterImpl.test(String tag) inside the notifyObserver(String tag, Object data) method
- However, for the purpose of this assignment, I utilized the FilterImpl.test method in the notifyObserver method

-----------------------------------------------------------------------
## Important References
- Overriding equals() method - https://javarevisited.blogspot.com/2011/10/override-hashcode-in-java-example.html
- Idea for overriding hashCode() method - https://www.baeldung.com/java-hashcode
- Idea for rounding to 2 decimal places: https://stackoverflow.com/a/11701527
- HashMap Documentation: https://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html
- Command Arg Validation Design based on classwork src: https://piazza.com/class/k5k3yuyx97612d?cid=37

-----------------------------------------------------------------------
## Custom Test In Driver Class:
I made a custom test that deregisters observers. This will ensure that my deregisterObserver() method works, that each Observer equals() method works, and that each Data Class equals() method works. To do this, uncomment line 101 and 113

-----------------------------------------------------------------------
## Miscellaneous:
I was told by TA that it's okay if my output is all doubles:
```
1.0 <= this is okay
1.5
1.67
2.33
3.0 <= this is okay
4.0 <= this is okay
5.0 <= this is okay
6.0 <= this is okay
```
(I posted this as a private piazza post but leaving this here anyways - https://piazza.com/class/k5k3yuyx97612d?cid=137)

-----------------------------------------------------------------------
### Academic Honesty statement:
-----------------------------------------------------------------------

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: 2/23
