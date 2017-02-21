package com.app.guiComponents;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.app.staticEngine.AppEnums.*;
import com.app.staticEngine.AppEnums.E_JFileChooserrMode;


/**
 *  @author AliAfzal
 * This class allows us to select an option on the main game window.
 * Options Available in File Menu: Item Editor , Character Editor , Map Editor , Campaign Editor and Exit
 * Options Available in Help Menu: About
 */
public class JFileChooserComponent {

	/**
	 * The Constructor
	 * 
	 * @param new_fileChooserMode
	 *            the mode of a file open
	 * @return a file
	 */
	public JFileChooser getJFileChooser(E_JFileChooserrMode new_fileChooserMode) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		if (new_fileChooserMode == E_JFileChooserrMode.Open) {
			fileChooser.setDialogTitle("Durgeons & Dragons .dd file");
		} else {
			fileChooser.setDialogTitle("Durgeons & Dragons Save");
		}
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Durgeons & Dragons", "dd","ddc"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		return fileChooser;
	}
}
