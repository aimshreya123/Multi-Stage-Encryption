package setproject;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileSystemView;

public class MultiStageEncryption {
	public static void main(String[] ar) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new A();
			}
		});

	}
	// Closing the main method
}// Closing the class A

class A implements ActionListener {
	JFrame jf;
	JButton button1, button2, button3, button4, button5, button6, button7;
	JLabel label, extLabel;
	String str_path = new String();
	String fileName, file_extension, des_key, aes_key, aes_encrypted, des_encrypted;
	String des_decrypted;

	A() {
		jf = new JFrame();
		button1 = new JButton("Select File...");
		button2 = new JButton("Encode File");
		button3 = new JButton("Apply AES Encryption");
		button4 = new JButton("Apply 3DES Encryption");
		button5 = new JButton("Apply 3DES Decryption");
		button6 = new JButton("Apply AES Decryption");
		button7 = new JButton("Decode File");

		label = new JLabel();
		extLabel = new JLabel();

		jf.add(button1);
		jf.add(button2);
		jf.add(button3);
		jf.add(button4);
		jf.add(button5);
		jf.add(button6);
		jf.add(button7);
		jf.add(label);

		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);

		jf.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
		jf.setSize(400, 450);
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {

		if (ae.getActionCommand().equals("Select File...")) {

			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				str_path = selectedFile.getAbsolutePath();
				fileName = selectedFile.getName();
				if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
					file_extension = fileName.substring(fileName.lastIndexOf(".")+1);
				}
			}
			label.setText(str_path);
			extLabel.setText("File Extension is : "+file_extension);
			jf.add(label);
			jf.add(extLabel);
			jf.setVisible(true);

		}
		String path = "/Users/shreya/Downloads/Encoded/encodedfile.txt";
		if (ae.getActionCommand().equals("Encode File")) {
			try {
				BaseDemo.encodeImage(str_path, path);
			} catch (Exception e) {
				e.printStackTrace();
			}
			label.setText("Congratulations! Your file has been encoded successfully!");
			jf.add(label);
			jf.setVisible(true);
		}

		if (ae.getActionCommand().equals("Apply AES Encryption")) {

			aes_key = JOptionPane.showInputDialog("Enter key to proceed AES Encrytion :");

			try {
				aes_encrypted = AESFileEncryption.AESEncryption(aes_key, path);
			} catch (Exception e) {
				e.printStackTrace();
			}

			label.setText("AES Encryption Done!");
			jf.add(label);
			jf.setVisible(true);

		}
		if (ae.getActionCommand().equals("Apply 3DES Encryption")) {
			des_key = JOptionPane.showInputDialog("Enter key to proceed 3DES Encryption :");

			try {
				des_encrypted = FileEncryption.TDESFileEncryption(des_key, aes_encrypted);
			} catch (Exception e) {
				e.printStackTrace();
			}

			label.setText("3DES Encryption Done!");
			jf.add(label);
			jf.setVisible(true);
		}

		if (ae.getActionCommand().equals("Apply 3DES Decryption")) {

			try {
				des_decrypted = FileDecryption.TDESFileDecryption(des_key, des_encrypted);
			} catch (Exception e) {
				e.printStackTrace();
			}

			label.setText("3DES Decryption Done!");
			jf.add(label);
			jf.setVisible(true);

		}
		if (ae.getActionCommand().equals("Apply AES Decryption")) {

			try {
				new AESFileDecryption(des_decrypted, aes_key, path);
			} catch (Exception e) {
				e.printStackTrace();
			}

			label.setText("3DES Decryption Done!");
			jf.add(label);
			jf.setVisible(true);

		}
		if (ae.getActionCommand().equals("Decode File")) {
			try {
				BaseDemo.decodeImage(path,
						"/Users/shreya/Downloads/Encoded/new_af."+file_extension);
			} catch (Exception e) {
				e.printStackTrace();
			}
			label.setText("Congratulations! Your file has been decoded successfully!");
			jf.add(label);
			jf.setVisible(true);
		}
	}
}
