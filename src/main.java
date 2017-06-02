import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Avinash Dhillor on 3/24/2017.
 */
public class main {

    private static String addlink = "https://www.youtube.com/watch?v=";
    private static String Currentdir = System.getProperty("user.dir");
    private static String Filename = Currentdir+ "\\tmp\\data.txt";


    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("*****CREATED BY AVINASH DHILLOR*****");
        System.out.println("NOTE:If you want to Download full Playlist \n     then START point will be 0 and \n     END point will also be 0 \n(E.g. PlayListlink 0 0)\n             OR\nIf you want a particular range then \n(E.g. PlayListlink 2 19)");
        System.out.println("\n");
        System.out.println("Enter Youtube Playlist Link followed by START POINT and END POINT:");
        String inputlink = scan.next();
        int start = scan.nextInt();
        int end = scan.nextInt();
        System.out.println("Chrome setting will be opened then select download location in chrome \nbrowser within 30 seconds and wait");
        Thread.sleep(3000);
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\drive\\chromedriver.exe" );

        WebDriver w = new ChromeDriver();

        Vector<String> vector = new Vector<String>();

            w.get("chrome://settings/");
            Thread.sleep(30000);
            w.get(inputlink);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Filename, false))) {
            bw.write(w.getPageSource());
        }
        Scanner s = new Scanner(new FileReader(Filename));
        while (s.hasNextLine()) {
            String abc[] = s.nextLine().split(" ");
            for (int i = 0; i < abc.length; i++) {
                do {
                    if (abc[i].startsWith("data-video-id=")) {
                        String sol = abc[i].substring(15, 26);
                        vector.add(addlink + sol);
                    }
                } while (abc[i] == "</ol>");
            }
        }
        if(start == 0 && end == 0) {
            try {
                for (String downlaod : vector) {
                    w.get("Http://en.savefrom.net");
                    Thread.sleep(1500);
                    w.findElement(By.id("sf_url")).sendKeys(downlaod);
                    Thread.sleep(1500);
                    w.findElement(By.id("sf_submit")).click();
                    Thread.sleep(1500);
                    w.findElement(By.className("def-btn-box")).click();
                    start++;
                    System.out.print("\rDownloading video number :" + start);
                }
                Thread.sleep(7000);
                w.get(Currentdir+ "\\html\\complete.html");
                System.out.println("Completed!");

            } catch (Exception ex) {
                Thread.sleep(7000);
                w.get(Currentdir+ "\\html\\error.html");
                System.out.println("\nFailed after"+ start + "video! Your internet Connection must be slow");
                Thread.sleep(10000);
            }
        }
        else {
            int number = 0;
            try {
                for (int i = start; i <= end; i++) {
                    String download1 = vector.elementAt(i - 1);
                    w.get("Http://en.savefrom.net");
                    Thread.sleep(1500);
                    w.findElement(By.id("sf_url")).sendKeys(download1);
                    Thread.sleep(1500);
                    w.findElement(By.id("sf_submit")).click();
                    Thread.sleep(1500);
                    w.findElement(By.className("def-btn-box")).click();
                    number = i;
                    System.out.print("\rDownloading video number :" + i);
                }
                Thread.sleep(7000);
                w.get(Currentdir + "\\html\\complete.html");
                System.out.println("Completed!");
            } catch (Exception e ) {
                Thread.sleep(7000);
                w.get(Currentdir+ "\\html\\error.html");
                System.out.println("\nFailed after"+ number + "video! Your internet Connection must be slow");

                Thread.sleep(10000);
            }
        }
    }
}
