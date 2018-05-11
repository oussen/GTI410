/*
   This file is part of j2dcg.
   j2dcg is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.
   j2dcg is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   You should have received a copy of the GNU General Public License
   along with j2dcg; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.ImageX;
import model.Pixel;
import controller.Caretaker;
import controller.CreateDiscCommand;
import controller.CreateImageCommand;
import controller.CreateRectangleCommand;

/**
 * <p>Title: Menu</p>
 * <p>Description: Class representing the upper menu in the GUI.</p>
 * <p>Copyright: Copyright (c) 2003 Mohammed Elghaouat, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.16 $
 */
public class Menu extends JMenuBar {
  	JMenu addMenu;
  	JMenuItem jAddDisc;
	JMenuItem jAddRect;
	JMenuItem jAddImg;
	
	JMenu helpMenu;
	JMenuItem jAbout;
  	
	private CreateDiscCommand defaultDisc =
	    new CreateDiscCommand(100, 60, new Pixel(0,255,0), 40);
	
	private CreateRectangleCommand defaultRect =
	    new CreateRectangleCommand(51, 51, new Pixel(255,0,0), 100, 100);
	
	private CreateImageCommand defaultImage = new CreateImageCommand(null);
	
	/**
  	 * Default constructor
  	 */
  	public Menu() {
    	addMenu = new JMenu("Add") ;
    	
    	jAddDisc = new JMenuItem("Disc");
    	jAddDisc.addActionListener(new ActionListener(){
         	public void actionPerformed(ActionEvent e){
        		System.out.println("Disc creation : Processing ...");
        		Caretaker.getInstance().execute(defaultDisc.copy());
      		}
    	});
		addMenu.add(jAddDisc);
    	
		jAddRect = new JMenuItem("Rectangle");
	    jAddRect.addActionListener(new ActionListener(){
	      	public void actionPerformed(ActionEvent e){
        		System.out.println("Rectangle creation : Processing ...");
        		Caretaker.getInstance().execute(defaultRect.copy());
	      	}
	    });
		addMenu.add(jAddRect);
	    
		jAddImg = new JMenuItem("Image");
		jAddImg.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
	 			JFileChooser chooser = new JFileChooser();
	  			chooser.setFileFilter(new ImageX.ImageXExtensionsFilter());
	   			chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
	   			chooser.setVisible(true);
	        			
	   			if ((chooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION)) {
	   				CreateImageCommand newCommand = (CreateImageCommand)defaultImage.copy();
	   				newCommand.setFilename(chooser.getSelectedFile().getAbsolutePath());
	           		Caretaker.getInstance().execute(newCommand);
	   			}   		
			}
		});
		addMenu.add(jAddImg);
		
		JMenuItem jExit = new JMenuItem("Exit");
		jExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});	
		addMenu.add(jExit);
		
		helpMenu = new JMenu("Help") ;		
		jAbout = new JMenuItem("About");
		jAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String title = "j2dcg informations"; 
				String text  = "j2dcg: Java 2D Computer Graphics and Imaging Framework.\n"
							 + "This Software is GPL.\n"
							 + "http://j2dcg.sourceforge.net\n";
				JOptionPane.showMessageDialog(Application.getInstance(), text, title, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(jAbout);
		
    	add(addMenu);
    	add(helpMenu);
  	}
}