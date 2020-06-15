/*
NARPassword for Java - Application to generate a non-random password
Copyright (C) 2011-2019 Hugues Johnson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.huguesjohnson.narpas.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.huguesjohnson.narpas.Narpas;

public class MainWindow {

	private JFrame frmNarpassword;
	private JPasswordField passwordFieldPassphrase;
	private JTextField textFieldPasswordName;
	private JTextField textFieldPassword;
	private JCheckBox checkBoxUseLowerCase;
	private JCheckBox checkBoxUseUpperCase;
	private JCheckBox checkBoxUseNumbers;
	private JCheckBox checkBoxUseSpecialCharacters;
	private JSlider sliderPasswordLength;

	private void generatePassword(){
		String passPhrase=new String(this.passwordFieldPassphrase.getPassword());
		String passwordName=this.textFieldPasswordName.getText();
		boolean useLCase=this.getCheckBoxUseLowerCase().isSelected();
		boolean useUCase=this.getCheckBoxUseUpperCase().isSelected();
		boolean useNumbers=this.getCheckBoxUseNumbers().isSelected();
		boolean useSpecialCharacters=this.getCheckBoxUseSpecialCharacters().isSelected();
		int basePasswordLength=this.getSliderPasswordLength().getValue()/8;
		if((passPhrase.length()<1)||(passwordName.length()<1)){
			this.textFieldPassword.setText("");
		}else if(passPhrase.equals(passwordName)){
			this.textFieldPassword.setText("");
		}else{
			String password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			this.textFieldPassword.setText(password);
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmNarpassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
		this.passwordFieldPassphrase.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    generatePassword();
			  }
			  public void removeUpdate(DocumentEvent e) {
				    generatePassword();
			  }
			  public void insertUpdate(DocumentEvent e) {
				    generatePassword();
			  }
			});
	
		this.textFieldPasswordName.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    generatePassword();
			  }
			  public void removeUpdate(DocumentEvent e) {
				    generatePassword();
			  }
			  public void insertUpdate(DocumentEvent e) {
				    generatePassword();
			  }
			});
	}
		
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNarpassword=new JFrame();
		frmNarpassword.setResizable(false);
		frmNarpassword.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/huguesjohnson/narpas/swing/resources/narpas-icon-64.png")));
		frmNarpassword.setTitle("NARPassword 1.0");
		frmNarpassword.setBounds(100, 100, 450, 300);
		frmNarpassword.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNarpassword.getContentPane().setLayout(new BoxLayout(frmNarpassword.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panelMainContent = new JPanel();
		frmNarpassword.getContentPane().add(panelMainContent);
		panelMainContent.setLayout(new BoxLayout(panelMainContent, BoxLayout.Y_AXIS));
		
		JPanel panelPassword = new JPanel();
		panelMainContent.add(panelPassword);
		panelPassword.setLayout(new BoxLayout(panelPassword, BoxLayout.Y_AXIS));
		
		JPanel panelPassphrase = new JPanel();
		panelPassword.add(panelPassphrase);
		panelPassphrase.setLayout(new BoxLayout(panelPassphrase, BoxLayout.X_AXIS));
		
		JLabel labelPassphrase = new JLabel("Pass Phrase:");
		labelPassphrase.setPreferredSize(new Dimension(120, 15));
		panelPassphrase.add(labelPassphrase);
		
		passwordFieldPassphrase = new JPasswordField();
		passwordFieldPassphrase.setPreferredSize(new Dimension(200, 15));
		passwordFieldPassphrase.setColumns(32);
		labelPassphrase.setLabelFor(passwordFieldPassphrase);
		panelPassphrase.add(passwordFieldPassphrase);
		
		JPanel panelPassphraseHint = new JPanel();
		panelPassword.add(panelPassphraseHint);
		panelPassphraseHint.setLayout(new BoxLayout(panelPassphraseHint, BoxLayout.X_AXIS));
		
		JLabel labelPassphraseHint = new JLabel("i.e. \"I like pie\" or \"Please no more California songs\"");
		labelPassphraseHint.setHorizontalAlignment(SwingConstants.RIGHT);
		panelPassphraseHint.add(labelPassphraseHint);
		
		JPanel panelPasswordName = new JPanel();
		panelPassword.add(panelPasswordName);
		panelPasswordName.setLayout(new BoxLayout(panelPasswordName, BoxLayout.X_AXIS));
		
		JLabel labelPasswordName = new JLabel("Password Name:");
		labelPasswordName.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPasswordName.setPreferredSize(new Dimension(120, 15));
		panelPasswordName.add(labelPasswordName);
		
		textFieldPasswordName = new JTextField();
		textFieldPasswordName.setPreferredSize(new Dimension(200, 15));
		labelPasswordName.setLabelFor(textFieldPasswordName);
		panelPasswordName.add(textFieldPasswordName);
		textFieldPasswordName.setColumns(32);
		
		JPanel panelPasswordNameHint = new JPanel();
		panelPassword.add(panelPasswordNameHint);
		panelPasswordNameHint.setLayout(new BoxLayout(panelPasswordNameHint, BoxLayout.X_AXIS));
		
		JLabel labelPasswordNameHint = new JLabel("i.e. \"Facebook\" or \"yourname@gmail.com\"");
		labelPasswordNameHint.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPasswordNameHint.setHorizontalAlignment(SwingConstants.RIGHT);
		panelPasswordNameHint.add(labelPasswordNameHint);
		
		JPanel panelGeneratedPassword = new JPanel();
		panelPassword.add(panelGeneratedPassword);
		panelGeneratedPassword.setLayout(new BoxLayout(panelGeneratedPassword, BoxLayout.X_AXIS));
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPassword.setPreferredSize(new Dimension(120, 15));
		panelGeneratedPassword.add(labelPassword);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setPreferredSize(new Dimension(180, 15));
		labelPassword.setLabelFor(textFieldPassword);
		textFieldPassword.setEditable(false);
		panelGeneratedPassword.add(textFieldPassword);
		textFieldPassword.setColumns(32);
		
		JButton buttonCopy = new JButton("");
		buttonCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldPassword.selectAll();
				textFieldPassword.copy();
			}
		});
		buttonCopy.setPreferredSize(new Dimension(20, 15));
		buttonCopy.setIcon(new ImageIcon(MainWindow.class.getResource("/com/huguesjohnson/narpas/swing/resources/tango-copy.png")));
		panelGeneratedPassword.add(buttonCopy);
		
		JPanel panelPasswordOptions = new JPanel();
		panelPasswordOptions.setBorder(new TitledBorder(null, "Password Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMainContent.add(panelPasswordOptions);
		panelPasswordOptions.setLayout(new BoxLayout(panelPasswordOptions, BoxLayout.Y_AXIS));
		
		checkBoxUseLowerCase = new JCheckBox("Use lower case characters (a-z)");
		checkBoxUseLowerCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generatePassword();
			}
		});
		checkBoxUseLowerCase.setSelected(true);
		checkBoxUseLowerCase.setPreferredSize(new Dimension(325, 23));
		panelPasswordOptions.add(checkBoxUseLowerCase);
		
		checkBoxUseUpperCase = new JCheckBox("Use upper case characters (A-Z)");
		checkBoxUseUpperCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generatePassword();
			}
		});
		checkBoxUseUpperCase.setSelected(true);
		checkBoxUseUpperCase.setPreferredSize(new Dimension(325, 23));
		panelPasswordOptions.add(checkBoxUseUpperCase);
		
		checkBoxUseNumbers = new JCheckBox("Use numbers (0-9)");
		checkBoxUseNumbers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generatePassword();
			}
		});
		checkBoxUseNumbers.setSelected(true);
		checkBoxUseNumbers.setPreferredSize(new Dimension(325, 23));
		panelPasswordOptions.add(checkBoxUseNumbers);
		
		checkBoxUseSpecialCharacters = new JCheckBox("Use special characters (!@#$%^&*-=+:;?,.)");
		checkBoxUseSpecialCharacters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generatePassword();
			}
		});
		checkBoxUseSpecialCharacters.setSelected(true);
		panelPasswordOptions.add(checkBoxUseSpecialCharacters);
		
		JPanel panelPasswordLength = new JPanel();
		panelPasswordOptions.add(panelPasswordLength);
		panelPasswordLength.setLayout(new BoxLayout(panelPasswordLength, BoxLayout.X_AXIS));
		
		JLabel labelPasswordLength = new JLabel("Password Length:");
		labelPasswordLength.setHorizontalAlignment(SwingConstants.LEFT);
		panelPasswordLength.add(labelPasswordLength);
		
		sliderPasswordLength = new JSlider();
		sliderPasswordLength.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				generatePassword();
			}
		});
		labelPasswordLength.setLabelFor(sliderPasswordLength);
		sliderPasswordLength.setPaintLabels(true);
		sliderPasswordLength.setPaintTicks(true);
		sliderPasswordLength.setSnapToTicks(true);
		sliderPasswordLength.setValue(32);
		sliderPasswordLength.setMajorTickSpacing(32);
		sliderPasswordLength.setMinorTickSpacing(8);
		sliderPasswordLength.setMinimum(8);
		sliderPasswordLength.setMaximum(128);
		panelPasswordLength.add(sliderPasswordLength);
		
		JPanel panelFooter = new JPanel();
		panelMainContent.add(panelFooter);
		panelFooter.setLayout(new BoxLayout(panelFooter, BoxLayout.X_AXIS));
		
		JLabel labelFooter = new JLabel(""); /* placeholder for future use maybe */
		panelFooter.add(labelFooter);
	}
	protected JCheckBox getCheckBoxUseLowerCase() {
		return checkBoxUseLowerCase;
	}
	protected JCheckBox getCheckBoxUseUpperCase() {
		return checkBoxUseUpperCase;
	}
	protected JCheckBox getCheckBoxUseNumbers() {
		return checkBoxUseNumbers;
	}
	protected JCheckBox getCheckBoxUseSpecialCharacters() {
		return checkBoxUseSpecialCharacters;
	}
	protected JSlider getSliderPasswordLength() {
		return sliderPasswordLength;
	}
}
