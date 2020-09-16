package com.example.beehivesolitaire;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
//CSC 309
//Project: Beehive Solitaire
//Author: Jonathon Gower
//Date: 10/18/2019
public class MainActivity extends AppCompatActivity {
    String lastClicked = "";
    //Initialize arrayLists for gardenCards, stack, workpile and deck
    ArrayList<String> gardenSlot1 = new ArrayList<>();
    ArrayList<String> gardenSlot2 = new ArrayList<>();
    ArrayList<String> gardenSlot3 = new ArrayList<>();
    ArrayList<String> gardenSlot4 = new ArrayList<>();
    ArrayList<String> gardenSlot5 = new ArrayList<>();
    ArrayList<String> gardenSlot6 = new ArrayList<>();
    ArrayList<String> workPileList = new ArrayList<>();
    ArrayList<String> CompletedSets = new ArrayList<>();
    ArrayList<String> CardStack = new ArrayList<>();
    ArrayList<String> DeckList = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    public void MoveCard(ArrayList<String> CardList1, ArrayList<String> CardList2, ImageButton CardPlace1, ImageButton CardPlace2,TextView SetBox){
        final Resources res = getApplicationContext().getResources();
        // Test if the garden card is empty or not
        if (CardList1.size()!=0) {
            if (CardList1.get(0).charAt(2) == CardList2.get(CardList2.size() - 1).charAt(2)) {//compares the ranks of two cards on the board
                CardList1.add(CardList2.get(CardList2.size() - 1));
                CardList2.remove(CardList2.size() - 1); //remove the top card in CardStack
                CardPlace1.setImageDrawable(CardPlace2.getDrawable()); //Change imageView drawable to stack's drawable
                //Change the image on CardStack to that of the next card in the stack

                if (CardList2.size() == 0)
                    CardPlace2.setImageResource(res.getIdentifier("c_transparent","drawable",getApplicationContext().getPackageName())); //This is to set the ImageView as blank and shows no card
                else
                    CardPlace2.setImageResource(res.getIdentifier(CardList2.get(CardList2.size() - 1), "drawable", getApplicationContext().getPackageName())); //display the next Card
                    //Shrink CardStack by 1 since it is 1 card less now
                if (CardList1.size()==4){// if there is a four of a kind on the gardenSlot after a card is placed
                    //add that number to completed sets
                    CompletedSets.add(CardList1.get(0).charAt(2)+"");
                    if(CardList1.get(0).charAt(2)=='k'||CardList1.get(0).charAt(2)=='q'||CardList1.get(0).charAt(2)=='a'||CardList1.get(0).charAt(2)=='j') //CardList1.get(0).charAt(2)=='t'||
                        SetBox.setText(SetBox.getText()+(Character.toUpperCase(CardList1.get(0).charAt(2))+", "));
                    else if (CardList1.get(0).charAt(2)=='t')
                        SetBox.setText(SetBox.getText()+("10, "));
                    else
                        SetBox.setText(SetBox.getText()+(CardList1.get(0).charAt(2)+", "));
                    CardPlace1.setImageResource(res.getIdentifier("c_transparent","drawable",getApplicationContext().getPackageName()));
                    CardList1.clear();
                }
            }
            else
                lastClicked = "";
        }
        else{ // if the garden card is empty add the card from stack/workpile to the garden slot
            CardList1.add(CardList2.get(CardList2.size()-1));
            CardPlace1.setImageDrawable(CardPlace2.getDrawable());
            CardList2.remove(CardList2.size()-1); // Remove the just moved card from stack/workpile list
            CardPlace2.setImageResource(res.getIdentifier(CardList2.get(CardList2.size() - 1), "drawable", getApplicationContext().getPackageName())); //display new workpile/stack card

        }
        lastClicked = "";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize buttons and text views
        final ImageButton garden1 = findViewById(R.id.iv_garden1);
        final ImageButton garden2 = findViewById(R.id.iv_garden2);
        final ImageButton garden3 = findViewById(R.id.iv_garden3);
        final ImageButton garden4 = findViewById(R.id.iv_garden4);
        final ImageButton garden5 = findViewById(R.id.iv_garden5);
        final ImageButton garden6 = findViewById(R.id.iv_garden6);
        final ImageButton stack = findViewById(R.id.iv_stack);
        final ImageButton deck = findViewById(R.id.iv_deck);
        final ImageButton workpile = findViewById(R.id.iv_workpile);
        final TextView winBox = findViewById(R.id.winLoseBox);
        final TextView SetBox = findViewById(R.id.tv_CompSet);

        //final TypedArray images = getResources().obtainTypedArray(R.array.images); // gets the images that are listed in array.xml so it can be iterated
        // draw random cards for the garden
        // use two different deck objects and have one be shuffled and index the pictures based off of the index of the shuffled, drawn card in the
        // un-shuffled deck
        Deck d = new Deck();
        d.Shuffle(); //shuffle the deck

        final Resources res = getApplicationContext().getResources(); // Make a resource object to get drawables from
        //Draw the garden cards and add them to their respective ArrayLists
        gardenSlot1.add(d.Draw());
        gardenSlot2.add(d.Draw());
        gardenSlot3.add(d.Draw());
        gardenSlot4.add(d.Draw());
        gardenSlot5.add(d.Draw());
        gardenSlot6.add(d.Draw());
        //Display the cards from the array Lists
        garden1.setImageResource(res.getIdentifier(gardenSlot1.get(0),"drawable", getApplicationContext().getPackageName()));
        garden2.setImageResource(res.getIdentifier(gardenSlot2.get(0),"drawable", getApplicationContext().getPackageName()));
        garden3.setImageResource(res.getIdentifier(gardenSlot3.get(0),"drawable", getApplicationContext().getPackageName()));
        garden4.setImageResource(res.getIdentifier(gardenSlot4.get(0),"drawable", getApplicationContext().getPackageName()));
        garden5.setImageResource(res.getIdentifier(gardenSlot5.get(0),"drawable", getApplicationContext().getPackageName()));
        garden6.setImageResource(res.getIdentifier(gardenSlot6.get(0),"drawable", getApplicationContext().getPackageName()));
        deck.setImageResource(res.getIdentifier("c_b","drawable",getApplicationContext().getPackageName())); //Display back card
        workpile.setImageResource(res.getIdentifier("c_transparent","drawable",getApplicationContext().getPackageName())); //Work pile starts as transparent until deck is clicked
        for (int i = 0; i<10 ; i++){ //Draw 10 cards for the stack
            CardStack.add(d.Draw());
        } //top of card stack is index 0 bottom is CardStack.length-1
        deck.setImageResource(res.getIdentifier("c_back", "drawable", getApplicationContext().getPackageName()));
        //display top card of stack
        //draw the other 36 cards into deck
        for (int i = 0; i<36;i++)
            DeckList.add(d.Draw());
        //Display top card in the stack of 10

        stack.setImageResource(res.getIdentifier(CardStack.get(CardStack.size()-1),"drawable", getApplicationContext().getPackageName()));

        garden1.setOnClickListener(new View.OnClickListener() { //Tried to do a general clickListener but it wouldn't recognize the buttons
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) { //Tried to use a general click listener could not get it to recognize the ImageButtons
                if (lastClicked.equals("")&&gardenSlot1.size()!=0)
                    lastClicked = "garden1"; //Used to determine which button was clicked last, initialized as Empty String
                else{
                    switch(lastClicked){
                        case "stack":
                            //test if there is nothing in gardenSlot1
                            if(CardStack.size()!=0)
                                MoveCard(gardenSlot1,CardStack,garden1,stack,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked="";
                            break;
                        case "workpile":
                            //test if there is nothing in gardenSlot1
                            if(workPileList.size()!=0)
                                MoveCard(gardenSlot1,workPileList,garden1,workpile,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked = "";
                            break;
                        case "garden2":
                            if(gardenSlot1.size()!=0 && gardenSlot2.size()!=0)
                                MoveCard(gardenSlot1, gardenSlot2, garden1, garden2, SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden3":
                            if(gardenSlot1.size()!=0 && gardenSlot3.size()!=0)
                                MoveCard(gardenSlot1, gardenSlot3, garden1, garden3, SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden4":
                            if(gardenSlot1.size()!=0 && gardenSlot4.size()!=0)
                                MoveCard(gardenSlot1, gardenSlot4, garden1, garden4, SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden5":
                            if(gardenSlot1.size()!=0 && gardenSlot5.size()!=0)
                                MoveCard(gardenSlot1, gardenSlot5, garden1, garden5, SetBox);
                            else
                                lastClicked = "";
                                break;
                        case "garden6":
                            if(gardenSlot1.size()!=0 && gardenSlot6.size()!=0)
                                MoveCard(gardenSlot1,gardenSlot6,garden1,garden6,SetBox);
                            else
                                lastClicked = "";
                            break;
                    }
                    if(CompletedSets.size()==13){ //
                        // tell user they win
                        winBox.setText("You win!");
                    }
                }

            }
        });
        garden2.setOnClickListener(new View.OnClickListener() { //Tried to do a general clickListener but it wouldn't recognize the buttons so I just did this
            // sorry for the scrolling and long code
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (lastClicked.equals("")&&gardenSlot2.size()!=0)
                    lastClicked = "garden2"; //Used to determine which button was clicked last, initialized as Empty String
                else{
                    switch(lastClicked){
                        case "stack":
                            //test if there is nothing in gardenSlot1
                            if(CardStack.size()!=0)
                                MoveCard(gardenSlot2,CardStack,garden2,stack,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked="";
                            break;
                        case "workpile":
                            //test if there is nothing in gardenSlot1
                            if(workPileList.size()!=0)
                                MoveCard(gardenSlot2,workPileList,garden2,workpile,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked = "";
                            break;
                        case "garden1":
                            if(gardenSlot1.size()!=0 && gardenSlot2.size()!=0)
                                MoveCard(gardenSlot2,gardenSlot1,garden2,garden1,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden3":
                            if(gardenSlot2.size()!=0 && gardenSlot3.size()!=0)
                                MoveCard(gardenSlot2,gardenSlot3,garden2,garden3,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden4":
                            if(gardenSlot2.size()!=0 && gardenSlot4.size()!=0)
                                MoveCard(gardenSlot2,gardenSlot4,garden2,garden4,SetBox);
                            else
                                lastClicked = "";

                            break;
                        case "garden5":
                            if(gardenSlot2.size()!=0 && gardenSlot5.size()!=0)
                                MoveCard(gardenSlot2,gardenSlot5,garden2,garden5,SetBox);

                            break;
                        case "garden6":
                            if(gardenSlot2.size()!=0 && gardenSlot6.size()!=0)
                                MoveCard(gardenSlot2,gardenSlot6,garden2,garden6,SetBox);
                            else
                                lastClicked = "";

                            break;
                    }
                    if(CompletedSets.size()==13){ //
                        // tell user they win
                        winBox.setText("You win!");
                    }
                }

            }
        });
        garden3.setOnClickListener(new View.OnClickListener() { //Tried to do a general clickListener but it wouldn't recognize the buttons
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (lastClicked.equals("")&&gardenSlot3.size()!=0)
                    lastClicked = "garden3"; //Used to determine which button was clicked last, initialized as Empty String
                else{
                    switch(lastClicked){
                        case "stack":
                            //test if there is nothing in gardenSlot1
                            if(CardStack.size()!=0)
                                MoveCard(gardenSlot3,CardStack,garden3,stack,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked="";
                            break;
                        case "workpile":
                            //test if there is nothing in gardenSlot1
                            if(workPileList.size()!=0)
                                MoveCard(gardenSlot3,workPileList,garden3,workpile,SetBox);//
                            else
                                lastClicked = "";
                            break;
                        case "garden1":
                            if(gardenSlot3.size()!=0 && gardenSlot1.size()!=0)
                                MoveCard(gardenSlot3,gardenSlot1,garden3,garden1,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden2":
                            if(gardenSlot3.size()!=0 && gardenSlot2.size()!=0)
                                MoveCard(gardenSlot3,gardenSlot2,garden3,garden2,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden4":
                            if(gardenSlot3.size()!=0 && gardenSlot4.size()!=0)
                                MoveCard(gardenSlot3,gardenSlot4,garden3,garden4,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden5":
                            if(gardenSlot3.size()!=0 && gardenSlot5.size()!=0)
                                MoveCard(gardenSlot3,gardenSlot5,garden3,garden5,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden6":
                            if(gardenSlot3.size()!=0 && gardenSlot6.size()!=0)
                                MoveCard(gardenSlot3,gardenSlot6,garden3,garden6,SetBox);
                            else
                                lastClicked = "";
                            break;
                    }
                    if(CompletedSets.size()==13){ //
                        // tell user they win
                        winBox.setText("You win!");
                    }
                }

            }
        });
        garden4.setOnClickListener(new View.OnClickListener() { //Tried to do a general clickListener but it wouldn't recognize the buttons
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (lastClicked.equals("")&&gardenSlot4.size()!=0)
                    lastClicked = "garden4"; //Used to determine which button was clicked last, initialized as Empty String
                else{
                    switch(lastClicked){
                        case "stack":
                            //test if there is nothing in gardenSlot1
                            if(CardStack.size()!=0)
                                MoveCard(gardenSlot4,CardStack,garden4,stack,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked="";
                            break;
                        case "workpile":
                            //test if there is nothing in gardenSlot1
                            if(workPileList.size()!=0)
                                MoveCard(gardenSlot4,workPileList,garden4,workpile,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked = "";

                            break;
                        case "garden1":
                            if(gardenSlot4.size()!=0 && gardenSlot1.size()!=0)
                                MoveCard(gardenSlot4,gardenSlot1,garden4,garden1,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden2":
                            if(gardenSlot4.size()!=0 && gardenSlot2.size()!=0)
                                MoveCard(gardenSlot4,gardenSlot2,garden4,garden2,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden3":
                            if(gardenSlot4.size()!=0 && gardenSlot3.size()!=0)
                                MoveCard(gardenSlot4,gardenSlot3,garden4,garden3,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden5":
                            if(gardenSlot4.size()!=0 && gardenSlot5.size()!=0)
                                MoveCard(gardenSlot4,gardenSlot5,garden4,garden5,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden6":
                            if(gardenSlot4.size()!=0 && gardenSlot6.size()!=0)
                                MoveCard(gardenSlot4,gardenSlot6,garden4,garden6,SetBox);
                            else
                                lastClicked = "";
                            break;
                    }
                    if(CompletedSets.size()==13){ //
                        // tell user they win
                        winBox.setText("You win!");
                    }
                }

            }
        });
        garden5.setOnClickListener(new View.OnClickListener() { //Tried to do a general clickListener but it wouldn't recognize the buttons
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (lastClicked.equals("")&&gardenSlot5.size()!=0)
                    lastClicked = "garden5"; //Used to determine which button was clicked last, initialized as Empty String
                else{
                    switch(lastClicked){
                        case "stack":
                            //test if there is nothing in gardenSlot1
                            if(CardStack.size()!=0)
                                MoveCard(gardenSlot5,CardStack,garden5,stack,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked="";

                            break;
                        case "workpile":
                            //test if there is nothing in gardenSlot1
                            if(workPileList.size()!=0)
                                MoveCard(gardenSlot5,workPileList,garden5,workpile,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked = "";

                            break;
                        case "garden1":
                            if(gardenSlot5.size()!=0 && gardenSlot1.size()!=0)
                                MoveCard(gardenSlot5,gardenSlot1,garden5,garden1,SetBox);
                            else
                                lastClicked = "";


                            break;
                        case "garden2":
                            if(gardenSlot5.size()!=0 && gardenSlot2.size()!=0)
                                MoveCard(gardenSlot5,gardenSlot2,garden5,garden2,SetBox);
                            else
                                lastClicked = "";

                            break;
                        case "garden3":
                            if(gardenSlot5.size()!=0 && gardenSlot3.size()!=0)
                                MoveCard(gardenSlot5,gardenSlot3,garden5,garden3,SetBox);
                            else
                                lastClicked = "";

                            break;
                        case "garden4":
                            if(gardenSlot5.size()!=0 && gardenSlot4.size()!=0)
                                MoveCard(gardenSlot5,gardenSlot4,garden5,garden4,SetBox);
                            else
                                lastClicked = "";

                            break;
                        case "garden6":
                            if(gardenSlot5.size()!=0 && gardenSlot6.size()!=0)
                                MoveCard(gardenSlot5,gardenSlot6,garden5,garden6,SetBox);
                            else
                                lastClicked = "";

                            break;

                    }
                    if(CompletedSets.size()==13){ //
                        // tell user they win
                        winBox.setText("You win!");
                    }
                }
            }
        });
        garden6.setOnClickListener(new View.OnClickListener() { //Tried to do a general clickListener but it wouldn't recognize the buttons
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (lastClicked.equals("")&&gardenSlot6.size()!=0)
                    lastClicked = "garden6"; //Used to determine which button was clicked last, initialized as Empty String
                else{
                    switch(lastClicked){
                        case "stack":
                            //test if there is nothing in gardenSlot1
                            if(CardStack.size()!=0)
                                MoveCard(gardenSlot6,CardStack,garden6,stack,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked="";
                            break;
                        case "workpile":
                            //test if there is nothing in gardenSlot1
                            if(workPileList.size()!=0)
                                MoveCard(gardenSlot6,workPileList,garden6,workpile,SetBox);// gardenSlot1, CardStack, garden1, stack,
                            else
                                lastClicked = "";
                            break;
                        case "garden1":
                            if(gardenSlot6.size()!=0 && gardenSlot1.size()!=0)
                                MoveCard(gardenSlot6,gardenSlot1,garden6,garden1,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden2":
                            if(gardenSlot6.size()!=0 && gardenSlot2.size()!=0)
                                MoveCard(gardenSlot6,gardenSlot2,garden6,garden2,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden3":
                            if(gardenSlot6.size()!=0 && gardenSlot3.size()!=0)
                                MoveCard(gardenSlot6,gardenSlot3,garden6,garden3,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden4":
                            if(gardenSlot6.size()!=0 && gardenSlot4.size()!=0)
                                MoveCard(gardenSlot6,gardenSlot4,garden6,garden4,SetBox);
                            else
                                lastClicked = "";
                            break;
                        case "garden5":
                            if(gardenSlot6.size()!=0 && gardenSlot5.size()!=0)
                                MoveCard(gardenSlot6,gardenSlot5,garden6,garden5,SetBox);
                            else
                                lastClicked = "";
                            break;
                    }
                    if(CompletedSets.size()==13){ //
                        // tell user they win
                        winBox.setText("You win!");
                    }
                }

            }
        });

        stack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastClicked.equals(""))
                    lastClicked = "stack";
            }
        });
        deck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DeckList.size()!=0){
                    if(DeckList.size()>=3) {
                        for (int i = 0; i < 3; i++) { //draw three cards to workpile
                            workPileList.add(DeckList.get(DeckList.size() - 1));
                            DeckList.remove(DeckList.size() - 1);
                            //display top card of workpile
                            if (DeckList.size() == 0)
                                deck.setImageResource(res.getIdentifier("c_transparent", "drawable", getApplicationContext().getPackageName()));
                            workpile.setImageResource(res.getIdentifier(workPileList.get(workPileList.size() - 1), "drawable", getApplicationContext().getPackageName()));
                        }
                    }
                    else{
                        for (int i = 0; i < DeckList.size()%3; i++) { //if the deck's size is less than 3 draw however many is left
                            workPileList.add(DeckList.get(DeckList.size() - 1));
                            DeckList.remove(DeckList.size() - 1);
                            //display top card
                            if (DeckList.size() == 0) {
                                deck.setImageResource(res.getIdentifier("c_transparent", "drawable", getApplicationContext().getPackageName()));
                                lastClicked = "";
                            }
                            workpile.setImageResource(res.getIdentifier(workPileList.get(workPileList.size() - 1), "drawable", getApplicationContext().getPackageName()));
                        }
                    }
                }
                else{
                    if(workPileList.size()!=0)
                        DeckList.addAll(workPileList);
                        workPileList.clear();
                        workpile.setImageResource(res.getIdentifier("c_transparent", "drawable", getApplicationContext().getPackageName()));
                        deck.setImageResource(res.getIdentifier("c_b","drawable",getApplicationContext().getPackageName()));
                }
            }
        });
        workpile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastClicked.equals(""))
                    lastClicked = "workpile";
            }
        });
    }
}
