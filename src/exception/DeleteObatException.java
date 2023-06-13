/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Gregory Wilson
 */
public class DeleteObatException extends Exception{
    public String message(){
        return "Tidak bisa delete, obat sudah masuk ke dalam transaksi!";
    }
}
