# GeneticAlgorithm
## Optimizing the linux scheduler

The CPU scheduling is the basis of multiprogramming operating systems. By switching the
CPU among processes, the operating system can make the computer more productive. The
scheduler controls the way processes are managed in the operating system. 
Linux supports preemptive multitasking, this means that the process scheduler decides which process
runs and when.
Balance performance across different computer configurations is one challenge in modern operating
systems. Linux has two separate process-scheduling algorithms.
If a Linux system performs similar tasks in a regular manner, it could be useful to implement
optimizations to the Linux scheduler to optimize the performance of those tasks. 

With this project is possible to analyze and evaluate the impact of modifying the kernel values 
timeslice, the latency, the granularity and the swappiness, finding the best performance
for the calculation of 8,765,4321 digits of pi using the Leibniz formula, measuring and comparing the 
time that the system takes to complete the calculation. All this through the utilization of a Genetic
Algorithm. 


## The algorithm
Create a population of size N, each with randomly generated DNA
### Selection
Evaluate the fitness of each element of the population.
- Fitness function - Phoronix Test Suit Sample Program
### Reproduction
Repeat N times:
-	1. Pick two parents with probability according to relative fitness.
-	2. Crossover - create a child by combining the DNA of these two parents.
-	3. Add the new child to a new population that will replace the old generation.
#### Return to selection step and repeat until get the target.


### Requirements

To automate your script tasks expect is needed.
Follow the instrutions to download in http://expect.sourceforge.net/
