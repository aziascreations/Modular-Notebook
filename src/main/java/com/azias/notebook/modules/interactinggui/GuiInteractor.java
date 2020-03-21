package com.azias.notebook.modules.interactinggui;

import com.azias.notebook.Notebook;
import com.azias.notebook.interactors.INotebookInteractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class GuiInteractor implements INotebookInteractor {
	private final static Logger logger = LoggerFactory.getLogger(GuiInteractor.class);
	
	// Ou window
	private JFrame window;
	private JPanel mainPanel;
	private JButton testButton;
	
	public GuiInteractor() {
		window = new JFrame();
		//mainPanel = new JPanel();
	}
	
	@Override
	public boolean initialize() {
		window.setBounds(0,0,500,500);
		window.setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		
		testButton = new JButton("Test01");
		mainPanel.add(testButton);
		
		window.add(mainPanel);
		window.setVisible(true);
		
		logger.info("{} was initialized !", this.getClass().getSimpleName());
		return true;
	}
	
	@Override
	public boolean update(Notebook app, long delta) {
		logger.info("{} was ticked and purposefully returned false !", this.getClass().getSimpleName());
		return false;
	}
	
	@Override
	public boolean close() {
		logger.info("{} was closed !", this.getClass().getSimpleName());
		return true;
	}
	
	@Override
	public boolean hasToInteract() {
		return false;
	}
}
