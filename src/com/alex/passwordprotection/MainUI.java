package com.alex.passwordprotection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainUI extends JFrame {
	private static final String TAG = "MainUI";

	private JLabel mAccountTextLabel;
	private JTextArea mResulTextArea;
	private ArrayList<JButton> mSelectedButtons = new ArrayList<JButton>();

	private Password mPassword;
	private File[] mPasswordFiles;
	private File mCurrentPasswordFile;

	private Color DEFAULT_BUTTON_COLOR;

	public MainUI() {
		setLayout(new BorderLayout());
		setTitle("Password Protection");
		setSize(480, 320);

		// make window in the center of the screen.
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int left = (screenSize.width - getSize().width) / 2;
		int top = (screenSize.height - getSize().height) / 2;
		setLocation(left, top);

		mPasswordFiles = PasswordManager.getPasswordFiles();

		if (mPasswordFiles != null && mPasswordFiles.length > 0) {
			for (File file : mPasswordFiles) {
				if (file.getName().equals(PasswordPreference.loadPassordFile())) {
					mCurrentPasswordFile = file;
				}
			}
			if (mCurrentPasswordFile == null) {
				// No preference, use the first.
				mCurrentPasswordFile = mPasswordFiles[0];
			}

		} else {
			Log.e(TAG, "No password file");
		}

		loadPassword();
		initUI();
		createMenu();
		updateUI();
	}

	private void updateUI() {
		if (mPassword == null) {
			mAccountTextLabel.setText("No password file!");
		} else {
			mAccountTextLabel.setText(mPassword.getAccount());
		}

		mResulTextArea.setText("");

		for (JButton button : mSelectedButtons) {
			button.setBackground(DEFAULT_BUTTON_COLOR);
		}
		mSelectedButtons.clear();
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		if (mPasswordFiles != null && mPasswordFiles.length > 0) {
			JMenu fileMenu = new JMenu("File");
			for (File file : mPasswordFiles) {
				JMenuItem menuItem = new JMenuItem(file.getName());
				final File passowordFile = file;
				menuItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						mPassword = new Password(passowordFile);
						mPassword.loadPassword();
						updateUI();
						saveChoice(passowordFile);
					}

				});
				fileMenu.add(menuItem);
			}
			menuBar.add(fileMenu);
		}
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutJMenuItem = new JMenuItem("About");
		aboutJMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AboutDialog aboutDialog = new AboutDialog();
				aboutDialog.setVisible(true);
			}
		});
		helpMenu.add(aboutJMenuItem);

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
	}

	private void saveChoice(File passowordFile) {
		PasswordPreference.save(passowordFile);
	}

	private void loadPassword() {
		if (mCurrentPasswordFile != null) {
			mPassword = new Password(mCurrentPasswordFile);
			mPassword.loadPassword();
		}

	}

	private void initUI() {
		add(createResultPanel(), BorderLayout.NORTH);
		add(createPasswordButtonsPanel(), BorderLayout.CENTER);
	}

	private Component createPasswordButtonsPanel() {
		GridLayout gridLayout = new GridLayout(Password.ROW, Password.COLUMN);
		gridLayout.setHgap(2);
		gridLayout.setVgap(2);
		JPanel panel = new JPanel(gridLayout);
		// i * j buttons
		for (int i = 0; i < Password.ROW; i++) {
			for (int j = 0; j < Password.COLUMN; j++) {
				final JButton button = new JButton(Password.ROW_ID[i]
						+ Password.COLUMN_ID[j]);
				final int row = i;
				final int column = j;

				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						if (mSelectedButtons.size() == 3) {
							// select 3 password at most
							return;
						} else if (mSelectedButtons.contains(button)) {
							// no repeat
							return;
						} else {
							mSelectedButtons.add(button);
							mResulTextArea.append(mPassword.getPassword(row,
									column));
							button.setBackground(Color.GREEN);
						}
					}
				});
				panel.add(button);
			}
		}
		return panel;
	}

	private JPanel createResultPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		mAccountTextLabel = new JLabel("");
		panel.add(mAccountTextLabel);

		final JButton button = new JButton("Clear");
		DEFAULT_BUTTON_COLOR = button.getBackground();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (JButton selectedButton : mSelectedButtons) {
					selectedButton.setBackground(DEFAULT_BUTTON_COLOR);
				}
				mSelectedButtons.clear();
				mResulTextArea.setText("");
			}
		});
		panel.add(button);

		mResulTextArea = new JTextArea("");
		mResulTextArea.setEditable(false);
		panel.add(mResulTextArea);
		return panel;
	}

	public static void main(String[] args) {
		MainUI mainUI = new MainUI();
		mainUI.setVisible(true);
	}

	@Override
	protected void processWindowEvent(WindowEvent event) {
		super.processWindowEvent(event);
		if (event.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}
}
