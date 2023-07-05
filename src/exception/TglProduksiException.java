package exception;

public class TglProduksiException extends Exception {

    public String message() {
        return "Tanggal produksi harus sebelum tanggal kadaluarsa!";
    }
}
