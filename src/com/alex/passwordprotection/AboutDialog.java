package com.alex.passwordprotection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class AboutDialog extends JDialog {

	public AboutDialog() {
		setTitle("About");
		setSize(200, 100);

		// make window in the center of the screen.
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int left = (screenSize.width - getSize().width) / 2;
		int top = (screenSize.height - getSize().height) / 2;
		setLocation(left, top);

		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		JLabel versionTextPane = new JLabel();
		versionTextPane.setText("Version: " + VersionInfo.VERSION);
		add(versionTextPane, BorderLayout.NORTH);

		JLabel releaseDatePane = new JLabel();
		releaseDatePane.setText("Date: " + VersionInfo.RELEASE_DATE);
		add(releaseDatePane, BorderLayout.CENTER);
	}
}
