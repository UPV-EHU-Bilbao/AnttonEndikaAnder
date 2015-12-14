package grafika;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Export;

public class ExportToExcel extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JCheckBox chckbxTweet;
	private JCheckBox chckbxFollowers;
	private JCheckBox chckbxFollowing;
	private JCheckBox chckbxFav;
	private String twitterUser;
	private JCheckBox chckbxMd;
	private JCheckBox chckbxList;



	/**
	 * Create the dialog.
	 * @param pTwitterUser 
	 */
	public ExportToExcel(String pTwitterUser) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 20, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2,1));
		setTitle("Export to Excel");
		
		
		twitterUser=pTwitterUser;

		chckbxTweet = new JCheckBox("Tweet");
		contentPanel.add(chckbxTweet);

		chckbxFollowers = new JCheckBox("Followers");
		contentPanel.add(chckbxFollowers);

		chckbxFollowing = new JCheckBox("Following");
		contentPanel.add(chckbxFollowing);

		chckbxMd = new JCheckBox("Direct Mesage");
		contentPanel.add(chckbxMd);

		chckbxFav = new JCheckBox("Fav");
		contentPanel.add(chckbxFav);

		chckbxList = new JCheckBox("List");
		contentPanel.add(chckbxList);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}

	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("OK")){
			
			 boolean getTweet=false;
			 boolean getFav=false;
			 boolean getFollows=false;
			 boolean getFollowers=false;
			 boolean getDirectMesage=false;
			 boolean getLists=false;
			
			if(chckbxTweet.isSelected()){
				getTweet=true;
			}
			if(chckbxFav.isSelected()){
				getFav=true;
			}
			if(chckbxFollowers.isSelected()){
				getFollowers=true;
			}
			if(chckbxFollowing.isSelected()){
				getFollows=true;
			}
			if(chckbxMd.isSelected()){
				getDirectMesage=true;
			}
			if(chckbxList.isSelected()){
				getLists=true;
			}
			Export export =new Export();
			export.exportToExcel(twitterUser, getTweet, getFav, getFollows, getFollowers, getDirectMesage, getLists);
		}
		this.dispose();
	}
}
	

