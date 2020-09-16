package com.example.beehivesolitaire;

import java.util.Random;
public class Deck { //deck builds a String Array of Strings that are identically named to the card Drawables
    int cardIndex = 0;
    String[] Cards = new String[52]; //Construct a deck
    public  Deck(){
        String[] Suits = {"c","d","h","s"};
        String[] Ranks = {"2","3","4","5","6","7","8","9","t","j","q","k","a"};

        for(int i =0; i<Cards.length;i++){
            Cards[i] = "c_"+Ranks[i%13]+Suits[i/13]; //Creates a string array of Strings matching card names
        }

    }
    public void Shuffle(){ //Shuffle the deck
        Random random = new Random();
        for (int i = 0; i < Cards.length; i++) {
            int r1 = random.nextInt(Cards.length-i);
            int r2 = random.nextInt(Cards.length-i);
            String t = Cards[r1];
            Cards[r1] = Cards[r2];
            Cards[r2] =t;
        }
    }
    public String[] getDeck(){
        return Cards;
    }

    public String Draw(){
        String card = Cards[cardIndex];
        cardIndex++;
        return card;
    }
}
