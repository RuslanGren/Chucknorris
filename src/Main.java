import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String choice;
        do {
            System.out.println("Please input operation (encode/decode/exit):");
            choice = scanner.nextLine();
            switch (choice.toLowerCase()) {
                case "encode" -> encode();
                case "decode" -> decode();
                case "exit" -> System.out.println("Bye!");
                default -> System.out.printf("There is no '%s' operation\n", choice);
            }
        } while (!choice.equals("exit"));
        scanner.close();
    }

    private static void encode() {
        System.out.println("Input string:");
        String s = scanner.nextLine();
        System.out.println("Encoded string:");
        System.out.println(chuckNorrisEncode(binaryEncode(s)));
    }

    private static void decode() {
        System.out.println("Input encoded string:");
        String s = scanner.nextLine();
        String decode;
        try {
            if (s.replaceAll("((0{1,2} 0+) ?)+", "").length() != 0)
                throw new RuntimeException();
            decode=binaryDecode(chuckNorrisDecode(s));
        } catch (Exception e) {
            System.out.println("Encoded string is not valid.");
            return;
        }
        System.out.println("decoded string:");
        System.out.println(decode);
    }

    static String chuckNorrisEncode(String bin) {
        String[] e = {"00","0"};
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bin.length();) {
            int actual = i;
            while (i < bin.length() && bin.charAt(i) == bin.charAt(actual)) ++i;
            str.append(e[bin.charAt(actual)-'0']).append(" ")
                    .append("0".repeat(i - actual)).append(" ");
        }
        return str.toString();
    }

    static String chuckNorrisDecode(String encrypt) {
        StringBuilder str = new StringBuilder();
        String[] s =encrypt.split(" ");
        for (int i = 0; i < s.length; i+=2) {
            str.append(s[i + 1].replaceAll("0", s[i].equals("0") ? "1" : "0"));
        }
        return str.toString();
    }

    static String binaryEncode(String s) {
        StringBuilder str = new StringBuilder();
        for (Character c : s.toCharArray()) str.append("%7s".formatted(Integer.toBinaryString(c))
                .replace(" ", "0"));
        return str.toString();
    }

    static String binaryDecode(String bin) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bin.length(); i+=7) {
            str.append((char)Integer.parseInt(bin.substring(i, i + 7), 2));
        }
        return str.toString();
    }
}