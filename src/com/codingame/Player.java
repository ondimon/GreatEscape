package com.codingame;

import java.awt.*;
import java.util.*;

public class Player {

    final String WALL_H = "H";
    final String WALL_V = "V";

    final String L = "LEFT";
    final String R = "RIGHT";
    final String U = "UP";
    final String D = "DOWN";

    int playerCount;
    int myId;

    int width;
    int height;

    Unit[] players;
    Wall[] walls;
    Graf notLinkedPoint;

    void play(){

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Player game = new Player();
        game.initGame(in);
        while (true) {
            game.updateGame(in);
            game.play();
        }
    }

    void initGame(Scanner in){
        width = in.nextInt(); // width of the board
        height = in.nextInt(); // height of the board
        playerCount = in.nextInt(); // number of players (2,3, or 4)
        myId = in.nextInt(); // id of my player (0 = 1st player, 1 = 2nd player, ...)
        players = new Unit[playerCount];
        Arrays.fill(players,new Unit());
        notLinkedPoint  = new Graf();
    }

    void updateGame(Scanner in){
        for (int i = 0; i < playerCount; i++) {
            Unit player = players[i];
            player.id = i;
            int x = in.nextInt(); // x-coordinate of the player
            int y = in.nextInt(); // y-coordinate of the player
            player.pos.move(x,y);
            player.wallsleft = in.nextInt();
        }

        int wallCount = in.nextInt(); // number of walls on the board
        walls = new Wall[wallCount];
        for (int i = 0; i < wallCount; i++) {
            int wallX = in.nextInt(); // x-coo0rdinate of the wall
            int wallY = in.nextInt(); // y-coordinate of the wall
            String wallOrientation = in.next(); // wall orientation ('H' or 'V')
            walls[i] = new Wall(new Point(wallX,wallY),wallOrientation);
            if(wallOrientation == WALL_V) {
                notLinkedPoint.addEdge(new Point(wallX, wallY), new Point(wallX-1, wallY));
                notLinkedPoint.addEdge(new Point(wallX, wallY+1), new Point(wallX-1, wallY+1));
            }else{
                notLinkedPoint.addEdge(new Point(wallX, wallY), new Point(wallX, wallY-1));
                notLinkedPoint.addEdge(new Point(wallX+1, wallY), new Point(wallX+1, wallY-1));
            }
        }
    }

    class Wall{
        Point pos;
        String orientation;
        Wall(Point pos,String orientation){
            this.pos = pos;
            this.orientation = orientation;
        }
        @Override
        public String toString() {
            return ""+ pos + " " + orientation;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (obj.getClass() == this.getClass()) {

                Wall wall = (Wall) obj;
                if (wall.pos.equals(this.pos) && wall.orientation.equals(this.orientation) ) return true;
            }
            return false;
        }
    }

    class Unit{
        int id = 0;
        Point pos = new Point();
        int wallsleft = 0;

    }

    class Graf{
        HashMap<Point,ArrayList<Point>> links = new HashMap<Point, ArrayList<Point>>();

        boolean hasVertex(Point vertex) {
            return this.links.containsKey(vertex);
        }

        void addVertex(Point vertex){
            this.links.put(vertex, new ArrayList<Point>());
        }

        void addEdge(Point vertex1,Point vertex2) {
            if(!this.hasVertex(vertex1)) this.addVertex(vertex1);
            if(!this.hasVertex(vertex2)) this.addVertex(vertex2);
            this.links.get(vertex1).add(vertex2);
            this.links.get(vertex2).add(vertex1);
        }

        boolean hasEdge(Point vertex1,Point vertex2) {
            if(!this.hasVertex(vertex1)) return false;
            if(!this.hasVertex(vertex2)) return false;
            return this.links.get(vertex1).contains(vertex2);
        }

        void deleteEdge(Point vertex1,Point vertex2) {
            if(!this.hasVertex(vertex1)) return;
            if(!this.hasVertex(vertex2)) return;
            this.links.get(vertex1).remove(vertex2);
            this.links.get(vertex2).remove(vertex1);
        }

    }
}


