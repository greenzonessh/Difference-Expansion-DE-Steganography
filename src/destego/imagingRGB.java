/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package destego;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ANGGA
 */
public class imagingRGB {
    public static final byte COLOR_GRAY  = 0;
    public static final byte COLOR_RED   = 1;
    public static final byte COLOR_GREEN = 2;
    public static final byte COLOR_BLUE  = 3;
    
    private BufferedImage cover;
    public  int[][] matrix;
    public  byte color;

    public imagingRGB(byte color) {
        this.color = color;
    }
    
    public int[][] read(String coverPath) throws IOException {
        this.cover  = ImageIO.read(new File(coverPath));
        this.matrix = new int[this.cover.getHeight()][this.cover.getWidth()];
        Color pixel = null;
        for(int y = 0; y < this.matrix.length; y++) {
            for(int x = 0; x < this.matrix[y].length; x++) {
                pixel = new Color(this.cover.getRGB(x, y));
                switch(this.color) {
                    case COLOR_RED:
                        this.matrix[y][x] = pixel.getRed();
                        break;
                    case COLOR_GREEN:
                        this.matrix[y][x] = pixel.getGreen();
                        break;
                    case COLOR_BLUE:
                        this.matrix[y][x] = pixel.getBlue();
                        break;
                    default:
                        this.matrix[y][x] = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
                        break;
                }
            }
        }
        return this.matrix;
    }
    
    public void write(String stegoPath) throws IOException {
        BufferedImage stego = new BufferedImage(matrix[0].length, matrix.length, BufferedImage.TYPE_4BYTE_ABGR);
        Color pixel = null;
        for(int y = 0; y < this.matrix.length; y++) {
            for(int x = 0; x < this.matrix[y].length; x++) {
                pixel = new Color(this.cover.getRGB(x, y));
                switch(this.color) {
                    case COLOR_RED:
                        pixel = new Color(this.matrix[y][x], pixel.getGreen(), pixel.getBlue());
                        break;
                    case COLOR_GREEN:
                        pixel = new Color(pixel.getRed(), this.matrix[y][x], pixel.getBlue());
                        break;
                    case COLOR_BLUE:
                        pixel = new Color(pixel.getRed(), pixel.getGreen(), this.matrix[y][x]);
                        break;
                    default:
                        pixel = new Color(this.matrix[y][x], this.matrix[y][x], this.matrix[y][x]);
                        break;
                }
                stego.setRGB(x, y, pixel.getRGB());
            }
        }
        ImageIO.write(stego, "png", new File(stegoPath));
    }
}
