package emailSender;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            EmailSender sender = new EmailSender("smtp.utb.cz", 25);
            sender.send("l_kollar@utb.cz", "l_kollar@utb.cz", "Email from Java", "Funguje to?\nSnad...");
            sender.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}