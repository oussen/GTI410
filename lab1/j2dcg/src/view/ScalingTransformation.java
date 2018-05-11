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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.AnchoredTransModel;

import controller.TransformersIndex;

/**
 * <p>Title: ScalingTransformation</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.5 $
 */
public class ScalingTransformation extends JPanel{
	/**
	 * 
	 */
	private JLabel xLabel;
	/**
	 * 
	 */
	private JTextField xTextField;
	/**
	 * 
	 */
	private JLabel yLabel;
	/**
	 * 
	 */
	private JTextField yTextField;
	/**
	 * 
	 */	 
	private JLabel relativeToLabel;
	/**
	 * 
	 */
	private JComboBox orientationComboBox;
	/**
	 * 
	 */
	private JButton applyButton;
	/**
	 * 
	 */
	private TransformersIndex ti;
	/**
	 * 
	 *
	 */
	public ScalingTransformation(TransformersIndex ti) {
		this.ti = ti;
		//setLayout(new GridLayout(2,2));
		setLayout(new FlowLayout());
		
		xLabel = new JLabel("X");
		xTextField = new JTextField(10);
		
		xTextField.addFocusListener(
			new FocusListener() {
				public void focusGained(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					String text   = tf.getText();		
					tf.select(0,text.length());	
				}
				public void focusLost(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					float n = Float.parseFloat(tf.getText());
					ScalingTransformation.this.ti.getTheScale().setXScale(n);					
				}
		});
					
		yLabel = new JLabel("Y");
		yTextField = new JTextField(10);
		
		yTextField.addFocusListener(
			new FocusListener() {
				public void focusGained(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					String text   = tf.getText();		
					tf.select(0,text.length());			
				}
				public void focusLost(FocusEvent fe) {
					JTextField tf = (JTextField)fe.getSource();
					float n = Float.parseFloat(tf.getText());
					ScalingTransformation.this.ti.getTheScale().setYScale(n);					
				}
		});
					
		relativeToLabel = new JLabel("Relative to");
		orientationComboBox = new JComboBox(AnchoredTransModel.ORIENTATIONS);
		orientationComboBox.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					JComboBox cb = (JComboBox)ae.getSource();
					String s = (String)cb.getSelectedItem();
					int a = cb.getSelectedIndex();
					ScalingTransformation.this.ti.getTheScale().setOrientation(a);
			}	
		});
							
		applyButton = new JButton("Apply");
		applyButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					ScalingTransformation.this.ti.getTheScale().execute();
			}	
		});
		
		setDefaultScaleValues();
				
		add(xLabel);
		add(xTextField);
		
		add(yLabel);
		add(yTextField);
		
		add(relativeToLabel);
		add(orientationComboBox);
		
		add(applyButton);
	}
	
	/**
	 * 
	 *
	 */
	private void setDefaultScaleValues(){
		float x = ScalingTransformation.this.ti.getTheScale().getXScale();
		float y = ScalingTransformation.this.ti.getTheScale().getYScale();
		
		xTextField.setText(Float.toString(x));
		yTextField.setText(Float.toString(y));
	}
}
