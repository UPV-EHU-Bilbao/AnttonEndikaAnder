package grafika;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import model.HashSha512;
import model.User;
import controller.UserController;

public class NewUser extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;
	private JLabel labelIncorrectPass; 
	private JLabel labelIncorrectUser; 

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */
	public NewUser() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUser = new JLabel("User");
			lblUser.setBounds(38, 52, 106, 15);
			contentPanel.add(lblUser);
		}
		
		textField = new JTextField();
		textField.setBounds(225, 50, 114, 19);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(38, 79, 70, 15);
		contentPanel.add(lblPassword);
		
		JLabel label = new JLabel("Repeat Password");
		label.setBounds(38, 105, 147, 15);
		contentPanel.add(label);
		
		labelIncorrectPass= new JLabel("Password is not the same");
		this.labelIncorrectPass.setBounds(200, 200, 300, 15);
		contentPanel.add(labelIncorrectPass);
		labelIncorrectPass.setForeground(Color.RED);
		labelIncorrectPass.setVisible(false);
		
		labelIncorrectUser= new JLabel("User is empty");
		this.labelIncorrectUser.setBounds(200, 175, 300, 15);
		contentPanel.add(labelIncorrectUser);
		labelIncorrectUser.setForeground(Color.RED);
		labelIncorrectUser.setVisible(false);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(225, 77, 114, 19);
		contentPanel.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(225, 103, 114, 19);
		contentPanel.add(passwordField_1);
		
		JLabel label_1 = new JLabel("Twitter User");
		label_1.setBounds(38, 132, 106, 15);
		contentPanel.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(225, 130, 114, 19);
		contentPanel.add(textField_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(this);
				okButton.setActionCommand("ok");
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("cancel");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ok")){
			if(!textField.getText().isEmpty()){
				if (!passwordField.getText().isEmpty() && passwordField.getText().equals(passwordField_1.getText())){
					User.getUser().newUser(textField.getText() , passwordField.getText());
					this.dispose();
				}else{
					labelIncorrectPass.setVisible(true);
				}
			}else{
				labelIncorrectUser.setVisible(true);
			}
		}else{
			this.dispose();
		}
	}
}
