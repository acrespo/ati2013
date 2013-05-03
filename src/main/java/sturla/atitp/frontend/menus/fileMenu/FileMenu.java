package sturla.atitp.frontend.menus.fileMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import sturla.atitp.app.ImageSaver;
import sturla.atitp.frontend.MainFrame;

public class FileMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainFrame mainFrame;
	
	public FileMenu(MainFrame parent){
		super("File");
		
		this.mainFrame = parent;
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		JMenuItem chooseFileItem = new JMenuItem("Choose Image");
		chooseFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(FileMenu.this);
				mainFrame.setCurrentImage(fc.getSelectedFile());
			}
		});
		JMenuItem chooseFile2Item = new JMenuItem("Choose Second Image");
		chooseFile2Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(FileMenu.this);
				mainFrame.setSecondImage(fc.getSelectedFile());
			}
		});
		JMenuItem save = new JMenuItem("Save result");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File file = new File("out.bmp");
				try {
				  ImageSaver.saveImage(file, mainFrame.resultImageLabel.getImage());
				} catch (Exception e) {
					System.out.println("Error");
				}
			}
		});
		JMenuItem swap = new JMenuItem("Result->Input");
		swap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (mainFrame.resultImageLabel.hasImage()) {
					mainFrame.currentImageLabel.setImage(mainFrame.resultImageLabel.getImage());
					mainFrame.resultImageLabel.removeImage();
				}
			}
		});
		this.add(chooseFileItem);
		this.add(chooseFile2Item);
		this.add(save);
		this.add(swap);
		this.add(exitItem);
	}

}
