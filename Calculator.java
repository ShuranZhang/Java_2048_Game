//Author: Shuran Zhang
//Partner: None
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.sound.sampled.*;
import java.io.*;
public class Calculator implements KeyListener {
	GameFrame frame;
	Random r = new Random();
	//initialize the valid move as zero at first 
	public int move=0;
	Clip clip;
	//start method of the game
	public void start() {  
		//play the background music whenever the game starts
		try {
			//The background music was download at the url:
			//https://freesound.org/people/Mrthenoronha/sounds/369920/download/369920__mrthenoronha__cartoon-game-theme-loop.wav
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("BGM.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception x) { x.printStackTrace(); }
		//set the new frame and add a random 2/4 on it when the game starts
		frame = new GameFrame();
		addRandom(frame.list);
		frame.repaint();
		//add key listener to the frame
		frame.addKeyListener(this); 
	}
	//method for checking if the game is over and adding a random 2 or 4 to the board
	private void addRandom( ArrayList<ArrayList<Integer>> list) {
		//control variable that prevent the program from adding a random number on the square 
		//that already contains a different number
		boolean random =true;
		//control variable to check if the game is over
		int lose=0; 
		//if the number on the 4x4 list is zero, then the ineteger increment by one
		for (int y = 0; y < list.size(); y++) {  
			for (int x = 0; x < list.size(); x++) {  
				if (list.get(y).get(x) == 0) {  
					lose++;  
				}  
			} 
		}
		//if there is still zero on the board, which means there is still space left,
		//add a random 2/4 to the board
		if (lose!= 0) {
			while(random==true) {
				//int index = r.nextInt(temp.size());  
				int posY = r.nextInt(4);  
				int posX = r.nextInt(4);  
				ArrayList<Integer> sublist = frame.list.get(posY);
				if(sublist.get(posX)==0) {
					if(Math.random()>=0.2) {
						sublist.set(posX, 2);
					}else {
						sublist.set(posX, 4);
					}
					random=false;
					break;
				}
				frame.list=list;  

			}} else { 
				//if there is no zero on the list, which means there is no space left to add, a random number, the game is over
				//ask the user if they want to start a new game after the game over by a dialogue box				
				JOptionPane.showMessageDialog(null, "Your Total Valid Move is: "+move+" Your Maximum Number on the Board is: "+calculateSum(frame.list));
				int dialogResult = JOptionPane.showConfirmDialog (null, "Do You Want To Start a New Round","",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION){
					move=0;
					start();
				}else{
					//exit the program if the use wants to quit
					System.exit(1);
				}


			} 

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		//play the sound effect if either arrow key is pressed
		if(e.getKeyCode()==37||e.getKeyCode()==38||e.getKeyCode()==39||e.getKeyCode()==40) {
			try {
				//The sound effect of key pressing was download at the url:
				//http://soundbible.com/761-Switch.html
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("KeyPress.wav").getAbsoluteFile());
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			}catch(Exception x) { x.printStackTrace(); }
		}
		if(e.getKeyCode()==82) {
			System.out.println("restart");
			int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure You Want To Restart?","",JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION){
				move=0;
				//stop the audio clip when restart the game
				clip.stop();
				start();
			}
		}else if(e.getKeyCode()==81) {
			//if user presses r, asks the user if they want to restart the game by a dialogue box
			int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure You Want To Quit?","",JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(null, "Your Total Valid Move is: "+move+" Your Maximum Number on the Board is: "+calculateSum(frame.list));
				System.exit(0);
			}
		}else if(e.getKeyCode()==40) {
			//the move method if they user presses down arrow key
	        int p;// index of rows  
	        int value;//the current value   
	        for (int j = 0; j <4; j++) {
	        	//start with the lower most row and set the initial current value as zero
	            p=3;  
	            value=0;  
	            for (int i = 3; i >=0; i--){  
	            	//get the horizontal sublist of the 2D array list from up to down
	            	//get the horizontal sublist2 of the 2D array list from down to up
	            	ArrayList<Integer>sublist=frame.list.get(i);
	            	ArrayList<Integer>sublist2=frame.list.get(p);	
	                if (frame.list.get(i).get(j) > 0){//if the number is not zero  
	                	// add if the two near by numbers are equal  
	                    if(frame.list.get(i).get(j)==value){  
	                    		//update the number
	                        int sum=value*2;  
	                        sublist.set(j, 0);  
	                        sublist2.set(j, sum);
	                        value=0;
	                        //move one row up 
	                        p--;  
	                    }else{ 
	                    		//if the non zero number can't add, stay the same
	                        if(value>0){  
	                            sublist2.set(j,value);  
	                            p--;  
	                        } 
	                        //update the current value to the next element in the array
	                        value=frame.list.get(i).get(j);
	                        sublist.set(j, 0);  
	                    }  
	                }  
	            }
	            ArrayList<Integer>sublist3=frame.list.get(p);
	            //if the value still contains number at last, keep it where it is
	            if(value>0) 
	               sublist3.set(j, value);  
	        }
	        //repaint the canvas
			frame.repaint();
			//check if the game is over after every move. If not, add a random number
			addRandom(frame.list);
			//increment the valid move and print the user action
			move++;
			System.out.println("Down Pressed.Made a Valid Move and Current Valid Move is: "+move+" Current Max Number: "+calculateSum(frame.list) ); 
	    //move down method has same logic with move up    
		}else if(e.getKeyCode()==38){
		        int p;//index of rows 
		        int value;//current value  
		        for (int j = 0; j <4; j++)  
		        {  
		            p=0;  
		            value=0;  
		            for (int i = 0; i <4; i++)   
		            {
		            	ArrayList<Integer>sublist=frame.list.get(i);
		            	ArrayList<Integer>sublist2=frame.list.get(p);
		               
		                if (frame.list.get(i).get(j) > 0)  
		                {  
		                    if(frame.list.get(i).get(j)==value)//相加放p位置  
		                    {  
		                        int sum=value*2;  
		                        sublist.set(j, 0);  
		                        sublist2.set(j, sum);  
		                        value=0;  
		                        p++;  
		                    }  
		                    else{  
		                        if(value>0){  
		                            sublist2.set(j, value);  
		                            p++;  
		                        }  
		                        value=frame.list.get(i).get(j);  
		                        sublist.set(j,0);  
		                    }  
		                }  
		            } 
		            ArrayList<Integer>sublist3=frame.list.get(p);
		            if(value>0)// 
		                sublist3.set(j,value);  
		        }  

			frame.repaint();
			addRandom(frame.list);
			move++;
			System.out.println("Up Pressed. Made a Valid Move and Current Valid Move is: "+move+" Current Max Number: "+calculateSum(frame.list) );
		        
		}else if(e.getKeyCode()==37) {
		        int p;//index of colums  
		        int value;//current value 
		        //start from the the first row
		        for (int i = 0; i < 4; i++){
		        	//get each row from top to bottom
	            	ArrayList<Integer>sublist=frame.list.get(i);
		            p=0;  
		            value=0;
		            //start from the left digit to the right
		            for (int j = 0; j < 4; j++){  
		            	if (frame.list.get(i).get(j) > 0){  
		                    if(frame.list.get(i).get(j)==value){//only add if two near numbers are same  
		                        int sum=value*2;  
		                        sublist.set(j, 0);  
		                        sublist.set(p, sum);  
		                        value=0;  
		                        p++;//more to next digit   
		                    }else{  
		                        if(value>0){//number does not change if it can't add  
		                            sublist.set(p, value);  
		                            p++;  
		                        }
		                        //update the current value
		                        value=frame.list.get(i).get(j);  
		                        sublist.set(j, 0);  	                          
		                    }  
		                }  
		            }  
		            if(value>0)
		            	//if there is still number left at last, keep it same
		               sublist.set(p,value);  
		        }
		    //repaint the frame
			frame.repaint();
			//check if the game is over. If not, add a random number
			addRandom(frame.list);
			//increment the valid move if the random number is successfully added and print it
			move++;
			System.out.println("Left Pressed.Made a Valid Move and Current Valid Move is: "+move+" Current Max Number: "+calculateSum(frame.list) ); 
		 //move right method has the same logic with moving right method   
		}else if(e.getKeyCode()==39) {
	        int p;//index of colum  
	        int value;//current value 
	        for (int i = 0; i <4; i++){  
            	ArrayList<Integer> sublist=frame.list.get(i);
	            p=3;  
	            value=0;  
	            for (int j = 3; j >= 0; j--){  
	                if (frame.list.get(i).get(j) > 0){  
	                    if(frame.list.get(i).get(j)==value)//相加放p位置  
	                    {  
	                        int sum=value*2;  
	                        sublist.set(j, 0);  
	                        sublist.set(p, sum);  
	                        value=0;  
	                        p--;  
	                    }else{  
	                        if(value>0)//value放p中，当前值放value中  
	                        {  
	                            sublist.set(p, value); 
	                            p--;  
	                        }  
	                        value=frame.list.get(i).get(j);  
	                        sublist.set(j, 0);  
	                    }  
	                }  
	            }  
	            if(value>0) 
	                sublist.set(p, value);  
	        }
			frame.repaint();
			addRandom(frame.list);
			move++;
			System.out.println("Right Pressed. Made a Valid Move and Current Valid Move is: "+move+" Current Max Number: "+calculateSum(frame.list) );	        
		}
	}
	//method for cauculating the sum in order to print the maximum number on the board everytime
	public int calculateSum(ArrayList<ArrayList<Integer>> list) {
		int sum=0;
		for(ArrayList<Integer> col:list) {
			for(int digit:col) {
				sum+=digit;
			}
		}
		return sum;
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
