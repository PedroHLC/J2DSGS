package com.pedrohlc.j2dsgs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Transparency;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window{

	/**
	 * Variaveis
	 */
	
	private JFrame Frame;
	private JPanel contentPane;
	private Graphics contentGraphic;
	private GraphicsDevice ScreenDevice;
	private GraphicsConfiguration ScreenConfig;
	private Insets insets;
	private DisplayMode originalDisplayMode,
			fullDisplayMode;
	private Cursor originalCursor,
			emptyCursor;
	private boolean isImgVolatile,
			isImgAccelerated;
	private boolean fullscreen = false,
			freezed = false,
			paused = false;
	private int windowWidth,
			windowHeight,
			frame_rate = 60,
			frame_wait = 1000 / frame_rate,
			nowFPS = 0,
			thisSec_FrameCount = 0;
	private long last_updateTime = System.currentTimeMillis(),
			last_secTime = last_updateTime;
	
	//private BufferedImage freezed_image;
	
	/**
	 * Método de obter variaveis 
	 */
	
	public Insets getInsets(){
		return insets;
	}
	public JFrame getFrame(){
		return Frame;
	}
	public int getFPS(){
		return nowFPS;
	}
	public GraphicsDevice getGraphicsDevice(){
		return ScreenDevice;
	}
	public GraphicsConfiguration getGraphicsConfiguration(){
		return ScreenConfig;
	}
	public boolean isImgVolatile(){
		return isImgVolatile;
	}
	public boolean isImgAccelerated(){
		return isImgAccelerated;
	}
	public boolean isFullScreen(){
		return fullscreen;
	}
	public int getFrameRate(){
		return frame_rate;
	}
	public int getMarginL(){
		return insets.left; 
	}
	public int getMarginT(){
		return insets.top; 
	}
	public int getMarginR(){
		return insets.right; 
	}
	public int getMarginB(){
		return insets.bottom; 
	}
	public boolean getPauseWhenLostFocus(){
		return Global.Input.getListener().pause_whenLostFocus;
	}
	
	/**
	 * Criação da classe
	 * @throws IOException 
	 */
	
	public Window(String windowTitle, int width, int height) throws IOException {
		// Se o robo não existir, cria-lo
		if(Global.Robot == null){
			try {
				Global.Robot = new Robot();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		// Desabilita o Cache do ImageIO
		ImageIO.setUseCache(false);
		// Cria o display fullsccreen do monitor
		ScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		originalDisplayMode = ScreenDevice.getDisplayMode();
		fullDisplayMode = new DisplayMode(800, 600, originalDisplayMode.getBitDepth(), originalDisplayMode.getRefreshRate());
		// Obtém configurações gráficas
		ScreenConfig = ScreenDevice.getDefaultConfiguration();
		isImgVolatile = ScreenConfig.getBufferCapabilities().getBackBufferCapabilities().isTrueVolatile();
		isImgAccelerated = ScreenConfig.getBufferCapabilities().getBackBufferCapabilities().isAccelerated();
		// Testa a aceleração gráfica
		if(!isImgVolatile)
			System.out.println("Imagens não seria criadas para a VRAM.");
		if(!isImgAccelerated)
			System.out.println("Imagens não poderão ser aceleradas.");
		// Cria o Frame
		Frame = new JFrame();
		// Configura o programa pra fechar ao pressionar sair
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Muda o titulo da janela
		Frame.setTitle(windowTitle);
		// Muda para um tamanho temporario
		Frame.setBounds(80, 80, 320, 320);
		// Painel do Conteudo
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setDoubleBuffered(false);
		Frame.setContentPane(contentPane);
		// Muda o fundo do Frame
		Frame.setBackground(Color.BLACK);
		// Cria cursor para esconder/mostrar
		originalCursor = contentPane.getCursor();
		emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0,0), "emptyCursor");
		// Esconde o cursor
		hideCursor();
		// Impede de redirencionar a janela
		Frame.setResizable(false);
		// Carrega e exibe o Icone
		try{
			Frame.setIconImage(ImageIO.read(new File("img/ico.png")));
		}catch(Exception e){}
		// Mostra a janela
		Frame.setVisible(true);
		// Pega as bordas da janela
		insets = Frame.getInsets();
		// Muda o tamanho e posiona a janela
		setSize(width, height);
		// Impede a janela de se auto repintar quando o SO pedir
		Frame.setIgnoreRepaint(true);
		// Associa o Input ao Listener
		Frame.addKeyListener(Global.Input.getListener());
		Frame.addMouseListener(Global.Input.getListener());
		Frame.addMouseMotionListener(Global.Input.getListener());
		Frame.addFocusListener(Global.Input.getListener());
		//Frame.addMouseWheelListener(Global.Input.listener);
		// Para de atualizar até a scene carregar
		setFreezed(true);
		// Desenha uma ao menos uma vez
		Frame.repaint();
		// Cria o BufferStrategy para a tela não flikar
		for(int i=1; i <= 3; i++){
			try{
				Frame.createBufferStrategy(2);
				break;
			}catch(Exception  e){
				if(i >= 3){
					System.out.println("Falha ao tentar criar o BufferStategy!");
					System.exit(1);
				}
			}
		}
		// Coloca o Graphics como primeiro contentGraphic
		contentGraphic = Frame.getBufferStrategy().getDrawGraphics();//getGraphics();
		// Coloca o primeiro Graphic como Graphic atual
		createGlobalGraphics();
	}
	
	public void setSize(int w, int h){
		// Configura posição(x, y) e tamanho(largura, altura)
		Frame.setBounds(0, 0, w + insets.left + insets.right,
				h + insets.top + insets.bottom);
		// Centraliza o programa na tela
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Frame.setLocation((dim.width-w)/2, (dim.height-h)/2);
		// Salva tamanho nas variaveis
		windowWidth = w;
		windowHeight = h;
	}
	
	public Window() throws IOException{
		this("J2DSGS (Java2D Simple Game System) -> Developed by: PedroHLC", 800, 600);
	}
	
	public void setFrameRate(int fr){
		if(fr < 1 | fr > 999) return;
		frame_rate = fr;
		frame_wait = 1000 / frame_rate;
	}
	
	public void hideCursor(){
		contentPane.setCursor(emptyCursor);
	}
	
	public void showCursor(){
		contentPane.setCursor(originalCursor);
	}
	
	public void setCursorIcon(String filepath){
		contentPane.setCursor(
				Toolkit.getDefaultToolkit().createCustomCursor(Global.Cache.getImage(filepath), new Point(0,0), "gcustomCursor")
				);
	}
	
	public boolean isCursorHidden(){
		return(contentPane.getCursor() == emptyCursor);
	}
	
	public void showMessageBox(String message){
		JOptionPane.showMessageDialog(Frame, message,Frame.getTitle(),JOptionPane.INFORMATION_MESSAGE);
	}
	
	@InTest
	public void goFullScreen(){
		// FIXME
		if(fullscreen == true) return;
		if(!ScreenDevice.isFullScreenSupported()) return;
		if(ScreenDevice.getFullScreenWindow() != Frame){
			ScreenDevice.setFullScreenWindow(Frame);
			ScreenDevice.setDisplayMode(fullDisplayMode);
		}
		fullscreen = true;
		insets = Frame.getInsets();
	}
	
	public void goWindowed(){
		if(fullscreen == false) return;
		ScreenDevice.setFullScreenWindow(null);
		fullscreen = false;
		insets = Frame.getInsets();
	}
	
	@InTest
	public void changeScreenState(){
		if(fullscreen){
			goWindowed();
		}else{
			goFullScreen();
		}
	}
	
	public void setFreezed(boolean state){
		if(freezed == state) return;
		/*if(state & !freezed){
			freezed_image = getScreenshot();
		}*/
		freezed = state;
	}
	
	public void setPauseWhenLostFocus(boolean value){
		Global.Input.getListener().pause_whenLostFocus = value;
	}
	
	public BufferedImage getScreenshot(){
		BufferedImage image, fimage;
		if(fullscreen)
			image = Global.Robot.createScreenCapture(new Rectangle(0, 0, windowWidth, windowHeight));
		else
			image = Global.Robot.createScreenCapture(new Rectangle(Frame.getX() + getMarginL(), Frame.getY() + getMarginT(), windowWidth, windowHeight));
		fimage = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
		fimage = ScreenConfig.createCompatibleImage(windowWidth, windowHeight, Transparency.OPAQUE);
		fimage.getGraphics().drawImage(image, 0, 0, windowWidth, windowHeight, null);
		return fimage;
	}
	
	public void saveScreenshot(String file_path){
		try {
		    ImageIO.write(getScreenshot(), "png", new File(file_path));
		    System.out.println("Um screenshot foi salvo como \"" + file_path + "\".");
		} catch (Exception e) {
		    System.out.println("Não foi possivel salvar uma screenshot como: "+file_path);
		}
	}
	
	public void saveScreenshot(){
		String times = Global.getNowTimeToStr("dd-mm-yy-hh-mm-ss");
		saveScreenshot("Screenshot-" + times + ".png");
	}
	
	private void createGlobalGraphics(){
		Global.Graphics = (Graphics2D) contentGraphic.create(getMarginL(), getMarginT(), windowWidth, windowHeight);
		Global.Graphics.setClip(0, 0, windowWidth, windowHeight);
	}
	
	private synchronized void sync(){
		if((System.currentTimeMillis() - last_secTime) >= 1000){
			last_secTime = System.currentTimeMillis();
			nowFPS = thisSec_FrameCount;
			thisSec_FrameCount = -1;
		}
		try {
			long totalTime = frame_wait - (System.currentTimeMillis() - last_updateTime);
			if(totalTime > 0){
				last_updateTime = System.currentTimeMillis() + totalTime;
				Thread.sleep(totalTime);//Frame.wait(totalTime);
			}else
				last_updateTime = System.currentTimeMillis();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		thisSec_FrameCount++;
	}
	
	private synchronized void dpaint(){	
	    // Carrega o novo graphics na memória
		Graphics new_cG = Frame.getBufferStrategy().getDrawGraphics();
		// Limpa a tela
		new_cG.clearRect(0, 0, windowWidth + getMarginL(), windowHeight + getMarginT());
		// Se for diferente do atual Graphics
		if(new_cG != contentGraphic){ 
			// Limpa o graphics atual da memória
			Global.Graphics.dispose();
			contentGraphic.dispose();
			// Atualiza os graficos para double-buffering
			contentGraphic = new_cG;
			createGlobalGraphics();
		}
	}
	
	public void setPaused(boolean value){
		paused = value;
	}
	
	public synchronized void update(){
		Frame.getBufferStrategy().show();
		sync();
		dpaint();
		// Para quando pausar
		while(paused){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
}
