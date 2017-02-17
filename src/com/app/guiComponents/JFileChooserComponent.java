package com.app.guiComponents;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.app.staticEngine.AppEnums.*;


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
			fileChooser.setDialogTitle("D&D Select .ddm file");
		} else {
			fileChooser.setDialogTitle("D&D Map Save");
		}
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tower Defence Map", "ddm"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		return fileChooser;
	}
}
