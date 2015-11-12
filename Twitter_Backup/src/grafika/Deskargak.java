package grafika;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

import model.TwitterConect;

public class Deskargak extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JCheckBox chckbxTweet;
	private JCheckBox chckbxRetweet;
	private JCheckBox chckbxFav;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Deskargak dialog = new Deskargak();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Deskargak() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		chckbxTweet = new JCheckBox("Tweet");
		chckbxTweet.setBounds(36, 34, 129, 23);
		contentPanel.add(chckbxTweet);
		
		chckbxRetweet = new JCheckBox("RETweet");
		chckbxRetweet.setBounds(217, 34, 129, 23);
		contentPanel.add(chckbxRetweet);
		
		chckbxFav = new JCheckBox("Fav");
		chckbxFav.setBounds(36, 112, 129, 23);
		contentPanel.add(chckbxFav);
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
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("ok")){
			TwitterConect tc=new TwitterConect();
			if(chckbxTweet.isSelected()){
				tc.getTwitts();
			}
			if(chckbxFav.isSelected()){
				tc.getFavs(new Long("1"));
			}
		}
		
		
	}
}
