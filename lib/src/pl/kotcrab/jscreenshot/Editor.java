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

package pl.kotcrab.jscreenshot;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import javax.swing.JDialog;
import javax.swing.Timer;

class Editor extends JDialog implements CaptureDialogOption {
	static final int UI_SIZE = 2;

	private ScreenshotListener listener;

	private AbstractCaptureDialog dialog;

	private SelectionArea selectionArea;

	private Timer repaintTimer;

	public Editor (boolean primaryMonitorOnly, ScreenshotListener listener, EditorListener editorListener) {
		this.listener = listener;

		createDialog();
		createGui(primaryMonitorOnly);

		hideCursor();

		setVisible(true);

		startRepainter();
	}

	private void createGui (boolean primaryMonitorOnly) {
		setLayout(new BorderLayout());
		selectionArea = new SelectionArea(this, captureScreen(calculateCaptureRectangle(primaryMonitorOnly)));
		add(selectionArea);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setAlwaysOnTop(true);

		setFocusable(true);
		selectionArea.setFocusable(false);

		setLocation(0, 0);
		pack();

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyDispatcher());
	}

	private Rectangle2D calculateCaptureRectangle (boolean primaryMonitorOnly) {
		Rectangle2D captureRectangle = new Rectangle2D.Float();
		if (primaryMonitorOnly)
			captureRectangle = getPrimaryMonitorBounds();
		else {
			captureRectangle = new Rectangle2D.Double();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			for (GraphicsDevice gd : ge.getScreenDevices()) {
				for (GraphicsConfiguration graphicsConfiguration : gd.getConfigurations()) {
					Rectangle2D.union(captureRectangle, graphicsConfiguration.getBounds(), captureRectangle);
				}
			}
		}

		return captureRectangle;
	}

	private void startRepainter () {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed (ActionEvent evt) {
				repaint();
			}
		};

		repaintTimer = new Timer(15, taskPerformer);
		repaintTimer.start();
	}

	private BufferedImage captureScreen (Rectangle2D initialCaptureRectangle) {
		try {
			return new Robot().createScreenCapture(initialCaptureRectangle.getBounds());
		} catch (AWTException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void createDialog () {
		try {
			Constructor<?> cons = Screenshot.getCaptureDialogClass().getConstructor();
			dialog = (AbstractCaptureDialog)cons.newInstance();
			dialog.setCaptureDialogOption(this);
			dialog.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void capture () {
		dispose();
		listener.screenshotTaken(selectionArea.getSelectedImagePart());
	}

	public void cancel () {
		dispose();
		listener.canceled();
	}

	@Override
	public void dispose () {
		super.dispose();
		dialog.dispose();
		repaintTimer.stop();
	}

	void showCurosr () {
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	void hideCursor () {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		getContentPane().setCursor(blankCursor);
	}

	void showPreCaptureDialog (Rectangle target) {
		MathUtils.shiftBounds(target);

		Insets dialogInsets = dialog.getOffset();

		int screenHeight = selectionArea.getHeight();
		int uiOffset = UI_SIZE / 2;

		int targetTakenHeght = target.y + target.height;
		int dialogRequiredHeight = targetTakenHeght + uiOffset + dialog.getHeight();

		int availableSpaceTop = screenHeight - target.height;

		int dialogX = target.x - uiOffset;
		int dialogY = 0;

		if (dialogRequiredHeight < screenHeight) // is enough space to show dialog in the bottom of the screenshot
			dialogY = target.y + target.height + uiOffset + dialogInsets.top;
		else if (availableSpaceTop > dialog.getHeight()) // is it enough space to show it above the screenshot
			dialogY = target.y - uiOffset - dialog.getHeight() + dialogInsets.bottom;
		else {
			// not enough space, show it inside screenshot
			dialogX += UI_SIZE + dialogInsets.left;
			dialogY = target.y + target.height - UI_SIZE / 2 - dialog.getHeight() + dialogInsets.bottom;
		}

		dialog.setLocation(dialogX, dialogY);
		dialog.setVisible(true);
		showCurosr();
	}

	class KeyDispatcher implements KeyEventDispatcher {
		public boolean dispatchKeyEvent (KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) dispose();
			return false;
		}
	}

	private static Rectangle getPrimaryMonitorBounds () {
		// because gd[0] is not always a primary monitor we must search for it

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // this is definitely primary monitor bounds

		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

		for (int i = 0; i < gd.length; i++) {
			Rectangle bounds = gd[i].getDefaultConfiguration().getBounds();

			if (bounds.width == screenSize.width && bounds.height == screenSize.height) return bounds;
		}

		// Ultimate fallback, if we could not match screenSize to any of the screens, return bounds of gd[0]
		// which may or may not be primary monitor bounds
		if (gd.length > 0) return gd[0].getDefaultConfiguration().getBounds();

		return null;
	}

}
