import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.util.Random;

public class MainGame implements ActionListener {

    public int sizeOfDiagram = 15;
    private boolean runHit = false;

    private int[][] neighbourCount = new int[sizeOfDiagram][sizeOfDiagram] ;
    private boolean[][] nodesOfDiagram = new boolean[sizeOfDiagram][sizeOfDiagram];
    private boolean[][] nextNodesOfDiagram = new boolean[sizeOfDiagram][sizeOfDiagram];
    // trues implies alive, false implies dead

    Container grid = new Container();
    Random rand = new Random();



    JFrame frame = new JFrame("Map");
    JButton reset = new JButton("reset");
    JButton run = new JButton("run");
    JButton[][] buttons = new JButton[sizeOfDiagram][sizeOfDiagram];

    public MainGame(int size){
        sizeOfDiagram = size;
        initializeFrameAndButtons(); // with the sizeOfDiagram


    }
    public MainGame(){
        initializeFrameAndButtons();
    }

    private int generateRandom(){

        return rand.nextInt(8);
    }


    private void initializeFrameAndButtons(){

        System.out.println("Initializing the diagram...");
        frame.setSize(800,800);
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        frame.add(run, BorderLayout.SOUTH);

        reset.addActionListener(this);
        run.addActionListener(this);

        grid.setLayout(new GridLayout(sizeOfDiagram,sizeOfDiagram));

        for(int a=0;a <this.sizeOfDiagram; a++) {
            for (int b = 0; b < this.sizeOfDiagram; b++) {
                buttons[a][b] = new JButton();
                buttons[a][b].addActionListener(this);
                buttons[a][b].setText("");
                grid.add(buttons[a][b]);
            }
        }
        frame.add(grid,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    public static void main(String args[]){

        //initialize the default diagram
        MainGame thisGame = new MainGame();


        while (true){

            if (thisGame.runHit == true){
                thisGame.countNeighbours();
            }
            try
            {
                Thread.sleep(300);
            }
            catch(InterruptedException ex)
            {
                System.out.println("exception occurred");
                Thread.currentThread().interrupt();
            }
        }






    }

    public void countNeighbours(){ // recursive function that keeps the thread running once run is clicked

        neighbourCount = new int[sizeOfDiagram][sizeOfDiagram];

        System.out.println("counting neighbours....");

        for (int i = 0; i < sizeOfDiagram; i++) {

            for (int j = 0; j < sizeOfDiagram; j++) {



                try {
                    if (nodesOfDiagram[i][j + 1] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                try {
                    if (nodesOfDiagram[i][j - 1] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                try {
                    if (nodesOfDiagram[i+1][j] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                try {
                    if (nodesOfDiagram[i-1][j] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                try {
                    if (nodesOfDiagram[i+1][j + 1] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                try {
                    if (nodesOfDiagram[i-1][j - 1] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                try {
                    if (nodesOfDiagram[i-1][j + 1] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                try {
                    if (nodesOfDiagram[i+1][j - 1] == true) {
                        neighbourCount[i][j]++;

                    }
                }
                catch (IndexOutOfBoundsException e){ // border check
                }
                // to print the count on each cell
                //buttons[i][j].setText(neighbourCount[i][j]+"");

            }
        } // all the nodes are counted

        implementRules();

        updateGraph();


    }

    public void updateGraph(){

        System.out.println("updating graph....");

        nodesOfDiagram = nextNodesOfDiagram;
        nextNodesOfDiagram = new boolean[sizeOfDiagram][sizeOfDiagram];

        for (int i = 0; i < sizeOfDiagram; i++) {
            for (int j = 0; j < sizeOfDiagram; j++) {
                if(nodesOfDiagram[i][j]== true){
                    buttons[i][j].setText("X"); // X implies living
                }
                else {
                    buttons[i][j].setText(" "); // " " implies dead

                }
            }

        }
    }

    private void implementRules(){ // takes neighbour count and nodesOfDiagram and computes the nextNodesOfDiagram

        System.out.println("implementing rules....");

        for (int i = 0; i < sizeOfDiagram; i++) {


            for (int j = 0; j < sizeOfDiagram; j++) {

                if (neighbourCount[i][j] < 2 && nodesOfDiagram[i][j] == true) {// live cell dies
                    nextNodesOfDiagram[i][j] = false;
                }
                if (neighbourCount[i][j] == 2 && nodesOfDiagram[i][j] == true) {// live cell survives
                    nextNodesOfDiagram[i][j] = true;
                }
                if (neighbourCount[i][j] == 3 && nodesOfDiagram[i][j] == false) {// dead cell comes back alive
                    nextNodesOfDiagram[i][j] = true;
                }
                if (neighbourCount[i][j] == 3 && nodesOfDiagram[i][j] == true) {// live cell survives
                    nextNodesOfDiagram[i][j] = true;
                }

                if (neighbourCount[i][j] > 3 && nodesOfDiagram[i][j] == true) {// live cell dies
                    nextNodesOfDiagram[i][j] = false;
                }




            }

        }


    }




    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(reset)) { // fix reset

            System.out.println("Reset was hit...");
            MainGame thisGame = new MainGame();

        }
        else if (event.getSource().equals(run)){
            System.out.println("Run was hit...");
            runHit = true;

        }

        else { // a button was hit
            for(int x=0;x<sizeOfDiagram;x++){
                for(int y=0;y<sizeOfDiagram;y++){

                    if(event.getSource().equals(buttons[x][y])){ // Checking if button has been pressed
                        System.out.println("A button was hit");
                        // mark the nodesOfDiagram as Hit or True
                        nodesOfDiagram[x][y] = true;
                        buttons[x][y].setText("X");


                    }
                }
            }

        }

    }



}
