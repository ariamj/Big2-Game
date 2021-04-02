# Big 2

## ***What will the application do?***

There are many different applications with their variations of Big 2, however, this application has its own version of
the game. Similar to the normal game of Big 2, each player will be dealt some cards. The number of cards  
will depend on whether the user choose to play with 13 cards (the normal amount) or half a deck (due to 2 players only).

There are 9 different types of hands that are possible to be played:
- Single
- Pairs
- Three of a kind
- Straight
- Flush
- Full house
- Four of a kind
- Straight flush
- Royal straight flush

With the last 6 hands being 5 card hands (they are played in sets of 5 cards). The number of cards being played must 
the number of cards that the most recent hand played has.
The cards also increase in ranking from 3 to the king--like normal counting--with the ace being bigger than the king 
and 2 being the greatest. As for their suits, they are ranked as follows from lowest to highest:
1. Diamonds
2. Clubs
3. Hearts
4. Spade

There are also chips to either be won or lost:
- White chips are worth 1 point
- Blue chips are worth 5 points
- Red chips are worth 10 points
- Gold chips are worth 25 points

At the end of each game, the amount of cards each player has left is equivalent to the number of points 
that they lose with the exception of the 2. If the player still has a 2, that card is worth 5 points (ie. they 
lose an extra 4 points on top).

## ***Who will use it?***

This application is for anyone who enjoys playing card games--especially Big 2--and perhaps want to try a different 
version of it. Since there is no actual gambling involved, it is for any age range.

## ***Why is this project of interest to you?***

I wanted to created this game because this version of playing Big 2 is something that my family always played whenever 
we had family dinners or gatherings. There were always more than four people who wanted to play so we had to use two 
decks of cards instead of one. My aunt is also a creative person who enjoys altering the rules of games and making her 
own games as well, thus, the new rules for how chips are paid. Being able to make something that my family "created" 
--or rather altered I guess--into an actual game would be fun and interesting.


## USER STORIES

- As a user, I want to be able to select whether to play with 13 cards or half a deck.
- As a user, I want to be able to play a hand on the table (ie. put cards onto the table).
- As a user, I want to both add and remove chips from my drawer.
- As a user, I want to both view my cards, and the current cards/hand on the table
- As a user, I want to be able to save the state of the card game to file when I quit the game
- As a user, I want to be able to load the state of the card game I last played from file when opening the game


## PHASE 4: TASK 2

Task chosen: Test and design a class in the model package that is robust.

- Class: DeckOfCards, method: DeckOfCards.dealCards(String)


## PHASE 4: TASK 3

After drawing out the full UML class diagram for my project, I realized that there are a lot more associations 
between classes than I thought. However, even after thinking about any places where I can perhaps refractor to 
improve my code, I found that my classes are still quite cohesive. Each class represented one aspect of my game that 
had different functionality as any other classes. As for classes that did perhaps have similar functionality, they 
were all subclasses of a more general class (eg. TablePile is a Hand which is a ListOfCards).

