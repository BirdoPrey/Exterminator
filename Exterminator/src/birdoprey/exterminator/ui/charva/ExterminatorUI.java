package birdoprey.exterminator.ui.charva;

import birdoprey.exterminator.ExterminatorApp;
import charva.awt.BorderLayout;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charvax.swing.JButton;
import charvax.swing.JFrame;

//The top-level JFrame - when this is closed, the application quits
//All other windows need to be spawned from here, or a child
public class ExterminatorUI extends JFrame {

	public ExterminatorUI (ExterminatorApp app) {
		
		initialize();

	}
	
	
	private void initialize() {
	
		//a simple start
		JButton helloButton = new JButton("WHY HELLO THERE!");
		
		helloButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
	
		getContentPane().add(helloButton, BorderLayout.CENTER);
		
		pack();
		
	}

}
