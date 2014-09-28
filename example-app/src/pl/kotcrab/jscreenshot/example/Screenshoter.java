/*******************************************************************************
 * Copyright 2014 Pawel Pastuszak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package pl.kotcrab.jscreenshot.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.kotcrab.jscreenshot.Screenshot;
import pl.kotcrab.jscreenshot.ScreenshotAdapter;

public class Screenshoter {

	public static void main (String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		Screenshot.take(true, new ScreenshotAdapter() {

			@Override
			public void screenshotTaken (BufferedImage image) {
				saveToFile(image);
			}

			private void saveToFile (BufferedImage image) {

				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				fc.setDialogTitle("Specify a file to save");
				fc.setAcceptAllFileFilterUsed(false);
				fc.setFileFilter(new FileNameExtensionFilter("PNG (*.png)", ".png"));
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile().getAbsolutePath();
					if (path.endsWith(".png") == false) path += ".png";

					try {
						ImageIO.write(image, "png", new File(path));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

		});
	}

}
