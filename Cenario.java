/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package working;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Yuri Domingos 
 * Data   : 14 - 01 - 2021
 * Objectivo : Construir o paint que desnha em tempo real 
 */
public class Cenario extends JPanel implements Runnable  {
    
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
    private Font fontDoMenu = new Font("Serief",Font.BOLD,16);
    
 

   
   
    
    public Cenario() {
        
        
       
        
        salvar = new JButton("Save Frame");
        choose = new JButton("Cor ");
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
        add(salvar, BorderLayout.AFTER_LAST_LINE);
        
        addMouseMotionListener(new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent me) {
                
                lapis.add(me.getPoint());
                repaint();
         
            }

            @Override
            public void mouseMoved(MouseEvent me) {
             
            }

            
        });
        
        
        // second option 
        
          
        salvar.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent ae) {
               
                 
                 for ( int i = 0; i < arrayX.length; i++)
                 {
                      ig2.fill(new Ellipse2D.Double(arrayX[i], arrayY[i], 10,10));
                 }
                 
                 //-- Action in this line 
                 
                  String nome_imagem = gerarString(6, "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxyz");
                    try {
             
                            ImageIO.write(bi, "PNG", new File("Imagens_da_animations/"+nome_imagem+".PNG"));
                            
                          
                             System.out.println(" Imagem adicionada com suscesso");
                          
            
                        } catch (IOException ex) {
          
                                  ex.printStackTrace();
                        }
                    
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
         
        barra.setBackground(Color.GRAY);
        barra.setForeground(Color.white);
       
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
                 
                  String nome_imagem = gerarString(6, "ABCDEFGHIJKLMNOPQRSTUVWXYabcdefghijklmnopqrstuvwxyz");
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
        
        frame.getContentPane().add(this);
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
        for (int i = 0; i < lapis.size(); i++)
        {
               graphics2D.fill(new Ellipse2D.Double(lapis.get(i).x, lapis.get(i).y, 10,10));
               arrayX[i] = lapis.get(i).x;
               arrayY[i] = lapis.get(i).y;
        }        
        
      
        //---- animation 
        
        //animationAdobeYuri.paint(graphics2D);
        
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
