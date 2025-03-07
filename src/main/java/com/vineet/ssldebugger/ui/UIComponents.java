package com.vineet.ssldebugger.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class UIComponents {

	/**
	 * Creates a {@link JTextField}
	 *
	 * @param text
	 * @param width
	 * @param height
	 * @return
	 */
	public static final JTextField createJTextField(final String text, final int width, final int height) {

		final JTextField textField = new JTextField("test");

		textField.setText(text);

		if ((width > 0) && (height > 0)) {
			textField.setPreferredSize(new Dimension(width, height));
		}

		return textField;
	}

	public static final JPasswordField createJPasswordField(final String text, final int width, final int height) {

		final JPasswordField passwordField = new JPasswordField();
		passwordField.setText(text);
		if ((width > 0) && (height > 0)) {
			passwordField.setPreferredSize(new Dimension(width, height));
		}
		return passwordField;
	}

	/**
	 * Creates a {@link JTextArea}
	 *
	 * @param text
	 * @param width
	 * @param height
	 * @param rows
	 * @param cols
	 * @param editable
	 * @return
	 */
	public static final JTextArea createJTextArea(final String text, final int width, final int height, final int rows, final int cols, final boolean editable) {
		final JTextArea area = new JTextArea();
		area.setText(text);
		if ((width > 0) && (height > 0)) {
			area.setPreferredSize(new Dimension(width, height));
		}
		if (rows > 0) {
			area.setRows(rows);
		}
		if (cols > 0) {
			area.setColumns(cols);
		}
		area.setEditable(editable);
		return area;
	}

	/**
	 * Creates a {@link JButton}
	 *
	 * @param text
	 * @param width
	 * @param height
	 * @return
	 */
	public static final JButton createJButton(final String text, final int width, final int height) {

		final JButton button = new JButton(text);
		if ((width > 0) && (height > 0)) {
			button.setPreferredSize(new Dimension(width, height));
		}
		return button;
	}

	/**
	 * Creates a {@link JFrame}
	 *
	 * @param text
	 * @param width
	 * @param height
	 * @param borderLayout
	 * @return
	 */
	public static final JFrame createJFrame(final String text, final int width, final int height, final boolean borderLayout) {
		final JFrame frame = new JFrame();
		frame.setTitle(text);

		if ((width > 0) && (height > 0)) {
			frame.setPreferredSize(new Dimension(width, height));
		}
		if (borderLayout) {
			frame.setLayout(new BorderLayout());
		}

		return frame;
	}

	/**
	 * Creates a {@link JPanel}
	 *
	 * @param width
	 * @param height
	 * @param borderLayout
	 * @return
	 */
	public static final JPanel createJPanel(final int width, final int height, final boolean borderLayout) {

		final JPanel panel = new JPanel();

		if ((width > 0) && (height > 0)) {
			panel.setPreferredSize(new Dimension(width, height));
		}
		if (borderLayout) {
			panel.setLayout(new BorderLayout());
		}
		return panel;

	}

	/**
	 * Creates a {@link JFileChooser}
	 *
	 * @param workingDirectory
	 * @param fileSelectionMode
	 * @param multiselect
	 * @return
	 */
	public static final JFileChooser createJFileChooser(final String workingDirectory, final int fileSelectionMode, final boolean multiselect) {
		final JFileChooser fileChooser = new JFileChooser(workingDirectory);
		if (fileSelectionMode > -1) {
			fileChooser.setFileSelectionMode(fileSelectionMode);
		}
		fileChooser.setMultiSelectionEnabled(multiselect);
		return fileChooser;
	}
	
	/**
	 * Creates a {@link JCheckBox}
	 * @param text
	 * @param width
	 * @param height
	 * @param checked
	 * @return
	 */
	public static final JCheckBox createJCheckBox(final String text, final int width, final int height, final boolean checked) {
		final JCheckBox checkBox = new JCheckBox();
		
		checkBox.setText(text);
		checkBox.setToolTipText(text);
		
		if ((width > 0) && (height > 0)) {
			checkBox.setPreferredSize(new Dimension(width, height));
		}
		
		checkBox.setSelected(checked);
		
		return checkBox;
	}

	/**
	 * Creates a {@link JLabel}
	 *
	 * @param text
	 * @param textColor
	 * @return
	 */
	public static final JLabel createJLabel(final String text, final Color textColor) {

		final JLabel label = new JLabel(text);
		label.setForeground(textColor);
		return label;

	}

	/**
	 * Creates a {@link JSeparator}
	 *
	 * @param orientation
	 * @param bgColor
	 * @return
	 */
	public static final JSeparator createJSeparator(final int orientation, final Color bgColor) {
		final JSeparator separator = new JSeparator(orientation);
		separator.setBackground(bgColor);
		return separator;
	}
}
