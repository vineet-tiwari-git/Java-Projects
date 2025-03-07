package com.vineet.ssldebugger.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.vineet.ssldebugger.bean.TransportRequest;
import com.vineet.ssldebugger.bean.TransporterOptions;
import com.vineet.ssldebugger.constant.TransportConstants;
import com.vineet.ssldebugger.constant.TransportRequestFieldNames;
import com.vineet.ssldebugger.debug.Transporter;
import com.vineet.ssldebugger.ui.util.Util;


public class TransposterView {

	private JTextArea	consoleOutput	= null;

	private String		browseLocation	= null;

	public JTextArea getJTextAreaConsoleForConsole() {
		return this.consoleOutput;
	}

	private Border createTitledBorder(final String titleNote) {
		final Font italicHelpFont = new Font("Dialog", Font.ITALIC, 10);
		final TitledBorder title = BorderFactory.createTitledBorder(titleNote);
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitleColor(Color.BLACK);
		title.setTitleFont(italicHelpFont);
		return title;
	}

	public JPanel createUI() {

		final Font italicHelpFont = new Font("Dialog", Font.ITALIC, 10);

		// Endpoint
		final JTextField propertyText = UIComponents.createJTextField("all", 340, 27);
		final JLabel propertyLabel = new JLabel(TransportConstants.JAVAX_NET_DEBUG_VM_ARGUMENT);
		final JPanel propertyPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.X_AXIS));
		propertyPanel.add(propertyLabel);
		propertyPanel.add(Box.createHorizontalStrut(5));
		propertyPanel.add(propertyText);
		propertyPanel.add(Box.createHorizontalStrut(6));
		propertyPanel.setBorder(this.createTitledBorder("default value is all"));

		// Endpoint
		final JTextField endpointText = UIComponents.createJTextField(null, 340, 27);
		final JLabel endpointLabel = new JLabel("Endpoint * ");
		final JPanel endpointPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		endpointPanel.setLayout(new BoxLayout(endpointPanel, BoxLayout.X_AXIS));
		endpointPanel.add(endpointLabel);
		endpointPanel.add(Box.createHorizontalStrut(5));
		endpointPanel.add(endpointText);
		endpointPanel.add(Box.createHorizontalStrut(6));
		endpointPanel.setBorder(this.createTitledBorder("Example: https://test.client.endpoint/test"));

		// Public Cert
		final JLabel publicCertLabel = new JLabel("Public Cert");
		final JTextField publicCertPathText = UIComponents.createJTextField(null, 200, 27);
		final JPanel publicCertPanel = this.createFileSelector("Browse", publicCertLabel, publicCertPathText);
		publicCertPanel.setBorder(this.createTitledBorder("Public Cert File Path."));

		// Private Cert
		final JLabel privateCertLabel = new JLabel("Private Key");
		final JTextField privateCertPathText = UIComponents.createJTextField(null, 200, 27);
		final JPanel privateCertPanel = this.createFileSelector("Browse", privateCertLabel, privateCertPathText);
		privateCertPanel.setBorder(this.createTitledBorder("Private Cert File Path."));

		// Private Key Password
		final JTextField privateKeyPasswordText = UIComponents.createJPasswordField(null, 30, 27);
		final JLabel privateKeyPasswordLabel = new JLabel("Password");
		final JPanel privateKeyPasswordPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		privateKeyPasswordPanel.setLayout(new BoxLayout(privateKeyPasswordPanel, BoxLayout.X_AXIS));
		privateKeyPasswordPanel.add(privateKeyPasswordLabel);
		privateKeyPasswordPanel.add(Box.createHorizontalStrut(5));
		privateKeyPasswordPanel.add(privateKeyPasswordText);
		privateKeyPasswordPanel.setBorder(this.createTitledBorder("Private Cert password"));

		// Time Out
		final JTextField timeOutText = UIComponents.createJTextField("60000", 30, 27);
		final JLabel timeOutLabel = new JLabel("Timeout");
		final JPanel timeOutPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		timeOutPanel.setLayout(new BoxLayout(timeOutPanel, BoxLayout.X_AXIS));
		timeOutPanel.add(timeOutLabel);
		timeOutPanel.add(Box.createHorizontalStrut(5));
		timeOutPanel.add(timeOutText);
		timeOutPanel.setBorder(this.createTitledBorder("Timeout in Mill secs. Default 1 Minute"));

		final JPanel privateKeyPasswordAndTimeOutPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		privateKeyPasswordAndTimeOutPanel.setLayout(new BoxLayout(privateKeyPasswordAndTimeOutPanel, BoxLayout.X_AXIS));
		privateKeyPasswordAndTimeOutPanel.add(privateKeyPasswordPanel);
		privateKeyPasswordAndTimeOutPanel.add(timeOutPanel);

		// RADIO BUTTONS
		final JPanel radioButtonsPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		final JRadioButton ssl = new JRadioButton("SSL");
		ssl.setSelected(Boolean.TRUE);
		ssl.addActionListener(new RadioButtonActionListener("SSL", publicCertPanel, privateCertPanel, privateKeyPasswordPanel));

		final JRadioButton serverCertValidation = new JRadioButton("ServerCert");
		serverCertValidation.addActionListener(new RadioButtonActionListener("SERVERSSL", publicCertPanel, privateCertPanel, privateKeyPasswordPanel));

		final JRadioButton linientSsl = new JRadioButton("LENIENTSSL");
		linientSsl.addActionListener(new RadioButtonActionListener("LenientSSL", publicCertPanel, privateCertPanel, privateKeyPasswordPanel));
		final JRadioButton standardProtocol = new JRadioButton("Standard HTTP/S");
		standardProtocol.addActionListener(new RadioButtonActionListener("STANDARD", publicCertPanel, privateCertPanel, privateKeyPasswordPanel));

		final ButtonGroup bG = new ButtonGroup();
		bG.add(ssl);
		bG.add(serverCertValidation);
		bG.add(linientSsl);
		bG.add(standardProtocol);
		radioButtonsPanel.add(ssl);
		radioButtonsPanel.add(serverCertValidation);
		//radioButtonsPanel.add(linientSsl);
		radioButtonsPanel.add(standardProtocol);
		radioButtonsPanel.setBorder(this.createTitledBorder("What are you trying to test ?"));

		// Sample Data File
		final JLabel dataFileLabel = new JLabel("SampleData");
		final JTextField dataFileText = UIComponents.createJTextField(null, 250, 27);
		final JPanel dataFilePanel = this.createFileSelector("Browse", dataFileLabel, dataFileText);
		dataFilePanel.setBorder(this.createTitledBorder("Data file path"));

		// Endpoint Credentials
		final JTextField endpointUserNameText = UIComponents.createJTextField(null, 90, 27);
		final JLabel userNameLabel = new JLabel("Username");
		final JTextField endpointPasswordText = UIComponents.createJPasswordField(null, 90, 27);
		final JLabel endpointPasswordLabel = new JLabel("Password");
		final JPanel userCredentialsPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		userCredentialsPanel.setLayout(new BoxLayout(userCredentialsPanel, BoxLayout.X_AXIS));
		userCredentialsPanel.add(userNameLabel);
		userCredentialsPanel.add(Box.createHorizontalStrut(2));
		userCredentialsPanel.add(endpointUserNameText);
		userCredentialsPanel.add(Box.createHorizontalStrut(2));
		userCredentialsPanel.add(endpointPasswordLabel);
		userCredentialsPanel.add(Box.createHorizontalStrut(2));
		userCredentialsPanel.add(endpointPasswordText);
		userCredentialsPanel.setBorder(this.createTitledBorder("Endpoint Credentials if there are any.."));

		final JCheckBox followRedirectsCheckBox = UIComponents.createJCheckBox("Follow Redirects", 90, 27, Boolean.FALSE);
		final JPanel additionalOptionsPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		additionalOptionsPanel.setLayout(new BoxLayout(additionalOptionsPanel, BoxLayout.X_AXIS));
		additionalOptionsPanel.add(Box.createHorizontalStrut(2));
		additionalOptionsPanel.add(followRedirectsCheckBox);
		additionalOptionsPanel.add(Box.createHorizontalStrut(315));
		additionalOptionsPanel.setBorder(this.createTitledBorder("Additional Options"));

		// Endpoint
		final JTextField httpHeadersText = UIComponents.createJTextField("Content-type:text/xml;", 300, 27);
		final JLabel httpHeadersLabel = new JLabel("HTTP Headers");
		final JPanel httpHeadersPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		httpHeadersPanel.setLayout(new BoxLayout(httpHeadersPanel, BoxLayout.Y_AXIS));
		
		
		final JPanel httpHeadersTextPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		httpHeadersTextPanel.setLayout(new BoxLayout(httpHeadersTextPanel, BoxLayout.X_AXIS));
		httpHeadersTextPanel.add(httpHeadersLabel);
		httpHeadersTextPanel.add(Box.createHorizontalStrut(2));
		httpHeadersTextPanel.add(httpHeadersText);

		final JCheckBox printRequestHeadersCheckBox = UIComponents.createJCheckBox("Show Request Headers", 90, 27, Boolean.TRUE);
		final JCheckBox printResponseHeadersCheckBox = UIComponents.createJCheckBox("Show Response Headers", 90, 27, Boolean.TRUE);
		final JPanel headersOptionsPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		headersOptionsPanel.setLayout(new BoxLayout(headersOptionsPanel, BoxLayout.X_AXIS));
		headersOptionsPanel.add(printRequestHeadersCheckBox);
		headersOptionsPanel.add(Box.createHorizontalStrut(2));
		headersOptionsPanel.add(printResponseHeadersCheckBox);
		headersOptionsPanel.add(Box.createHorizontalStrut(108));

		httpHeadersPanel.add(httpHeadersTextPanel);
		httpHeadersPanel.add(headersOptionsPanel);

		httpHeadersPanel.setBorder(this.createTitledBorder("Format: Semicolon separated name:value pairs."));

		// create transform button
		final JButton postButton = this.createRunButton("POST", endpointText, publicCertPathText, privateCertPathText, dataFileText, privateKeyPasswordText, endpointUserNameText, endpointPasswordText, httpHeadersText, ssl, serverCertValidation, linientSsl, timeOutText, propertyText, printRequestHeadersCheckBox, printResponseHeadersCheckBox, followRedirectsCheckBox);
		final JButton getButton = this.createRunButton("GET", endpointText, publicCertPathText, privateCertPathText, dataFileText, privateKeyPasswordText, endpointUserNameText, endpointPasswordText, httpHeadersText, ssl, serverCertValidation, linientSsl, timeOutText, propertyText, printRequestHeadersCheckBox, printResponseHeadersCheckBox, followRedirectsCheckBox);
		final JPanel btnPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		btnPanel.add(postButton, BorderLayout.WEST);
		btnPanel.add(getButton, BorderLayout.EAST);

		final TitledBorder consoleBorder = BorderFactory.createTitledBorder("OUTPUT");
		consoleBorder.setTitleJustification(TitledBorder.CENTER);
		consoleBorder.setTitleFont(italicHelpFont);

		final JPanel consoleTextPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		final JTextArea consoleTextArea = new JTextArea("", 15, 36);
		consoleTextArea.setLineWrap(Boolean.TRUE);
		final JScrollPane sp = new JScrollPane(consoleTextArea);
		consoleTextArea.setBackground(Color.WHITE);
		consoleTextArea.setEditable(Boolean.FALSE);
		this.consoleOutput = consoleTextArea;
		consoleTextPanel.add(sp);
		consoleTextPanel.setBorder(consoleBorder);

		final JButton copyToClipBoardButton = UIComponents.createJButton("Copy To Clipboard", -1, -1);
		copyToClipBoardButton.addActionListener(new CopyToClipboardActionListener());
		final JButton saveToFileButton = UIComponents.createJButton("Save", -1, -1);
		saveToFileButton.addActionListener(new SaveActionListener());
		final JButton clearButton = UIComponents.createJButton("Clear", -1, -1);
		clearButton.addActionListener(new ClearActionListener());

		final JPanel utilityBtnsPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		utilityBtnsPanel.setLayout(new BoxLayout(utilityBtnsPanel, BoxLayout.X_AXIS));
		utilityBtnsPanel.add(clearButton);
		utilityBtnsPanel.add(Box.createHorizontalStrut(15));
		utilityBtnsPanel.add(saveToFileButton);
		utilityBtnsPanel.add(Box.createHorizontalStrut(15));
		utilityBtnsPanel.add(copyToClipBoardButton);

		final JLabel noteLabel = new JLabel("Feebback or suggestion. Please drop an email to mailto:tiwari_vineet@hotmail.com");
		noteLabel.setFont(italicHelpFont);
		final JPanel noteLabelPanel = UIComponents.createJPanel(-1, -1, Boolean.FALSE);
		noteLabelPanel.setLayout(new BoxLayout(noteLabelPanel, BoxLayout.X_AXIS));
		noteLabelPanel.add(noteLabel);

		// MAIN Panel

		final JPanel inputPanel = UIComponents.createJPanel(-1, -1, false);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		inputPanel.add(radioButtonsPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(propertyPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(endpointPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(publicCertPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(privateCertPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(privateKeyPasswordAndTimeOutPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(dataFilePanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(userCredentialsPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(httpHeadersPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(additionalOptionsPanel);
		this.createVerticalSpacings(inputPanel);
		
		inputPanel.add(btnPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(consoleTextPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(utilityBtnsPanel);
		this.createVerticalSpacings(inputPanel);
		this.createVerticalSpacings(inputPanel);

		inputPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		inputPanel.add(noteLabelPanel);
		this.createVerticalSpacings(inputPanel);

		final JPanel mainPanel = UIComponents.createJPanel(-1, -1, true);
		mainPanel.add(inputPanel, BorderLayout.NORTH);
		mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		return mainPanel;
	}

	private void createVerticalSpacings(final JPanel panel) {
		panel.add(Box.createVerticalStrut(2));
		panel.add(Box.createVerticalStrut(2));
	}

	private final class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {

			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify the file name ");
			final int userSelection = fileChooser.showSaveDialog(null);

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				final File fileToSave = fileChooser.getSelectedFile();
				final String absolutePath = fileToSave.getAbsolutePath();
				final String contents = TransposterView.this.getJTextAreaConsoleForConsole().getText();
				try {
					Util.saveFile(contents, absolutePath);
					System.out.println("File Save at location: " + absolutePath);
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private final class ClearActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			TransposterView.this.getJTextAreaConsoleForConsole().setText("");;
		}
	}

	private final class CopyToClipboardActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			TransposterView.this.getJTextAreaConsoleForConsole().selectAll();
			TransposterView.this.getJTextAreaConsoleForConsole().copy();
		}
	}

	/**
	 * Creates and returns the Run button
	 *
	 * @param inputXMLFile
	 * @param inputXSLFile
	 * @param outputXMLFile
	 * @return
	 */
	private JButton createRunButton(final String buttonText, final JTextField endpointText, final JTextField publicCertPathText, final JTextField privateCertPathText, final JTextField dataFileText, final JTextField privateCertPasswordText, final JTextField endpointUserNameText, final JTextField endpointPasswordText, final JTextField headersText, final JRadioButton isSSLEnabled, final JRadioButton serverCertValidation, final JRadioButton isLinientSSL, final JTextField timeOutText, final JTextField propertyText, final JCheckBox printRequestHeaders, final JCheckBox printResponseHeaders, final JCheckBox followRedirects) {
		final JButton transformButton = UIComponents.createJButton(buttonText, -1, -1);
		transformButton.addActionListener(new PostDataActionListener(buttonText, endpointText, publicCertPathText, privateCertPathText, dataFileText, privateCertPasswordText, endpointUserNameText, endpointPasswordText, headersText, isSSLEnabled, serverCertValidation, isLinientSSL, timeOutText, propertyText, printRequestHeaders, printResponseHeaders, followRedirects));
		return transformButton;
	}

	private final class RadioButtonActionListener implements ActionListener {

		private final String	name;
		private final JPanel	publicCertPanel;
		private final JPanel	privateKeypasswordPanel;
		private final JPanel	privateKeyCertPanel;

		private RadioButtonActionListener(final String name, final JPanel publicCertPanel, final JPanel privateKeypasswordPanel, final JPanel privateKeyCertPanel) {
			this.name = name;
			this.publicCertPanel = publicCertPanel;
			this.privateKeypasswordPanel = privateKeypasswordPanel;
			this.privateKeyCertPanel = privateKeyCertPanel;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			if ("SSL".equals(this.name)) {
				this.publicCertPanel.setEnabled(Boolean.TRUE);
				for (int i = 0; i < this.publicCertPanel.getComponentCount(); i++) {
					this.publicCertPanel.getComponent(i).setEnabled(Boolean.TRUE);
				}

				this.privateKeypasswordPanel.setEnabled(Boolean.TRUE);
				for (int i = 0; i < this.privateKeypasswordPanel.getComponentCount(); i++) {
					this.privateKeypasswordPanel.getComponent(i).setEnabled(Boolean.TRUE);
				}

				this.privateKeyCertPanel.setEnabled(Boolean.TRUE);
				for (int i = 0; i < this.privateKeyCertPanel.getComponentCount(); i++) {
					this.privateKeyCertPanel.getComponent(i).setEnabled(Boolean.TRUE);
				}
			} else if ("SERVERSSL".equals(this.name)) {

				this.publicCertPanel.setEnabled(Boolean.TRUE);
				for (int i = 0; i < this.publicCertPanel.getComponentCount(); i++) {
					this.publicCertPanel.getComponent(i).setEnabled(Boolean.TRUE);
				}

				this.privateKeypasswordPanel.setEnabled(Boolean.FALSE);
				for (int i = 0; i < this.privateKeypasswordPanel.getComponentCount(); i++) {
					this.privateKeypasswordPanel.getComponent(i).setEnabled(Boolean.FALSE);
				}

				this.privateKeyCertPanel.setEnabled(Boolean.FALSE);
				for (int i = 0; i < this.privateKeyCertPanel.getComponentCount(); i++) {
					this.privateKeyCertPanel.getComponent(i).setEnabled(Boolean.FALSE);
				}

			} else {
				this.publicCertPanel.setEnabled(Boolean.FALSE);
				for (int i = 0; i < this.publicCertPanel.getComponentCount(); i++) {
					this.publicCertPanel.getComponent(i).setEnabled(Boolean.FALSE);
				}

				this.privateKeypasswordPanel.setEnabled(Boolean.FALSE);
				for (int i = 0; i < this.privateKeypasswordPanel.getComponentCount(); i++) {
					this.privateKeypasswordPanel.getComponent(i).setEnabled(Boolean.FALSE);
				}

				this.privateKeyCertPanel.setEnabled(Boolean.FALSE);
				for (int i = 0; i < this.privateKeyCertPanel.getComponentCount(); i++) {
					this.privateKeyCertPanel.getComponent(i).setEnabled(Boolean.FALSE);
				}
			}
		}
	}

	private final class PostDataActionListener implements ActionListener {

		private final String		actionName;
		private final JTextField	endpointText;
		private final JTextField	publicCertPathText;
		private final JTextField	privateCertPathText;
		private final JTextField	dataFileText;
		private final JTextField	privateCertPasswordText;
		private final JTextField	endpointUserNameText;
		private final JTextField	endpointPasswordText;
		private final JTextField	headersText;
		private final JRadioButton	isSSLEnabled;
		private final JRadioButton	isLinientSSL;
		private final JRadioButton	serverCertValidation;
		private final JTextField	timeOutText;
		private final JTextField	propertyText;
		private final JCheckBox		printRequestHeaders;
		private final JCheckBox		printResponseHeaders;
		private final JCheckBox		followRedirects;

		private PostDataActionListener(final String operationName, final JTextField endpointText, final JTextField publicCertPathText, final JTextField privateCertPathText, final JTextField dataFileText, final JTextField privateCertPasswordText, final JTextField endpointUserNameText, final JTextField endpointPasswordText, final JTextField headersText, final JRadioButton isSSLEnabled, final JRadioButton serverCertValidation, final JRadioButton isLinientSSL, final JTextField timeOutText, final JTextField propertyText, final JCheckBox printRequestHeaders, final JCheckBox printResponsetHeaders, final JCheckBox followRedirects) {
			this.actionName = operationName;
			this.endpointText = endpointText;
			this.publicCertPathText = publicCertPathText;
			this.privateCertPathText = privateCertPathText;
			this.dataFileText = dataFileText;
			this.privateCertPasswordText = privateCertPasswordText;
			this.endpointUserNameText = endpointUserNameText;
			this.endpointPasswordText = endpointPasswordText;
			this.headersText = headersText;
			this.isSSLEnabled = isSSLEnabled;
			this.serverCertValidation = serverCertValidation;
			this.isLinientSSL = isLinientSSL;
			this.timeOutText = timeOutText;
			this.propertyText = propertyText;
			this.printRequestHeaders = printRequestHeaders;
			this.printResponseHeaders = printResponsetHeaders;
			this.followRedirects = followRedirects;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			final JButton clickedButton = (JButton) e.getSource();
			TransposterView.this.getJTextAreaConsoleForConsole().setText(null);

			if (this.validateContext()) {

				System.setProperty(TransportConstants.JAVAX_NET_DEBUG_VM_ARGUMENT, this.propertyText.getText());

				System.out.println(TransportConstants.JAVAX_NET_DEBUG_VM_ARGUMENT
						+ " property is set to "
						+ System.getProperty(TransportConstants.JAVAX_NET_DEBUG_VM_ARGUMENT));

				System.out.println(this.actionName.toUpperCase()
						+ " operation started..........");

				final String endpoint = this.endpointText.getText();
				final String pubCertPath = this.publicCertPathText.getText();
				final String privateCertPath = this.privateCertPathText.getText();
				final String privateKeyPassword = this.privateCertPasswordText.getText();
				final String dataFilePath = this.dataFileText.getText();
				final String endpointUserName = this.endpointUserNameText.getText();
				final String endpointPassword = this.endpointPasswordText.getText();
				final String httpHeaders = this.headersText.getText();
				final int timeout = Integer.valueOf(this.timeOutText.getText());
				final boolean isSSLEnabled = this.isSSLEnabled.isSelected();
				final boolean isServerCertValidation = this.serverCertValidation.isSelected();
				final boolean isLinientSSL = this.isLinientSSL.isSelected();

				final boolean printRequestHeaders = this.printRequestHeaders.isSelected();
				final boolean printResponseHeaders = this.printResponseHeaders.isSelected();
				final boolean followRedirects = this.followRedirects.isSelected();
				
				final Map map = new HashMap<>();
				map.put(TransportRequestFieldNames.END_POINT.getName(), endpoint);
				map.put(TransportRequestFieldNames.PUBLIC_CERT_PATH.getName(), pubCertPath);
				map.put(TransportRequestFieldNames.PRIVATE_CERT_PATH.getName(), privateCertPath);
				map.put(TransportRequestFieldNames.PRIVATE_CERT_PASSWORD.getName(), privateKeyPassword);
				map.put(TransportRequestFieldNames.ENDPOINT_USERNAME.getName(), endpointUserName);
				map.put(TransportRequestFieldNames.ENDPOINT_PASSWORD.getName(), endpointPassword);
				map.put(TransportRequestFieldNames.DATA_FILE_PATH.getName(), dataFilePath);
				map.put(TransportRequestFieldNames.HTTP_HEADERS.getName(), httpHeaders);
				map.put(TransportRequestFieldNames.HTTP_METHOD_TYPE.getName(), this.actionName.toUpperCase());
				map.put(TransportRequestFieldNames.IS_SSL_ENABLED.getName(), isSSLEnabled);
				map.put(TransportRequestFieldNames.IS_SERVER_CERT_VALIDATION.getName(), isServerCertValidation);
				map.put(TransportRequestFieldNames.IS_LINIENT_SSL.getName(), isLinientSSL);
				map.put(TransportRequestFieldNames.TIME_OUT.getName(), timeout);

				TransporterOptions transporterOptions = new TransporterOptions(printRequestHeaders, printResponseHeaders, followRedirects);

				final TransportRequest request = new TransportRequest(map);
				final Transporter transporter = new Transporter(transporterOptions);

				clickedButton.setText("Working........");
				clickedButton.setEnabled(Boolean.FALSE);

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							transporter.doWork(request);
						} catch (final Exception e1) {
							e1.printStackTrace();
						}

						clickedButton.setText(PostDataActionListener.this.actionName);
						clickedButton.setEnabled(Boolean.TRUE);
					}
				});
			} else {
				System.out.println("Please provide needed inputs and try again.");
			}
		}

		private boolean validateContext() {

			boolean isContextValid = Boolean.TRUE;

			if (this.isEmpty(this.endpointText.getText())) {
				isContextValid = Boolean.FALSE;
				System.out.println("Endpoint is empty.");
			}

			if ((this.isSSLEnabled.isSelected() || this.serverCertValidation.isSelected())
					&& this.isEmpty(this.publicCertPathText.getText())) {
				isContextValid = Boolean.FALSE;
				System.out.println("No public cert found.");
			}

			if (this.isSSLEnabled.isSelected() &&
					this.isEmpty(this.privateCertPathText.getText())) {
				isContextValid = Boolean.FALSE;
				System.out.println("No private cert found. ");
			}

			if (this.isSSLEnabled.isSelected() &&
					this.isEmpty(this.privateCertPasswordText.getText())) {
				isContextValid = Boolean.FALSE;
				System.out.println("Password for private cert not found.");
			}

			if (this.isEmpty(this.dataFileText.getText())) {
				isContextValid = Boolean.FALSE;
				System.out.println("No sample file found ");
			}

			if (this.isEmpty(this.headersText.getText())) {
				isContextValid = Boolean.FALSE;
				System.out.println("No headers found. Atleast provide the content-type");
			}

			try {
				Integer.valueOf(this.timeOutText.getText());
			} catch (final NumberFormatException e) {
				isContextValid = Boolean.FALSE;
				System.out.println("Timeout should be a number");
			}

			return isContextValid;
		}

		private boolean isEmpty(final String value) {
			return ((value == null) || (value.trim().length() == 0));
		}
	}

	/**
	 * Creates a file selector and return the panel with text and button
	 *
	 * @param buttonText
	 * @param fileText
	 * @param save
	 * @return
	 */
	private JPanel createFileSelector(final String buttonText, final JLabel label, final JTextField fileText) {
		final JPanel panel = UIComponents.createJPanel(-1, -1, true);

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		final JButton openBtn = UIComponents.createJButton(buttonText, -1, -1);
		panel.add(label);

		panel.add(Box.createHorizontalStrut(2));
		// panel.add(new JSeparator(SwingConstants.VERTICAL));
		panel.add(Box.createHorizontalStrut(2));

		panel.add(fileText);

		panel.add(Box.createHorizontalStrut(2));
		// panel.add(new JSeparator(SwingConstants.VERTICAL));
		panel.add(Box.createHorizontalStrut(2));

		panel.add(openBtn);
		openBtn.addActionListener(new OpenActionListener(fileText));
		return panel;
	}

	public String getBrowseLocation() {
		return this.browseLocation;
	}

	public void setBrowseLocation(final String browseLocation) {
		this.browseLocation = browseLocation;
	}

	/**
	 * open button action listener
	 *
	 *
	 */
	private final class OpenActionListener implements ActionListener {
		private final JTextField	fileText;

		private OpenActionListener(final JTextField fileText) {
			this.fileText = fileText;
		}

		@Override
		public void actionPerformed(final ActionEvent arg0) {

			final String location = TransposterView.this.getBrowseLocation() != null
					? TransposterView.this.getBrowseLocation()
					: this.fileText.getText();

			final JFileChooser openFile = UIComponents.createJFileChooser(location, -1, false);
			int returnVal = JFileChooser.CANCEL_OPTION;
			returnVal = openFile.showOpenDialog(null);
			if (JFileChooser.APPROVE_OPTION == returnVal) {
				TransposterView.this.setBrowseLocation(openFile.getSelectedFile().getAbsolutePath());
				this.fileText.setText(openFile.getSelectedFile().getAbsolutePath());
			}
		}
	}
}
