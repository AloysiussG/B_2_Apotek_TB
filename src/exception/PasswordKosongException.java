/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author ASUS
 */
public class PasswordKosongException extends Exception{
    
    public String message(){
        return "Password tidak boleh kosong!";
    }
}
