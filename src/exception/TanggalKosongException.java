/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Gregory Wilson
 */
public class TanggalKosongException extends Exception{
    public String message(){
        return "[!] TANGGAL TIDAK BOLEH KOSONG [!]";
    }
}
