import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game_Panel extends JPanel implements ActionListener, KeyListener {
    public static final int TABLE_WIDTH=1000;
    public static final int TABLE_HEIGHT=(int)(TABLE_WIDTH*(0.5555));
    public static final int Middle=(int)(TABLE_WIDTH/2)-15;
    int score1,score2;
    boolean player1wins;
    int ballspeed;
    Timer timer;

    //PADDLES

    //1. Positions
    double paddle1x,paddle1y;
    double paddle2x,paddle2y;
    int ballx,bally;
    int balldirectionx,balldirectiony;
    Random random;
    public Game_Panel(){
        this.setPreferredSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        start_game();
    }
    public void start_game(){
        timer=new Timer(10,this);
        random=new Random();

        //initialize the scores
        score1=0;
        score2=0;
        player1wins=false;

        //initialize a new ball
        newball();

        //initialize paddle positions
        paddle1x=0;
        paddle1y=(int)(TABLE_HEIGHT/2)-50;

        paddle2x=TABLE_WIDTH-25;
        paddle2y=(int)(TABLE_HEIGHT/2)-50;
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //check for winner
        if(score1==5){
            player1wins=true;
            gameover(g);
        }
        else if(score2==5){
            player1wins=false;
            gameover(g);
        }
        else {
            //draw the middle boundary
            g.drawLine(Middle, 0, Middle, 600);

            //draw the scores
            g.setColor(Color.PINK);
            g.setFont(new Font("INK FREE", Font.BOLD, 40));
            g.drawString(score1 + "", Middle - 30, 30);
            g.drawString(score2 + "", Middle + 10, 30);

            //DRAW PADDLES
            g.setColor(Color.GREEN);
            g.fillRect((int) paddle1x, (int) paddle1y, 10, 60);
            g.setColor(Color.RED);
            g.fillRect((int) paddle2x, (int) paddle2y, 10, 60);

            //DRAW THE BALL
            g.setColor(Color.white);
            g.fillOval(ballx, bally, 20, 20);
        }
    }
    public void newball(){
        random=new Random();
        //inital ball position
        bally=random.nextInt(500);
        ballx=Middle-10; //ball diameter subtracted (10)

        //ball speed
        ballspeed=2;

        //intial ball direction

        // for x dir
        int dirx= random.nextInt(2);
        if(dirx==0){
            balldirectionx=-1;
        }
        else{
            balldirectionx=1;
        }
        // for y dir
        int diry= random.nextInt(2);
        if(diry==0){
            balldirectiony=-1;
        }
        else{
            balldirectiony=1;
        }
    }
    public void moveball(){

        //check for collision with paddles
        //for paddle1
        if(ballx<=paddle1x+10 && bally>=paddle1y && bally<=paddle1y+60){
            ballspeed++;
            balldirectionx=balldirectionx*(-1);
        }
        //for paddle2
        if(ballx>=paddle2x-20 && bally>=paddle2y && bally<=paddle2y+60){
            ballspeed++;
            balldirectionx=balldirectionx*(-1);
        }
        //check for upper and bottom wall collision
        if(bally<=0 || bally>=TABLE_HEIGHT-50){
            balldirectiony=balldirectiony*(-1);
        }
        //check for left and right wall collision
        if(ballx<=0){
            score2++;
            newball();
        }
        if(ballx>=1000){
            score1++;
            newball();
        }

        ballx+=(balldirectionx)*(ballspeed);
        bally+=(balldirectiony)*(ballspeed);
    }
    public void gameover(Graphics g){
        timer.stop();
        g.setColor(Color.PINK);
        g.setFont(new Font("CONSOLAS", Font.BOLD, 40));
        if(player1wins){
            g.drawString("Player 1 Wins ",Middle-100,300);
        }
        else{
            g.drawString("Player 2 Wins ",Middle-100,300);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        moveball();
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //CONTROLS FOR PLAYER 1
        if(e.getKeyCode()==KeyEvent.VK_A){
            if(paddle1y<=0){
                paddle1y=0;
            }
            else{
                paddle1y-=15;
                repaint();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_Z){
            if(paddle1y>=TABLE_HEIGHT-100){
                paddle1y=TABLE_HEIGHT-100;
            }
            else{
                paddle1y+=15;
                repaint();
            }
        }

        //CONTROLS FOR PLAYER 2
        if(e.getKeyCode()==KeyEvent.VK_UP){
            if(paddle2y<=0){
                paddle2y=0;
            }
            else{
                paddle2y-=15;
                repaint();
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            if(paddle2y>=TABLE_HEIGHT-100){
                paddle2y=TABLE_HEIGHT-100;
            }
            else{
                paddle2y+=15;
                repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
