/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kosta Nikopoulos
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Inputing the default information
        Scanner console = new Scanner(System.in);
        int x = 0;
        int y = 0;
        int itrCount = 0;
        int totalScore = 300;
        //will print the results of the robot moving from cell to cell throughout the game
        do {            
            displayInfo(x, y, itrCount, totalScore);
            char direction = inputDirection();
            //will give a penalty if robot moves in a certain direction
            if(direction == 'u') {
                y++;
                totalScore -= 10;
            }
            if (direction == 'd') {
                y--;
                totalScore -= 50;
            }
            if(direction == 'l') {
                x++;
                totalScore -= 50;
            }
            if (direction == 'r') {
                x--;
                totalScore -= 50;
            }
            //will give the robot a penalty if it goes over the set boundaries
            if (doesExceed(x, y, direction)) {
                totalScore -= 2000;
                System.out.println("Exceed boundary, -2000 damage applied");
                if (y > 4) y--;
                if (y < 0) y--;
                if (x > 4) x++;
                if (x < 0) x--;   
            }
            else{
                int reward = reward();
                reward = punishOrMercy(direction, reward);
                totalScore += reward;
            }
            System.out.println();
            itrCount++;
        //will print the user's name and the total amount of points that they have 
        } while (!(isGameOver(x, y, totalScore, itrCount)));
            evaluation(totalScore);
    }
    /**
     * To have a character inputed and to tell the robot where to go
     * @param x the x-coordinate that the robot is at
     * @param y the y-coordinate that the robot is at
     * @param totalScore the total accumulated number of points
     * @param itrCount number of steps
     */   
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {
        System.out.println("For point (X=" + x + ", Y=" + y + ") at iterations: " 
                + itrCount + "the total score is: " + totalScore);
    }
    /**
     *To determine if the coordinates the robot has to go to 
     * are exceed the limits of the project grid
     * @param x the x-coordinate that the robot is at
     * @param y the y-coordinate that the robot is at
     * @param direction the direction the robot will move
     * @return if the robot exceeds the grid then true if not then false
     */
    public static boolean doesExceed(int x, int y, char direction) {   
        if(y > 4 && direction == 'u')
            return true;
        else if(x < 0 && direction == 'd')
            return true;
        else if(y < 4 && direction == 'l')
            return true;
        else if(x > 4 && direction == 'r')
            return true;
        return false;
    }
    /**
     * To reward or punish the robot for landing in a cell
     * @return the number of points you either gained or lost
     */
    public static int reward() {
        Random rand = new Random();
        int dice = rand.nextInt(6) + 1;
        rand.nextInt();
        switch(dice){
            case 1: System.out.print("Dice: 1, reward: -100");
            return -100;
            case 2: System.out.print("Dice: 2, reward: -200");
            return -200;
            case 3: System.out.println("Dice: 3, reward: -300");
            return -300;
            case 4: System.out.println("Dice: 4, reward: 300");
            return 300;
            case 5: System.out.println("Dice: 5, reward: 400");
            return 400;
            case 6: System.out.println("Dice: 6, reward: 600");
            return 600;
        }
       return reward();
    }
    /**
     * To give the robot a chance to skip the punishment if it receives it
     * @param direction the direction the robot is going in
     * @param reward the number of points the robot will get or lose
     * @return if mercy the loss is ignored, if no mercy the loss is applied
     */
    public static int punishOrMercy(char direction, int reward){
        Random rand = new Random();
        int coinFlip = rand.nextInt(2);
        if(direction == 'u' && coinFlip == 0 && reward < 0) {
            System.out.println("Coin: Tails | Mercy, negative reward is removed.");
            return 0;
        } else if(direction == 'u' && coinFlip == 1 && reward < 0) {
            System.out.println("Coin: Heads | No mercy, negative reward remains.");
            return reward;
        } else
            return reward;
    }
    /**
     * To capitalize the first letter of the first and last names of the user's 
     * @param str the users full name
     * @return the TitleCased version of the user's name
     */
    public static String toTitleCase(String str) {
        String fname = str.substring(0, str.indexOf(' '));
        String lname = str.substring(str.indexOf(' ') + 1);
        String tFName = (fname.substring(0,1)).toUpperCase()
                + (fname.substring(1).toLowerCase());
        String tLName = (lname.substring(0,1)).toUpperCase()
                + (lname.substring(1).toLowerCase());
        String tName = tFName + "" + tLName;
        return tName;               
    }
    /**
     * To calculate the total score of the user at the end of the game
     * @param totalScore the total numbers of points that the user amassed
     */
    public static void evaluation(int totalScore) {
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter your name(only two words): ");
        String name = console.nextLine();
        if (totalScore >= 2000) 
          System.out.print("Victory! " + toTitleCase(name) 
                  + ",your score is" + totalScore);   
        else
          System.out.println("Mission Failed! " + toTitleCase(name)
                  + ",your score is" + totalScore);
    }
    /**
     * To make sure that the robot can only 
     * move in the preset directions(up, down, left , & right)
     * @return the matching letter to each of the 4 directions set 
     */
    public static char inputDirection() {
        Scanner console = new Scanner(System.in);
        System.out.print("Please input a valid direction: ");
        char direction = Character.toLowerCase(console.next().charAt(0));
        if(direction == 'u' || direction == 'd' ||
                direction == 'l' || direction == 'r')
            return direction;
        else
            return inputDirection();
    }
    /**
     * To check if the game is over
     * @param x  the x-coordinate that the robot is at
     * @param y  the y-coordinate the robot is at
     * @param totalScore the total numbers of points gathered
     * @param itrCount the number of steps taken
     * @return 
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount) {      
        if(itrCount >= 20){
            return true;            
        }else if (x == 4 && y == 4 || x == 0 && y == 4) {
            return true;
        }else if (totalScore < -1000) {
                return true;
        }else if (totalScore >= 2000) {
                return true;
        }            
        return false;
    }    
}