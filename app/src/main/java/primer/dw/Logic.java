package primer.dw;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by primer on 6/19/16.
 */

public class Logic {



    public static int length_of_lists = 10;
    public static int number_of_lists = 5;
    static int[][][] cards = {{{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8}, {0, 9}},
                              {{1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}, {1, 8}, {1, 9}},
                              {{2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8}, {2, 9}},
                              {{3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9}},
                              {{4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9}}};


    public static void isWinning(){

    }

    public static void prepare(){

    }

    public static void reset(){
        int[][][] c2 = {{{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8}, {0, 9}},
                {{1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}, {1, 8}, {1, 9}},
                {{2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8}, {2, 9}},
                {{3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9}},
                {{4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9}}};
        cards = c2;
    }

    public static boolean isEmpty(int list, int pos){
        if (cards[list][pos][1] == 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidSwap(int source, int source_index, int destination, int destination_index){
        if (! isEmpty(destination, destination_index)){
            return false;
        }
        else if(destination_index == 0 & cards[source][source_index][1] != 1){
            return false;
        }
        else if(destination_index == 0 & cards[source][source_index][1] == 1){
            return true;
        }
        int source_color = cards[source][source_index][0];
        int source_number = cards[source][source_index][1];

        int pre_destination_color = cards[destination][destination_index-1][0];
        int pre_destination_number = cards[destination][destination_index-1][1];

        if (source_color == pre_destination_color & pre_destination_number == source_number -1){
            return true;
        }
        else{
            return false;
        }
    }

    public static void swap(int source, int source_index, int destination, int destination_index){
        int s = source;
        int si = source_index;

        int d = destination;
        int di = destination_index;

        int d0 = cards[d][di][0];
        int d1 = cards[d][di][1];

        cards[d][di] = cards[s][si];
        cards[s][si] = new int[] {d0, d1};
    }

    public static void shuffle(){
        Random randomizer = new Random();

        for (int i = 0; i < number_of_lists; i++){
            for (int j = 0; j < length_of_lists; j ++){

                int d_list = randomizer.nextInt(number_of_lists);
                int d_pos = randomizer.nextInt(length_of_lists);

                swap(i, j, d_list, d_pos);
            }
        }
    }

    public static void printCards(){
        for(int[][] list: cards){
            for (int[] card: list){
                System.out.print(card[0] + "-" + card[1] + " ");
            }
            System.out.println();
        }
    }
}