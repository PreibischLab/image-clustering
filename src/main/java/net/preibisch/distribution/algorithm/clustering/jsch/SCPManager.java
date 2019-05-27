package main.java.net.preibisch.distribution.algorithm.clustering.jsch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import main.java.net.preibisch.distribution.algorithm.controllers.items.callback.Callback;
import main.java.net.preibisch.distribution.algorithm.controllers.items.server.Login;
import main.java.net.preibisch.distribution.gui.items.Colors;
import main.java.net.preibisch.distribution.gui.items.DataPreview;
import main.java.net.preibisch.distribution.io.img.XMLFile;

public class SCPManager {

	public static void sendInput(XMLFile inputFile) throws JSchException, IOException {
		sendFile(inputFile);
		System.out.println("Related files: " + inputFile.getRelatedFiles().size());
		for (File f : inputFile.getRelatedFiles()) {
			System.out.println("Related file: " + f.getAbsolutePath());
			sendFile(f);
		}
	}

	public static void sendFile(File f) throws JSchException, IOException {

		String serverPath = Login.getServer().getPath();
		String localFile = f.getAbsolutePath();
		String clusterFile = new File(serverPath, f.getName()).getPath();
		System.out.println("Local file: " + localFile);
		System.out.println("Cluster file: " + clusterFile);
		SCPFunctions.send(localFile, clusterFile);
		System.out.println("File sent ! " + localFile + " -> " + clusterFile);
	}

	public static void createClusterFolder(String path) throws JSchException {
		String command = "mkdir " + path;
		SCPFunctions.runCommand(command, new Callback());
	}

	public static void sendFile(String localFile, String remoteFile, int id) throws JSchException, IOException {
		SessionManager.validateConnection();
		SCPFunctions.send(localFile, remoteFile);
		if (id != -1) {
			try {
				DataPreview.getBlocksPreview().get(id).setStatus(Colors.SENT);

				throw new Exception("Out of boxes");
			} catch (Exception e) {
				// Helper.log("Out of size");
			}
		}
	}

	public static void getFile(String remoteFile, String localFile, int id) throws IOException, JSchException {
		SessionManager.validateConnection();
		SCPFunctions.get(remoteFile, localFile);
		if (id >= 0) {
			try {
				DataPreview.getBlocksPreview().get(id).setStatus(Colors.GOT);
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("Error! no box for index: " + id);
			}
		}
	}

	public static void sendFolder(String localFile, String remoteFile)
			throws SftpException, JSchException, FileNotFoundException {
		SessionManager.validateConnection();

		Channel channel = SessionManager.getCurrentSession().openChannel("sftp"); // Open SFTP Channel
		channel.connect();
		ChannelSftp channelSftp = (ChannelSftp) channel;
		channelSftp.cd(remoteFile); // Change Directory on SFTP Server
		SCPFunctions.recursiveFolderUpload(channelSftp, localFile, remoteFile);
		
		System.out.println("Finish copy folder: "+remoteFile);
	}
}