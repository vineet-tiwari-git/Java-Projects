package com.vineet.ssldebugger;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.vineet.ssldebugger.ui.TransposterView;
import com.vineet.ssldebugger.ui.UIComponents;

/**
 * Main class for SSLDebuggerStarter. 
 *
 */
public class SSLDebuggerStarter {

	public final static boolean	RIGHT_TO_LEFT	= false;

	public void run() {

		final JFrame frame = UIComponents.createJFrame(" SSL Debugger ", -1, -1, false);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().removeAll();
		final TransposterView vw = new TransposterView();
		final JPanel panel = vw.createUI();

		System.setOut(new PrintStream(new TextAreaOutputStream(vw.getJTextAreaConsoleForConsole())));
		System.setErr(new PrintStream(new TextAreaOutputStream(vw.getJTextAreaConsoleForConsole())));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setResizable(Boolean.FALSE);
		frame.pack();

		// Center the window on the screen
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(final String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SSLDebuggerStarter().run();;
			}
		});
	}
}
