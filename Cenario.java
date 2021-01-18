/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package working;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 *
 * @author Yuri Domingos 
 * Data   : 14 - 01 - 2021
 * Objectivo : Construir o paint que desnha em tempo real 
 */
public class Cenario extends JPanel implements Runnable  

{
    
    private  Graphics2D ig2;
    private ArrayList<Point> lapis = new ArrayList<>();
    private JButton choose ;
    private Color color;
    private JButton salvar;
    int width = 500, height = 500;
    BufferedImage bi;
    private int [] arrayX =  new int[10000];
    private int [] arrayY =  new int[10000];
    private AnimationAdobeYuri animationAdobeYuri = new AnimationAdobeYuri();
    private Thread thread;
    private JMenuBar barra = new JMenuBar();
    private JMenu file, edit, view, insert, modify, text, commands, control,debug,window,help;
    private JMenuItem  new_project, new_file, open_project,openRecentProject,SaveAs ;
    private JMenuItem new_pictur, efects, texto;
    private Font fontDoMenu = new Font("Serief",Font.BOLD,16);
    private JPanel PanelPrincipal,PanelMenu,PanelPaleta,PanelStettings,PanelTimeLine; 
    private Image loadPic;
    private boolean picturISLoaded = false,  freeDraw = false;
    private JTextField search;
    
    
   
    
    public Cenario() {
        
        
       //--- Configurações dos páneis
       
       
        salvar = new JButton("Save Frame");
        choose = new JButton("Cor ");
        search = new JTextField(3);
        bi     = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        thread = new Thread(this);
        thread.start();
        
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            
                color = JColorChooser.showDialog(null, "Seleciona a cor ", Color.RED);
                
            }
        });
        
   
        add(choose);
      
        
        addMouseMotionListener(new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent me) {
                
                if ( freeDraw )
                {
                     lapis.add(me.getPoint());
                     repaint();
                }
               
         
            }

            @Override
            public void mouseMoved(MouseEvent me) {
             
            }

            
        });
        
        
       
       init();
    }
    
    public void init()
    {
        
        JFrame frame = new JFrame();
        frame.setTitle("Adobe Yuri  ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400,1200);
        //frame.setResizable(false);
        frame.getContentPane().add(this);
       // frame.setLayout(new BorderLayout(8,6));
        frame.setBackground(Color.gray);
   
        barra.setBackground(Color.GRAY);
      
       
        frame.setJMenuBar(barra);
        file = new JMenu("File");
     
        
        file.setFont(fontDoMenu);
        
        edit = new JMenu("Edit");
        edit.setFont(fontDoMenu);
         
         
        view = new JMenu("View");
        view.setFont(fontDoMenu);
        
        
        insert = new JMenu("Insert");
        insert.setFont(fontDoMenu);
        
        modify = new JMenu("Modify");
        modify.setFont(fontDoMenu);
        
        text = new JMenu("Text");
        text.setFont(fontDoMenu);
        
        commands = new JMenu("Commands");
        commands .setFont(fontDoMenu);
        
        
        control = new JMenu("Control");
        control.setFont(fontDoMenu);
        
        debug = new JMenu("Debug");
        debug .setFont(fontDoMenu);
        
        
        
        window = new JMenu("Window");
        window.setFont(fontDoMenu);
        
        help = new JMenu("Help");
        help.setFont(fontDoMenu);
        
        //-- Adicionar os items do menu principal 
        
        barra.add(file);
        barra.add(edit);
        barra.add(view);
        barra.add(insert);
        barra.add(modify);
        barra.add(text);
        barra.add(commands);
        barra.add(control);
        barra.add(debug);
        barra.add(window);
        barra.add(help);
        
        
        //  Inicializar & Adicionar os sub-items do menu 
        
         new_project = new JMenuItem("New Project.. Ctrl+Shift+N");
         new_project.setIcon(new ImageIcon(this.getClass().getResource("hat32.png")));
         
         new_file = new JMenuItem("New File.. Ctrl+N");
         new_file.setIcon(new ImageIcon(this.getClass().getResource("tec24.png")));
         
         open_project = new JMenuItem("Open project.. Ctrl+Shift+O");
         open_project .setIcon(new ImageIcon(this.getClass().getResource("yes.png")));
         openRecentProject = new JMenuItem("Open Recent project");
        
         
         //--- Seção para Inserir a imagem em tempo real da memória do utilizador 
         //new_pictur, efects, texto;
         new_pictur = new JMenuItem("Inserir Imagem");
         efects     = new JMenuItem("Adicionar Efeitos 2D");
         texto      = new JMenuItem("Adicionar cenário como Frame "); // podemos adicionar imagem 
         
         
         
         SaveAs = new JMenuItem("Save as");
         
        
         SaveAs.setIcon(new ImageIcon(this.getClass().getResource("sistema32.png")));
        
        SaveAs.addActionListener(new ActionListener(){
             
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                 for ( int i = 0; i < arrayX.length; i++)
                 {
                      ig2.fill(new Ellipse2D.Double(arrayX[i], arrayY[i], 10,10));
                 }
                 
                 //-- Action in this line 
                 
                  String nome_imagem = gerarString(6, "ABCDEYabcdefghijkl");
                    try {
             
                            ImageIO.write(bi, "PNG", new File("Imagens_da_animations/"+nome_imagem+".PNG"));
                            
                          
                         JOptionPane.showMessageDialog(null, "Frame Guardado com sucesso ");
                          
   
            
                        } catch (IOException ex) {
          
                                  ex.printStackTrace();
                        }
                    
                    }
            
             
         });
        
       
        //--
        file.add(new_project);
        file.add(new_file);
        file.add(open_project);
        file.add(openRecentProject);
        file.add( SaveAs);
        
        //-- adicionar para o insert 
        
        insert.add(new_pictur);
        insert.add(efects );
        insert.add(texto );
        
        new_pictur.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae) {
        
                JFileChooser choose_pictur = new JFileChooser();
                choose_pictur.setCurrentDirectory(new File("."));
                
                int return_object = choose_pictur.showOpenDialog(null);
                
                
                if ( return_object == 0)
                {
                    File pictur = choose_pictur.getSelectedFile();
                    
                    try {
                        
                           loadPic = ImageIO.read(pictur);
                           picturISLoaded = true;
                        
                        
                    }catch(IOException error)
                    {
                        JOptionPane.showMessageDialog(null, "Erro ! Lamentamos mas não conseguimos carregar a imagem ");
                    }
                    
                }
            }
            
        });
  
        PanelPaleta = new JPanel();
       // PanelPaleta.setBorder(new LineBorder(Color.BLACK,3));
        PanelPaleta.setBackground(Color.DARK_GRAY);
        PanelPaleta.setPreferredSize(new Dimension(95,150));
        PanelPaleta.setLayout(new FlowLayout(20,8,12));
        
        //-- Adicionar as imagens do cantinho 
        
        
        JLabel paletinha = new JLabel("");
        JLabel paletinha2 = new JLabel("");
        JLabel paletinha3 = new JLabel("");
        JLabel paletinha4 = new JLabel("");
        JLabel paletinha5 = new JLabel("");
        JLabel paletinha6 = new JLabel("");
        JLabel paletinha7 = new JLabel("");
        JLabel paletinha8 = new JLabel("");
        JLabel paletinha9 = new JLabel("");
        JLabel paletinha10 = new JLabel("");
        JLabel paletinha11 = new JLabel("");
        JLabel paletinha12 = new JLabel("");
        JLabel paletinha13 = new JLabel("");
        JLabel paletinha14 = new JLabel("");
        
        paletinha.setIcon(new ImageIcon(this.getClass().getResource("Icon4.png")));
        paletinha2.setIcon(new ImageIcon(this.getClass().getResource("U7d.png")));
        paletinha3.setIcon(new ImageIcon(this.getClass().getResource("Icoc11.png")));
        paletinha4.setIcon(new ImageIcon(this.getClass().getResource("Un2.png")));
        paletinha5.setIcon(new ImageIcon(this.getClass().getResource("Unti-2.png")));
        paletinha6.setIcon(new ImageIcon(this.getClass().getResource("Untitled-2.png")));
        paletinha7.setIcon(new ImageIcon(this.getClass().getResource("Icon3.png")));
        paletinha8.setIcon(new ImageIcon(this.getClass().getResource("Icoc11.png")));
        paletinha9.setIcon(new ImageIcon(this.getClass().getResource("tudy4.png")));
        paletinha10.setIcon(new ImageIcon(this.getClass().getResource("Un2.png")));
        paletinha11.setIcon(new ImageIcon(this.getClass().getResource("Icon5.png")));
        paletinha12.setIcon(new ImageIcon(this.getClass().getResource("Icon2.png")));
        paletinha13.setIcon(new ImageIcon(this.getClass().getResource("klo.png")));
        paletinha14.setIcon(new ImageIcon(this.getClass().getResource("Icoc11.png")));
        
        
        PanelPaleta.add(paletinha);
        PanelPaleta.add(paletinha2);
        PanelPaleta.add(paletinha3);
        PanelPaleta.add(paletinha4);
        PanelPaleta.add(paletinha5);
        PanelPaleta.add(paletinha6);
        PanelPaleta.add(paletinha7);
        PanelPaleta.add(paletinha8);
        PanelPaleta.add(paletinha9);
        PanelPaleta.add(paletinha10);
        PanelPaleta.add(paletinha11);
        PanelPaleta.add(paletinha12);
        PanelPaleta.add(paletinha13);
        PanelPaleta.add(paletinha14);

      
        //-- Eventos dos Icons da paleta 
        
        
          paletinha2.addMouseListener(new MouseAdapter(){
             
           @Override
                public void mouseClicked(MouseEvent e) {
                  
                    //System.out.println(" O usuário Clicou em mim");
                    freeDraw = true;
                }
             
         });
        //-- Teremos o painel do TimeLine
        
        PanelTimeLine = new JPanel();
      //  PanelTimeLine.setBorder(new LineBorder(Color.white,3));
        PanelTimeLine.setPreferredSize(new Dimension(100,100));
        PanelTimeLine.setBackground(Color.DARK_GRAY);
        
        
        //-- Configuração do settings Panel 
        
        PanelStettings = new JPanel();
        //PanelStettings.setBorder(new LineBorder(Color.BLACK,3));
        PanelStettings.setPreferredSize(new Dimension(270,150));
        PanelStettings.setBackground(Color.DARK_GRAY);
        PanelStettings.add(search);
        
       
       frame.add(PanelPaleta, BorderLayout.WEST);
       frame.add(PanelTimeLine, BorderLayout.SOUTH);
       frame.add(PanelStettings, BorderLayout.EAST);
     
       
       
        
        frame.setVisible(true);
        
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g.create();
        
        ig2 = bi.createGraphics(); // this line allow the user put image in our local memory 
        
        
        graphics2D.clearRect(0, 0, this.getWidth(), this.getHeight());
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        graphics2D.setColor(color);
        ig2.setColor(color);
        
        
        
        
        graphics2D.setStroke(new BasicStroke(32));
       
      /*  for ( Point lapis_para_desenhar  : lapis )
        {
            graphics2D.fill(new Ellipse2D.Double(lapis_para_desenhar.x, lapis_para_desenhar.y, 10,10));
          
            
        }
        */
      
         if (picturISLoaded)
         {
           graphics2D.drawImage(loadPic, this.getWidth()/2, this.getHeight()/2, null);
         }
         
         
        
        for (int i = 0; i < lapis.size(); i++)
        {
               graphics2D.fill(new Ellipse2D.Double(lapis.get(i).x, lapis.get(i).y, 10,10));
               arrayX[i] = lapis.get(i).x;
               arrayY[i] = lapis.get(i).y;
        }        
        
      
        //---- animation 
        
      // animationAdobeYuri.paint(graphics2D);
       
       
    
        g.dispose(); // clone design cenary 
    }
    
    
        public  String gerarString(int tamanho, String letras)
        { 
            Random random = new Random(); 
            StringBuilder out_put = new StringBuilder();
            
            for (int i = 0; i < tamanho; i++)
            { 
               out_put.append(letras.charAt(random.nextInt(letras.length()))); 
             }
            return out_put.toString();
        
        }

    @Override
    public void run() {
       
        
        while(true)
        {
            
            try {
                
                animationAdobeYuri.updateCenary();
                Thread.sleep(40);
                repaint();
                
            } catch (InterruptedException ex) {
                
                System.out.println("All right");
            }
        }
    }
    
    
    
}
