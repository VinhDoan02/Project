# CodeFight Problem
This folder will contain comments and solutions to some coding question from CodeFight
[CodeFight](https://codefights.com/) 


## Motivation
To explain my thought process and show a possible solution to some coding question 
from CodeFight that i found interesting or challenging.
I will try to talk about my though process on this README as well as providing a solution in JAVA.
Some comment can also be found in the code.

### LexSwapOrder
My Original method was a brute force method. I first tried by using a wrapper class with a recursive method.
The idea was to generate all possible swapping solution from the indices and add them to a HashSet the recursion would stop when it found a previously found String.
However, that would prove to not work quite well for a larger data set which would cause a StackOverflow error due to all the recursion accessing the same HashSet. 
The second method was to create a nested for loop with a set and a queue, the set would hold possible swapped string result while the queue would hold current swappedString.
The principle was the outer loop would loop until the queue was empty while the inner loop would pop the queue and proceed to swap the indices for each pair of indices.
For each new string discovered would be added to the set and to the queue.
This worked somehow but it was obviously not very efficient and would take too long for a larger dataset.
 
After attempting to optimize part of the code I realize that I would need to rethink the problem.
I then realize that only the character part of the indices could change and more specifically 
that some pair of indices would overlap and create potentially a larger set of indices where characters could be swapped.
But only character corresponding to the indices within each (potentially larger) sets could be swapped together
So I divided the problem into three part.
The First part, combining overlapping indices into a list of sets of indices. 
The Second part, for each set of indices that can be swapped, 
we get the corresponding characters from the string and then sort them in descending order (to get the largest lexicographical String).
The third part was to take the string replace the corresponding character with the proper character from the mapping obtain from part two.
