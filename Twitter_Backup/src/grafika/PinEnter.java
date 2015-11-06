package grafika;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


public class PinEnter extends JDialog implements ActionListener {

	private JPanel contentPane;
	private JTextField txtPin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PinEnter frame = new PinEnter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PinEnter() {
		super((java.awt.Frame) null, true);
		setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMesedezSartuNabigatzailean = new JLabel("Mesedez sartu nabigatzailean lortutako pin zenbakia");
		lblMesedezSartuNabigatzailean.setBounds(28, 23, 396, 32);
		contentPane.add(lblMesedezSartuNabigatzailean);
		
		txtPin = new JTextField();
		txtPin.setBounds(123, 96, 223, 32);
		contentPane.add(txtPin);
		txtPin.setColumns(10);
		
		JButton btnSartu = new JButton("Sartu");
		btnSartu.setBounds(83, 207, 117, 25);
		contentPane.add(btnSartu);
		btnSartu.addActionListener(this);
		btnSartu.setActionCommand("sartu");
		
		JButton btnAtzera = new JButton("Atzera");
		btnAtzera.setBounds(261, 207, 117, 25);
		contentPane.add(btnAtzera);
		btnAtzera.addActionListener(this);
		btnAtzera.setActionCommand("atzera");
	}
	
	public String textuaHartu(){
		return txtPin.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("sartu")){
			textuaHartu();
			this.dispose();
		}else{
			this.dispose();
		}
	}
	public String getPin(){
		return textuaHartu();
	}
}
