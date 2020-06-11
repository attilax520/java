package com.attilax.ui;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class javafx1 extends Application {
	
	
	public class JavaObj {  
	    public void method1() {  
	       // Platform.exit();  
	    }  
	}

	@Override
	public void start(Stage primaryStage) {
//		Button btn = new Button();
//		btn.setText("Say 'Hello World'");
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				System.out.println("Hello World!");
//			}
//		});
		
		
		WebView WebView1=new WebView();
		WebEngine webEngine = WebView1.getEngine();
		JSObject window = (JSObject) webEngine.executeScript("window");  
		window.setMember("javaobj", new JavaObj());
		//js中调用java浏览器注入对象模式    window.javaobj.method1()
		
		webEngine.load("http://192.168.1.18:8080/centerweb/cloudhis/web/index.jsp");
		//	webEngine.load("http://www.baidu.com");
			webEngine.load("http://192.168.1.18:8080/centerweb/login.jsp");
		
			webEngine.load("http://localhost:8080/clidoctorweb/");
			
		
		
		

		StackPane root = new StackPane();
		root.getChildren().add(WebView1);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("标题!");
		primaryStage.setScene(scene);
		primaryStage.setHeight(800);
		primaryStage.setWidth(1800);
		//设置窗口的图标.  
		primaryStage.getIcons().add(   new Image( "com/icon.png"         )); //path relt class pkg dir
		primaryStage.show();
		
		
		
		//添加系统托盘图标.  
	       SystemTray SystemTray1 = SystemTray.getSystemTray();  
	       BufferedImage image;
		try {
			image = ImageIO.read(new FileInputStream( new File("c:\\0logs\\icon_tray.png"  ))   );
			  TrayIcon  trayIcon = new TrayIcon(image, "自动备份工具");  
		       trayIcon.setToolTip("坐标软件");  
		       SystemTray1.add(trayIcon); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
		//悬浮窗
		Stage stage =new Stage();
		stage.setAlwaysOnTop(true);
		stage.setHeight(200);
		stage.setWidth(200);
	//	stage.show();
	     
	}

	public static void main(String[] args) {
		 javafx.application.Application.launch(args);
		 System.out.println("--");
	}
}
