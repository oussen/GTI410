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
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import model.Document;
import controller.Dispatcher;

/**
 * <p>Title: Application</p>
 * <p>Description: Class managing the runtime application instance</p>
 * <p>Copyright: Copyright (c) 2003 Mohammed Elghaouat, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.22 $
 */
public class Application extends JFrame {
  
  	private Window window;
  	private Menu menu;
  	private Document doc;
	
	private JPanel parametersPanel;
	
  	static Application theInstance;
  	
	/**
	 * Default constructor.
	 */
  	public Application() {}
  	
	/**
	 * Get the active document.
	 * @return active document instance
	 */
	public Document getActiveDocument(){
		return doc;
	}
	
	/**
	 * Get the parameter panel. 
	 * @return
	 */
	public JPanel getParametersPanel(){
		return parametersPanel;
	}  	

	/**
	 * Get the current instance of the application.
	 * @return current instance of the application
	 */
  	static public Application getInstance(){
		return theInstance;
  	}
  	
  	/**
  	 * Launches the application
  	 */
  	private void launchApplication(){
  		doc = new Document();
		Dispatcher dispatcher = new Dispatcher();
    	window = new Window(dispatcher);
	    menu = new Menu();
	    JPanel objectsPanel = window;

		ButtonsPanel buttonsPanel = new ButtonsPanel(dispatcher);
		buttonsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		parametersPanel = new JPanel();
		parametersPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		parametersPanel.setLayout(new CardLayout());

		parametersPanel.add(new ColorParameterPanel(doc), "ColorParameterPanel");
		parametersPanel.add(new FillTransformation(dispatcher.getTransformersIndex().getTheFiller()), "FillPanel");
		parametersPanel.add(new FilterKernelPanel(dispatcher.getTransformersIndex()), "FilterKernelPanel");
		parametersPanel.add(new CurvesPanel(dispatcher), "CurvesPanel");
		parametersPanel.add(new TranslationTransformation(dispatcher.getTransformersIndex()), "TranslationTransformation");
		parametersPanel.add(new ScalingTransformation(dispatcher.getTransformersIndex()), "ScalingTransformation");
		parametersPanel.add(new ShearingTransformation(dispatcher.getTransformersIndex()), "ShearingTransformation");
		parametersPanel.add(new RotationTransformation(dispatcher.getTransformersIndex()), "RotationTransformation");
		
		getContentPane().add(parametersPanel, BorderLayout.NORTH);
		getContentPane().add(buttonsPanel, BorderLayout.WEST);
	    getContentPane().add(window, BorderLayout.CENTER);
	    
	    setJMenuBar(menu);
	    
	    setTitle("j2dcg");    
	    setSize(800, 600);
	    setVisible(true);
    	
    	addWindowListener(new WindowAdapter() { 
	    	public void windowClosing(WindowEvent e) {
	        	System.exit(0);
	        }
	    });
  	}
  	
  	/**
  	 * MAIN
  	 * @param args main's parameters
  	 */
  	public static void main(String[] args) {
    	theInstance = new Application();
    	theInstance.launchApplication();
  	}
}