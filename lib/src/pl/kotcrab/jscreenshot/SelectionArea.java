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

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

/** Selection area, allows for selection parts of an image.
 * @author Pawel Pastuszak */
class SelectionArea extends JLabel {
	private Editor editor;

	private BufferedImage image;
	private BufferedImage imageGrayscale;

	private Rectangle currentRect = null;
	private Rectangle rectToDraw = null;

	private int mouseX;
	private int mouseY;

	private boolean dragged = false;
	private boolean renderCross = true;

	public SelectionArea (Editor editor, BufferedImage image) {
		super(new ImageIcon(image));

		this.editor = editor;
		this.image = image;

		createGrayscale();

		SelectionMouseListener listener = new SelectionMouseListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	private void createGrayscale () {
		imageGrayscale = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = imageGrayscale.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		RescaleOp rescaleOp = new RescaleOp(0.4f, 0, null);
		rescaleOp.filter(imageGrayscale, imageGrayscale);
	}

	private class SelectionMouseListener extends MouseInputAdapter {
		@Override
		public void mousePressed (MouseEvent e) {
			currentRect = new Rectangle(e.getX(), e.getY(), 0, 0);

			renderCross = true;
			dragged = false;
			editor.hideCursor();
		}

		@Override
		public void mouseDragged (MouseEvent e) {
			dragged = true;
			updateSize(e);
			updateMousePos(e);
		}

		@Override
		public void mouseReleased (MouseEvent e) {
			updateSize(e);

			if (dragged == false) {
				Rectangle full = new Rectangle(0, 0, getWidth(), getHeight());
				editor.showPreCaptureDialog(full);
				rectToDraw = full;
			} else
				editor.showPreCaptureDialog(rectToDraw);

			renderCross = false;
		}

		@Override
		public void mouseMoved (MouseEvent e) {
			updateMousePos(e);
		}

		private void updateSize (MouseEvent e) {
			currentRect.setSize(e.getX() - currentRect.x, e.getY() - currentRect.y);
			updateDrawableRect();
		}

		private void updateMousePos (MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}

	private void updateDrawableRect () {
		int x = currentRect.x;
		int y = currentRect.y;
		int width = currentRect.width;
		int height = currentRect.height;

		// Make the width and height positive, if necessary.
		if (width < 0) {
			width = 0 - width;
			x = x - width + 1;
		}

		if (height < 0) {
			height = 0 - height;
			y = y - height + 1;
		}

		// Update rectToDraw after saving old value.
		if (rectToDraw != null) {
			rectToDraw.setBounds(x, y, width, height);
		} else {
			rectToDraw = new Rectangle(x, y, width, height);
		}
	}

	@Override
	protected void paintComponent (Graphics g1) {
		super.paintComponent(g1);

		final Graphics2D g = (Graphics2D)g1.create();
		try {

			g.setStroke(new BasicStroke(Editor.UI_SIZE));
			g.setColor(Screenshot.getUiColor());

			if (rectToDraw != null) {

				Area outside = calculateRectOutside(rectToDraw);
				g.setClip(outside);
				g.drawImage(imageGrayscale, 0, 0, null);
				g.setClip(null);

				g.drawRect(rectToDraw.x, rectToDraw.y, rectToDraw.width, rectToDraw.height);
			}

			if (renderCross) {
				g.drawLine(mouseX, 0, mouseX, getHeight());
				g.drawLine(0, mouseY, getWidth(), mouseY);
			}
		} finally {
			g.dispose();
		}

	}

	private Area calculateRectOutside (Rectangle2D r) {
		Area outside = new Area(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		outside.subtract(new Area(r.getBounds()));
		return outside;
	}

	public BufferedImage getSelectedImagePart () {
		Rectangle r = rectToDraw;
		return image.getSubimage(r.x, r.y, r.width, r.height);
	}
}
