package exception;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
public class NoHpKosongException extends Exception {
    
    public String message(){
        return "No. Hp tidak boleh kosong!";
    }
}
