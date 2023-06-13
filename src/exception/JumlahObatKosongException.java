/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Gregory Wilson
 */
public class JumlahObatKosongException extends Exception{
    public String message(){
        return "[!] JUMLAH OBAT TIDAK BOLEH KOSONG [!]";
    }
}
