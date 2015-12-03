	package grafika;
	
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

	import model.TwitterConect;
import model.User;
import controller.Dd;
import controller.TwitterSesionController;
	
	public class Login extends JFrame implements ActionListener {
	
		private JPanel contentPane;
		private JTextField erabiltzailea;
		private JPasswordField passwordField;
		private JComboBox comboBox = new JComboBox();
		private JLabel oker;
	
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Login frame = new Login();
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
		public Login() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			setTitle("Login");
			setResizable(false);
			
			JLabel lblUsername = new JLabel(Messages.getString("login.0")); //$NON-NLS-1$
			lblUsername.setBounds(45, 65, 100, 15);
			contentPane.add(lblUsername);
			
			JLabel lblPasword = new JLabel(Messages.getString("login.1")); //$NON-NLS-1$
			lblPasword.setBounds(45, 122, 70, 15);
			contentPane.add(lblPasword);
			
			erabiltzailea = new JTextField("");
			erabiltzailea.setBounds(161, 63, 114, 19);
			contentPane.add(erabiltzailea);
			erabiltzailea.setColumns(10);
			
			oker =new JLabel("Erabiltzale edo pasahitz okerra");
			oker.setBounds(80, 65, 300, 220);
			oker.setForeground(Color.RED);
			contentPane.add(oker);
			oker.setVisible(false);
			
			passwordField = new JPasswordField("");
			passwordField.setBounds(161, 120, 114, 17);
			contentPane.add(passwordField);
			
			JButton btnLogin = new JButton(Messages.getString("login.2")); //$NON-NLS-1$
			btnLogin.setBounds(267, 229, 117, 25);
			contentPane.add(btnLogin);
			btnLogin.addActionListener(this);
			btnLogin.setActionCommand("login");
			
			JButton btnNewUser = new JButton(Messages.getString("login.3")); //$NON-NLS-1$
			btnNewUser.setBounds(55, 229, 117, 25);
			contentPane.add(btnNewUser);
			btnNewUser.addActionListener(this);
			btnNewUser.setActionCommand("newuser");
			
			
			comboBox.setBounds(336, 38, 102, 24);
			contentPane.add(comboBox);
			comboBox.setVisible(false);
			comboBox.addActionListener(this);
			comboBox.addItem(Messages.getString("login.4")); //$NON-NLS-1$
			comboBox.addItem(Messages.getString("login.5")); //$NON-NLS-1$
			comboBox.addItem(Messages.getString("login.6")); //$NON-NLS-1$
			
		}
		
		public void actionPerformed(ActionEvent e){
			
			if (comboBox.getSelectedIndex()==2){
				Locale spanishLocale = new Locale("grafika.messages", "ES");
				//ResourceBundle bundle3 = ResourceBundle.getBundle("TestBundle", spanishLocale);
				Messages.setBundle(spanishLocale);
				Messages.setString("grafika.messages");
			}
				if (e.getActionCommand().equals("login")){
					int log=User.getUser().login(erabiltzailea.getText(),passwordField.getText());
					if (log!=-1) {
						HasierakoMenua menua=new HasierakoMenua();
						menua.setVisible(true);
						this.dispose();
					}else{
						oker.setVisible(true);
					}
				}
				if(e.getActionCommand().equals("newuser")){
					NewUser dialog = new NewUser();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
				
			}
			
			
		
	}
