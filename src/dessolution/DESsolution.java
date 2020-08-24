/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dessolution;

import java.util.Scanner;

/**
 *
 * @author PRANTO
 */
public class DESsolution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String key=new String("");
        String plainText=new String("");
        
        Scanner in=new Scanner(System.in);
        
        System.out.print("Key : ");
        key=in.nextLine();
        //System.out.println("");
        
        System.out.print("Plaintext : ");
        plainText=in.nextLine();
        System.out.println("");
        
        //System.out.println(key+plainText+"hi");
        Encryption encryp=new Encryption();
        encryp.doEncrypt(key, plainText);
    }
    
}
