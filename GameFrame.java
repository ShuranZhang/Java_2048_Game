/*Name: Shuran Zhang
Partner: None
 */

import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
//The basic GUI frame with the drawing canvas
public class GameFrame extends JFrame{
	//Initialize the 2D Array List and the location of each small square
	protected ArrayList<ArrayList<Integer>> list=new ArrayList<ArrayList<Integer>>() ;
	int posX;
	int posY;
	public GameFrame() {
		//set up the JFrame 
		this.setTitle("彭于晏女朋友写的2048");
		this.setSize(500,490);
		//add the canvas to the frame
		this.setLayout(new FlowLayout());
		add();
		this.add(new canvas());
		this.setVisible(true);
		//make the frame pop up in the middle of the screen
		this.setLocation(330, 120);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	//initialize the array list with all zeros to avoid null pointer exception
	public void add() {
		list.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
		list.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
		list.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
		list.add(new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
	}

	//canvas class that draws all the small squares and numbers on the,
	protected class canvas extends JPanel{
		//set the preferred size for the canvas.
		public Dimension getPreferredSize() {
			return new Dimension(500, 500);
		}
		public void paintComponent(Graphics g) {
			g.setColor(Color.LIGHT_GRAY);  
			g.fillRect(0, 0, 500, 500);  
			g.setFont(new Font("Tmes New Roman", Font.BOLD, 25));  
			for (int y = 0; y < 4; y++) {  
				for (int x = 0; x < 4; x++) {
					//draw the 16 small squares
					int posX=x * 100 + (x * 5) + 40;
					int posY=y * 100 + (y * 5) + 10;
					switch(list.get(y).get(x)) {
					//change the color of the small square based on the number on it. 
					case 0:  
						g.setColor(Color.white);  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  
						break; 
					case 2:  
						//color information for different number's background from 
						//https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java
						g.setColor(new Color(0xeee4da));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  
						break;
					case 4:
						g.setColor(new Color(0xede0c8));  
						g.fillRoundRect(posX, posY,100, 100, 30, 30);  

						break;
					case 8:
						g.setColor(new Color(0xf2b179));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 16: 
						g.setColor(new Color(0xf59563));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 32: 
						g.setColor(new Color(0xf67c5f));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 64: 
						g.setColor(new Color(0xf67c5f));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 128:
						g.setColor(new Color(0xedcf72));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 256:
						g.setColor(new Color(0xedcc61));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 512: 
						g.setColor(new Color(0xedc850));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 1024: 
						g.setColor(new Color(0xedc53f));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					case 2048: 
						g.setColor(new Color(0xedc22e));  
						g.fillRoundRect(posX, posY, 100, 100, 30, 30);  

						break;
					default:
						g.setColor(Color.BLUE);  
	                    g.fillRoundRect(posX, posY, 100, 100, 30, 30); 
	                    break;

					}
					//draw the number as string in the middle of each small square
						g.setColor(Color.BLACK);
						if (list.get(y).get(x) != 0) {  

							g.drawString(list.get(y).get(x) + "", x * 100 + (x * 5) + 85, y  
									* 100+ (y * 5) + 75);  
						}  
						//System.out.print(list.get(y).get(x) + "\t");  
						}  
					System.out.println();  
				}  
				System.out.println("Next Move----------");  
			}
		
		}

	//start the game
	public static void main(String [] args) {
		new Calculator().start();
	}



}
