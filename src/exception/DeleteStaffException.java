/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Gregory Wilson
 */
public class DeleteStaffException extends Exception{
    public String message() {
        return "Tidak bisa menghapus staff, staff sudah pernah melayani transaksi!";
    }
}
