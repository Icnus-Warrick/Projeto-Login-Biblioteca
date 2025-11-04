package br.com.warrick.biblioteca.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 02/11/2025
 */
public class ImageLoader {
    
    /**
 * Projeto: Biblioteca 
     * Carrega uma imagem da pasta de recursos com a qualidade otimizada
     * @param path Caminho para a imagem na pasta de recursos (ex: "/Imagem/PortaE.png")
     * @param width Largura desejada (use -1 para largura original)
     * @param height Altura desejada (use -1 para altura original)
     * @return ImageIcon com a imagem carregada e redimensionada com qualidade
     */
    public static ImageIcon loadImage(String path, int width, int height) {
        try {
            // Carrega a imagem usando o class loader
            URL imgURL = ImageLoader.class.getResource(path);
            if (imgURL == null) {
                System.err.println("Não foi possível encontrar o arquivo: " + path);
                return null;
            }
            
            // Carrega a imagem original
            Image originalImage = ImageIO.read(imgURL);
            
            // Define as dimensões para redimensionamento, se necessário
            int newWidth = (width > 0) ? width : originalImage.getWidth(null);
            int newHeight = (height > 0) ? height : originalImage.getHeight(null);
            
            // Cria uma nova imagem com o tamanho desejado e alta qualidade
            Image scaledImage = originalImage.getScaledInstance(
                newWidth, 
                newHeight, 
                Image.SCALE_SMOOTH
            );
            
            // Cria um BufferedImage para melhor qualidade
            BufferedImage bufferedImage = new BufferedImage(
                newWidth, 
                newHeight, 
                BufferedImage.TYPE_INT_ARGB
            );
            
            // Desenha a imagem redimensionada no BufferedImage
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );
            g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
            );
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );
            
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            
            return new ImageIcon(bufferedImage);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a imagem: " + path);
            e.printStackTrace();
            return null;
        }
    }
}
