public class Fido {
    private final static String CHATBOT_NAME = "Fido";

    public static void main(String[] args) {
        String greetMsg = String.format("Hello! I'm %s\nWhat can I do for you?", CHATBOT_NAME);
        String byeMsg = "Bye. Hope to see you again soon!";
        String seperator = "____________________________________________________________";

        System.out.println(seperator);
        System.out.println(greetMsg);
        System.out.println(seperator);
        System.out.println(byeMsg);
        System.out.println(seperator);
    }
}
