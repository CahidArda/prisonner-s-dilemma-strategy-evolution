In this project I tried to recreate the results reached in an article named [A strategy of win-stay, lose-shift that outperforms tit-for-tat in the Prisoner's Dilemma game](https://www.nature.com/articles/364056a0)

In the article, simulation is explained with the followirng sentences:
> Two players are engaged in the Prisonner's Dilemma and have to choose between cooperation (C) and defection (D). According to their decisions, they are awarded with points. In any given round, the two players receive R points if both cooperate and only P points if they both defect; but a defector exploiting a cooperator gets T points, while the cooperator receives S (with T>R>P>S and 2R>T+S). Thus in a single round it is always best to defect, but cooperation may be rewarded in an iterated (or spatial) Prisoner's Dilemma.

There are two seperate folders, containing two seperate programs in this repository. One is the first implementation, written without use of multi-threading; in the second implementation, I tried to harness the power of multi-threading and somewhat succeeded.
