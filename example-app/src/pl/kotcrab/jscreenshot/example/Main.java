/**
 * Copyright 2014 Pawel Pastuszak
 * 
 * This file is part of Screenshoter.
 * 
 * Arget is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Arget is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Arget.  If not, see <http://www.gnu.org/licenses/>.
 */
package pl.kotcrab.jscreenshot.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.kotcrab.jscreenshot.Screenshot;
import pl.kotcrab.jscreenshot.ScreenshotAdapter;

public class Main {

	public static void main (String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		Screenshot.setCaptureDialogClass(CaptureDialog.class);
		
		Screenshot.take(new ScreenshotAdapter() {

			@Override
			public void screenshotTaken (BufferedImage image) {
				saveToFile(image);
			}

			private void saveToFile (BufferedImage image) {
				JDialog dummyParent = new JDialog();
				
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				fc.setFileFilter(new FileNameExtensionFilter("PNG (*.png)", ".png"));
				int returnVal = fc.showSaveDialog(dummyParent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile().getAbsolutePath();
					if (path.endsWith(".png") == false) path += ".png";

					try {
						ImageIO.write(image, "png", new File(path));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				dummyParent.dispose(); //that will dispose JFileChooser as well
			}

		});
	}

}
