/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package working;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author : Yuri Domingos
 * Data    : 15 - 01 - 2021
 * Objectivo : Construir as funções que podem nos gerar uma animação completa 
 */
public class AnimationAdobeYuri {
    
    
    private BufferedImage objecto_desenhado[];
    private int index ;
    private float timer ;
    
    

    public AnimationAdobeYuri()
    {
        index = 0;
        objecto_desenhado = new BufferedImage[3];
        
        for ( int i = 0; i <= 3; i++)
        {
            try {
                
                String pictures_place = "Imagens_da_animations/Run"+(i+1)+".PNG";
               // System.out.println("Carregando a imagem "+pictures_place);*/
                
                objecto_desenhado [i] = ImageIO.read(new File(pictures_place));
                
                
            } catch (IOException ex) {
                System.out.println("Imagem não foram carregadas corretamente ");
              
            }
            
        }
        
       updateCenary(); 
    }
    
    public void updateCenary()
    {
        timer++;
        
        if ( timer >= 10)    
        {
            index++;
        
             if( index == 3)
             {
                index = 0;
             }
             
          //   System.out.println("Atualizado");
             timer = 0;
        }
        
    }
    
    public void paint(Graphics2D graphics2D)
    {
        
        graphics2D.drawImage(objecto_desenhado[index], 50, 50, null);
        
      //  System.out.println("Objecto desenhado na tela com sucesso ");
        
    }
    
    
    
    
    
    
}
