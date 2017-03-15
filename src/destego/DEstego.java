/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package destego;

/**
 *
 * @author ANGGA
 */
public class DEstego {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        imagingRGB test = null;
        int[][] matrix = null;
        try {
            test = new imagingRGB(imagingRGB.COLOR_RED);
            matrix = test.read("C:\\kita.jpg");
            System.out.println("Cover data:");
            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix.length; x++) {
                    System.out.print(String.format("%3s", matrix[y][x]) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Konverter
        System.out.println("panjang :" + matrix.length);
        String message = "angga";
        char[] temp = message.toCharArray();
        message = "";
        for (char tmp : temp) {
            message += String.format("%7s", Integer.toBinaryString((int) tmp)).replace(' ', '0');
        }
        System.out.println("Secret message: " + message);
        // Proses penyisipan
        System.out.println("Stego data:");

        int l, h, haksen, xaksen, yaksen; // variabelnya

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x += 2) {
                if (message.length() > 0) {
                    l = ((matrix[y][x] + matrix[y][x + 1]) / 2);
                    h = matrix[y][x] - matrix[y][x + 1];
                    haksen = (2 * h) + Integer.parseInt(message.substring(0, 1));
                    message = message.substring(1);
                    xaksen = (int) (l + (Math.floor((double)(haksen + 1) / 2)));
                    yaksen = (int) (l - (Math.floor((double)haksen / 2)));
                    //mengganti
                    matrix[y][x] = xaksen;
                    matrix[y][x + 1] = yaksen;
                }

            }
        }
        // kirim ke array imagingRGB
        test.matrix = matrix;
        // cek dahulu
        for (int i = 0; i < test.matrix.length; i++) {
            System.out.println("================");
            for (int j = 0; j < test.matrix[i].length; j++) {
                System.out.print(test.matrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("panjang :" + matrix.length);
        
        // jadikan gambar
        try {
            test.write("C:\\DE-stego.png");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        
        /* EXTRACT GAMBAR
        rumusnya tinggal di balik saja 
        */
        try {
            test = new imagingRGB(imagingRGB.COLOR_RED);
            matrix = test.read("C:\\DE-stego.png");
            System.out.println("Stego data:");
            for(int y = 0; y < matrix.length; y++) {
                for(int x = 0; x < matrix.length; x++) {
                    System.out.print(String.format("%3s", matrix[y][x]) + " ");
                }
                System.out.println();
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        // Proses ekstraksi
        message = "";
        String tmp = "";
        for(int y = 0; y < matrix.length; y++) {
            for(int x = 0; x < matrix[y].length; x+=2) {
                tmp += Math.abs((matrix[y][x]-matrix[y][x+1]) % 2);
//                if(tmp.length() == 7) {
//                    message += (char)Integer.parseInt(tmp, 2);
//                    tmp = "";
//                }
            }
        }
//        for (int i = 0; i < tmp.length(); i+=7) {
//            
//            message += (char)Integer.parseInt(tmp);
//        }
        
        System.out.println("Temp ="+tmp);
        System.out.println("Secret message: " + message);
        
    }

}
