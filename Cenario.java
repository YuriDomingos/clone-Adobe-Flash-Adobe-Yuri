/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package working;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Yuri Domingos 
 * Data   : 14 - 01 - 2021
 * Objectivo : Construir o paint que desnha em tempo real 
 */
public class Cenario extends JPanel  {
    
    private  Graphics2D ig2;
    private ArrayList<Point> lapis = new ArrayList<>();
    private JButton choose ;
    private Color color;
    private JButton salvar;
    int width = 500, height = 500;
    BufferedImage bi;
    private int [] arrayX =  new int[1000];
    private int [] arrayY =  new int[1000];
    
 

   
   
    
    public Cenario() {
        
        

        salvar = new JButton("Save Frame");
        choose = new JButton("Cor ");
        bi     = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        
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
        frame.setTitle("Paint ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
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
    
    
    
}
