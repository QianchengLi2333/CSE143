//<Qiancheng Li>
//<01/22/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #3>
//
//A class to represent the AssassinManager, which keeps track of 
//who is stalking whom and the history of who killed whom in games 
//of Assassin.
import java.util.*;

public class AssassinManager {
    private AssassinNode killRing;
    private AssassinNode graveyard;

    //Construct a new assassin manager over the given list of people.
    //Throw an exception if the list is empty.
    //Parameter:
    //    List<String> names-a list of strings where names of players in this game are stored.
    public AssassinManager(List<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }
        killRing = new AssassinNode(names.get(0));
        AssassinNode current = killRing;
        for (int i = 1; i < names.size(); i++) {
            current.next = new AssassinNode(names.get(i));
            current = current.next;
        }
    }

    //Print the names of the people in the kill ring as “X is stalking Y."
    //If the game is over, print “X is stalking X”
    public void printKillRing() {
        AssassinNode current = killRing;
        while (current.next != null) {
            System.out.println("    " + current.name + " is stalking " + current.next.name);
            current = current.next;
        }
        System.out.println("    " + current.name + " is stalking " + killRing.name);
    }

    //Print the names of the people in the graveyard in the form “X was killed by Y.”
    //Names are printed in the opposite of the order in which victimes were assassinated.
    //Produce no output if the graveyard is empty.
    public void printGraveyard() {
        AssassinNode current = graveyard;
        while (current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }  

    //Check if the kill ring contains the given name (case-insensitively).
    //Return true if the given name is in the current kill ring and false otherwise.
    //Parameter:
    //    String name-the name to be checked.
    public boolean killRingContains(String name) {
        return listContains(killRing, name);
    } 

    //Check if the graveyard contains the given name (case-insensitively).
    //Return true if the given name is in the current graveyard and false otherwise.
    //Parameter:
    //    String name-the name to be checked.
    public boolean graveyardContains(String name) {
        return listContains(graveyard, name);
    }

    //Check if the game is over.
    //Return true if the game is over and false otherwise.
    public boolean gameOver() {
        return (killRing.next == null);
    }

    //Show the winner of this game.
    //Return the name of the winner of the game, or null if the game is not over yet.
    public String winner() {
        if (gameOver()) {
            return killRing.name;
        } else {
            return null;
        }
    }

    //Record the assassination of the person with the given name, 
    //transferring the person from the kill ring to the front of the graveyard.
    //Ignore case in comparing names.
    //Throw exceptions if the game is over, or if the given name is not part of the kill ring.
    //Parameter:
    //    String name-the name of the person who is going to be killed
	public void kill(String name) {
        if (gameOver()) {
            throw new IllegalStateException();
        }
		else if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        }
        AssassinNode current = killRing;
        AssassinNode dead = null;
        if (current.name.equalsIgnoreCase(name)) {
            dead = current;
            while (current.next != null) {
                current = current.next;
            }
            killRing = dead.next;
        } else {
            while (current.next != null && !current.next.name.equalsIgnoreCase(name)) {
                current = current.next;
            }
		    dead = current.next;
	    	current.next = dead.next;
        }
        dead.killer = current.name;
        dead.next = graveyard;
        graveyard = dead;
    }

    //Check if this list contains the given name (case-insensitively).
    //Return true if the given name is in this list and false otherwise.
    //Parameter:
    //    AssassinNode list-the list to be checked if it contains the name
    //    String name-the name to be checked.
    private boolean listContains(AssassinNode list, String name) {
        AssassinNode current = list;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                return true;
            } else {
                current = current.next;
            }
        }
        return false;
    } 
}
