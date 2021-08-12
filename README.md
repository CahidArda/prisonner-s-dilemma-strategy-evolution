# Prisonner's Dilemma Strategy Evolution

In this project I tried to recreate the results reached in an article named [A strategy of win-stay, lose-shift that outperforms tit-for-tat in the Prisoner's Dilemma game](https://www.nature.com/articles/364056a0)

In the article, the simulation is explained with the followirng sentences:

> Two players are engaged in the Prisonner's Dilemma and have to choose between cooperation (C) and defection (D). According to their decisions, they are awarded with points. In any given round, the two players receive R points if both cooperate and only P points if they both defect; but a defector exploiting a cooperator gets T points, while the cooperator receives S (with T>R>P>S and 2R>T+S). Thus in a single round it is always best to defect, but cooperation may be rewarded in an iterated (or spatial) Prisoner's Dilemma.

## Project Directory

There are four seperate folders in this repository:
* output: folder where the simulation results are written to. There is also a real-time plotting script.
* previous_results: record of the results obtained during code development. This data is then used to create plots in the __graph folder.
* src-with-threads: code for the simulation with threads can be found in this folder
* src-without-threads: code for the simulation without threads can be found in this folder

## Results

As I developed the code, I gradually added new features and tested them by generating data and observing the evolution of agent behaviour.

I started with the most basic features; creating agents with random strategies, matching them to generate scores, replacing the worst performing agents with new random agents. Results looked like this:
  
##### Graph 1
![](./previous_results/_graphs/r1.png)

In the plots, horizontal axis represents the number of generation. Vertical axis represents agent's probability to cooperate in the next round given the previous result. For example, we can see that in the first generation, the best performing agent's probability of cooperating is a number just below 0.5 if the previous result is S (blue line).
  
##### Graph 2
![](./previous_results/_graphs/r2.png)

##### Graph 3
![](./previous_results/_graphs/r3.png)

Here are my observations for these 3 initial attemps:
- Average strategies are stable in uncooperative levels, no changes occur in the overall strategy of the population.
- Strategy of the best performing agent often attempts to develop a cooperative strategy but fails everytime.

### Adding Mutation

My next step was to add mutations. In every simulation, I set a probability of mutation in every generation. This is how it looked like in my first test with mutation added:

##### Graph 4
![](./previous_results/_graphs/m1.png)

Since this is a short test with 1000 generations, there aren't major changes imidiately. It looks similar to the Graph 2 which was run without mutation.

### Changing Strategy Initialization to Sigmoid Function

When initializing strategy probabilities for a new agent, I was using pseudo-random values. I then decided to add a sigmoid function step to my strategy probability initialization. Combined with the addition of mutation, my next test produced the following result:

##### Graph 5
![](./previous_results/_graphs/s1.png)

This was the first time cooperative strategy gained dominance. Best performing agent always had high R probability and average R probability gradually increased. After this short test with 1000 generations, I ran another test to see how strategies evolved for more generations:

##### Graph 6
![](./previous_results/_graphs/s2.png)

### Adding Evolution Step 2

Until now, there were two ways for agents with new strategy probabilities to enter the generation: One is when worst performing agents get deleted and new agents with random strategies are created insted, and the other way is when an existing agents get mutated. In this step, I changed the former one so that strategy initialization is not random but is done by blending the strategies of the best performing agents.
This resulted in the following evolution:

###### Graph 7
![](./previous_results/_graphs/e1.png)

In this test, agents evolved in a way that resulted with a strategy described in the [paper](https://www.nature.com/articles/364056a0) as *Pavlov player*. This type of player is described with the following sentences in the paper:
> A Pavlov player cooperates if and only if both players opted for the same alternative in the previous move. The name stems from the fact that this strategy embodies the almost reflex-like response to the payoff: it repeats its former move if it was rewarded R or T points, but switches behaviour if it was punished by receiving only P or S points.

My second test with more generations created a similar result, with Pavlov domination:

###### Graph 8
![](./previous_results/_graphs/e2.png)

Another thing we can observe is that the worst performing agent strategy is no longer a random behaivour. It is similar to the most common strategy of the population.

### Trembling Hand

I then implemented the concept of *trembling hand*. This is analogus to adding noise to the environment. Agents occasionally miscommunicate and make mistakes: They cooperate or betray by mistake.

Another Pavlov dominated group of agents evolved:

###### Graph 9
![](./previous_results/_graphs/t1.png)

When I increased the noise (probability of making a mistake), all agents evolved to uncooperative agents:

###### Graph 10
![](./previous_results/_graphs/t2.png)

### Final Run

After completing every features, I wanted to run the simulation for thousands of times but there was an issue. It was taking too long. I decided to look into multi-threading to run the simulation faster. After implementing multi-threading, I ran the simulation for 100.000 generations and I got the following result:

###### Graph 11
![](./previous_results/_graphs/g1.png)

In this run there was again a Pavlov domination, but interestingly this domination collapsed towards the end.
