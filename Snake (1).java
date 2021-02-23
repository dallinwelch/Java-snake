
//score to work
//if he touches himself he loses
//boarder for if  he loses


//Imports code for window creation
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends UserCode{
   //Variables go here
   Random ran = new Random();
   ArrayList<Point> length;
   //int SnakeX = 30;
   //int SnakeY =30;
   int foodX = ran.nextInt(30)*30;
   int foodY = ran.nextInt(30)*30; 
   
   final static int STOP = 0;
   final static int UP = 1;
   final static int DOWN = 2;
   final static int RIGHT = 3;
   final static int LEFT = 4;
   
   private int direction = STOP;
   
  
   String message = "press any key to begin ";
   double timer = 0;
  
   double Score= 0;
   
            
 
       
   
   private Random generator = new Random();
   
   //Initializes the project
   public void init(){
      length = new ArrayList<Point>(100);
      length.add(new Point(420,420));
      length.add(new Point(420,450));
      length.add(new Point(420,480));
      length.add(new Point(420,510));
     
      System.out.println("sdf" + length.size());
     
      Window.WINDOW_WIDTH = 1000;
      Window.WINDOW_HEIGHT = 1000;
      Window.WINDOW_TITLE = "Snake";
      Window.TIMER_SPEED = (int)(100 - (Score +5));
   }
   
   //Put graphics code here
	public void draw(Graphics g){
      g.drawString(message,10,30);
            
      for(Point p: length){
         g.fillRect((int)p.getX(),(int)p.getY(),30,30);
          

      }

      
      g.drawString("Time: "+ timer,30,10);
      g.drawString("Score: "+ Score,200,10);
      g.fillRect(foodX,foodY,20,20);
          

         

   
   }
   
   //Code to respond to a mouse press
	public void mousePressed(MouseEvent mouse){
				
	}
	
   //Code to respond to a keyboard press
	public void keyPressed(KeyEvent key){
 
	}
	
   public void keyUp(KeyEvent w){

      
   }
  //Code to respond to a keyboard press
	public void keyReleased(KeyEvent key){ 

    if (key.getKeyCode() == KeyEvent.VK_W){
         //SnakeY -= 10;
         direction = UP;
         
      }else if(key.getKeyCode() == KeyEvent.VK_S){
        // SnakeY +=10;
         direction = DOWN;
      }
      else if(key.getKeyCode() == KeyEvent.VK_A){
        // SnakeX -=10;
         direction = LEFT;
      }else if(key.getKeyCode() == KeyEvent.VK_D){
         direction = RIGHT;
         
      }
      	
   }
   
   
   
   //Code executes every half second
	public void timer_tick(){
   timer += .05;
   Score += (timer * .005);
   
  
     
            
   
            Point head = length.get(0);
              int x =(int) head.getX();
              int y = (int)head.getY(); 
            Point body;
            
            
      for(int i = 3; i< length.size(); i++){
         body = length.get(i);
         if(head.equals(body) && timer > 5){
            System.out.println(head + " " + body + " " + i);
         x = 500;
         y = 500;
         Window.TIMER_SPEED = 10000;
         JOptionPane.showMessageDialog(null, "Game Over");
         }
      }
   
      
      if(x >  Window.WINDOW_WIDTH || x < 0 ){
         x = 500;
         Window.TIMER_SPEED = 10000;
         JOptionPane.showMessageDialog(null, "Game Over");
      }
      if(y >  Window.WINDOW_HEIGHT || y <0){
          y = 500;
          Window.TIMER_SPEED = 10000;
          JOptionPane.showMessageDialog(null, "Game Over");
          
      }
   
               
               if(direction == UP){
                        y-=30;          
               }else if(direction == DOWN){
                        y+=30;          
               }else if(direction == LEFT){
                        x-=30;          
               }else if(direction == RIGHT){
                        x+=30;          
               }else{
                               }
               
               
            length.set(0,new Point(x, y));
  // System.out.println(length.get(0));
   
           for(int i= length.size() -1; i>0;i--){
               Point prev = length.get(i-1);
               
               length.set(i, prev);
               

           }
           
            Point food = new Point(foodX, foodY);

           
          if( length.get(0).equals(food)){
            
            Score++;
            foodX = ran.nextInt(30)*30;
            foodY = ran.nextInt(30)*30;
           //First push everything back one,
            
            length.add(0, food);
         }
           
           
	}
   
   //Starts the program
	public static void main(String args[]){
	    new Snake();
	}
}


/*
--------------------------------------------------------------------------------------------------
Window Code Below
Creates a window and implements the user's code
--------------------------------------------------------------------------------------------------
*/
abstract class UserCode {
   //Constructor, creates the window
   public UserCode(){
      new Window(this);
   }
     
   //Initialize the program
   public abstract void init();
   
   //Draw
	public  abstract void draw(Graphics g);
   
   //mouse press
	public  abstract void mousePressed(MouseEvent mouse);
	
   //keyboard press
	public  abstract void keyPressed(KeyEvent key);
	
   //keyboard press
	public  abstract void keyReleased(KeyEvent key);   
   //Code executes every half second
	public  abstract void timer_tick();

}
class Window extends JPanel implements Runnable{
	
   public static int WINDOW_WIDTH = 800;   
   public static int WINDOW_HEIGHT = 500;
   public static String WINDOW_TITLE = "Window Name";
   public static int TIMER_SPEED = 200;
   
   //Adds the user code to the window                                    
	private UserCode myCode;
	
	private Thread thread;
	
	public Window(UserCode e){
      myCode = e;
   	//Initializes user code
		myCode.init();
   
		//Creates the window and title
		JFrame win = new JFrame(WINDOW_TITLE);
      //Closes window when X is pressed
      win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Size of the window
		win.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		//Make sure to show the window
		win.setVisible(true);
		//Adds a panel for graphics
		win.add(this);
      win.setVisible(true);
	

		
		//Creates a thread for the timer_tick
	     thread = new Thread (this, "gameloop");
	     //Starts the timer
        thread.start ();
	     
     
		//Adds keyboard functionality to the window
		win.addKeyListener(new KeyListener(){

			//Method for when a user presses a key
			@Override
			public void keyPressed(KeyEvent arg0) {
				//Executes the code in the user's file
				myCode.keyPressed(arg0);
				//Refreshes any changes on the window
				repaint();
               //Code to respond to a keyboard press
	      }
			//Method for when a user releases a key (unused)
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				myCode.keyReleased(arg0);
            repaint();
				
			}
			//Method for when a user types a key (unused)
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//Adds mouse functionality
		this.addMouseListener(new MouseListener(){
		
		//Event for when the user presses down a mouse button
		 public void mousePressed(MouseEvent e) {
			 myCode.mousePressed(e);
			 repaint();
		 }
		//Event for when the user clicks down a mouse button
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		//Event for when the user's mouse enters the area (unused)
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		//Event for when the user's mouse exits the area (unused)
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		//Event for when the mouse button is released
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		});
	}
	//Draws any graphics from MyCode
	public void paintComponent(Graphics g){
      //Refreshes the screen
      super.paintComponent(g);
      //Draw's the user's code
		myCode.draw(g);
	}
	
 
	//Game loop, executes the Timer_tick method every half milisecond
	@Override
	public void run() {
		//continuously runs this code until the program ends
		while(true){
			try {
				//Slows the code down to every 50 milliseconds to reduce flickering
				Thread.sleep(TIMER_SPEED);
				myCode.timer_tick();
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
