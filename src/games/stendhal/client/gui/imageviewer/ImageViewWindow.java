package games.stendhal.client.gui.imageviewer;

import games.stendhal.client.stendhal;
import games.stendhal.client.gui.InternalManagedWindow;
import games.stendhal.client.gui.j2DClient;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

/**
 * Opens an image resource at a given URL, and displays it in the client.
 * 
 * @author timothyb89
 */
public class ImageViewWindow extends InternalManagedWindow {
	/**
	 * The padding of the window, in pixels, when generating the maximum size.
	 */
	private static final int PADDING = 100;

	public ImageViewWindow(String title, ViewPanel viewPanel) {
		super("examine", title);
		
		viewPanel.prepareView(genMaxSize());
		setContent(viewPanel);
		
		/*
		 * Generating the window was likely triggered an event from the network
		 * thread; do the rest of the work in EDT.
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				j2DClient.get().addWindow(ImageViewWindow.this);
				center();
				getParent().validate();
			}
		});
	}

	/**
	 * Calculate maximum size for a window that fits on the game screen.
	 * 
	 * @return size
	 */
	private Dimension genMaxSize() {
		final int width = (int) (stendhal.screenSize.getWidth() - PADDING);
		final int height = (int) (stendhal.screenSize.getHeight() - PADDING);
		return new Dimension(width, height);
	}
}
