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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.ShearingTransformationModel;
import controller.TransformersIndex;

/**
 * <p>Title: ShearingTransformation</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 Sébastien Bois, Eric Paquette</p>
 * <p>Company: (ÉTS) - École de Technologie Supérieure</p>
 * @author unascribed
 * @version $Revision: 1.14 $
 */
public class ShearingTransformation extends JPanel {
	/**
	 * 
	 */
	private JRadioButton shearX;
	/**
	 * 
	 */
	private JRadioButton shearY;
	/**
	 * 
	 */
	private ButtonGroup group;
	/**
	 * 
	 */
	private JLabel valueLabel;
	/**
	 * 
	 */
	private JTextField valueAngleTextField;
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
	public ShearingTransformation(TransformersIndex ti){
		this.ti = ti;
		//setLayout(new GridLayout());
		setLayout(new FlowLayout());
		
		// mediator
		group = new ButtonGroup();
		
		valueLabel = new JLabel("Angle (degrees)");
		valueAngleTextField = new JTextField(10);
		
		valueAngleTextField.addFocusListener(
			new FocusListener(){
				public void focusGained(FocusEvent fe){
					JTextField tf = (JTextField)fe.getSource();
					String text   = tf.getText();		
					tf.select(0,text.length());		
				}
				public void focusLost(FocusEvent fe){
					JTextField tf = (JTextField)fe.getSource();
					float n = Float.parseFloat(tf.getText());
					ShearingTransformation.this.ti.getTheShear().setShearAngleDegree(n);
				}	
		});
					
		relativeToLabel = new JLabel("Relative to");
		orientationComboBox = new JComboBox(ShearingTransformationModel.ORIENTATIONS);
		orientationComboBox.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					JComboBox cb = (JComboBox)ae.getSource();
					int a = cb.getSelectedIndex();
					ShearingTransformation.this.ti.getTheShear().setOrientation(a);
				}	
			});
								
		shearX = new JRadioButton(ShearingTransformationModel.SHEAR_X);
		shearX.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					ShearingTransformation.this.ti.getTheShear().setShearType(ae.getActionCommand());
				}	
			});
						
		shearY = new JRadioButton(ShearingTransformationModel.SHEAR_Y);
		shearY.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					ShearingTransformation.this.ti.getTheShear().setShearType(ae.getActionCommand());
				}	
			});
					
		applyButton = new JButton("Apply");
		applyButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ae) {
					ShearingTransformation.this.ti.getTheShear().execute();
				}	
			});
		
		setDefaultShearType();
		setDefaultShearAngleDegree();
		
		group.add(shearX);
		group.add(shearY);
		
		add(valueLabel);
		add(valueAngleTextField);
		
		add(relativeToLabel);
		add(orientationComboBox);
				
		add(shearX);
		add(shearY);
		
		add(applyButton);
	}
	
	/**
	 * 
	 *
	 */
	private void setDefaultShearType(){
		String ds = ShearingTransformation.this.ti.getTheShear().getShearType();
		String sx = ShearingTransformationModel.SHEAR_X;
		String sy = ShearingTransformationModel.SHEAR_Y;
		
		if(ds.equalsIgnoreCase(sx))
			shearX.setSelected(true);
		else
			shearY.setSelected(true);	
	}
	
	/**
	 * 
	 *
	 */
	private void setDefaultShearAngleDegree(){
		float f = ShearingTransformation.this.ti.getTheShear().getShearAngleDegrees();
		valueAngleTextField.setText(Float.toString(f));	
	}
}
