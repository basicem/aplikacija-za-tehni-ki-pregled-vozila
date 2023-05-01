package services;

public final class UserSession {

    private static UserSession instance;
    private static String userName;
    private static boolean privilege;


    private UserSession(String userName, boolean privilege) {
        this.userName = userName;
        this.privilege = privilege;
    }


    public static UserSession getInstace(String korisnickoIme, boolean privilegija) {
        if(instance == null) {
            instance = new UserSession(korisnickoIme, privilegija);
        }
        return instance;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean getPrivileges() {
        return privilege;
    }

    public static void cleanUserSession() {
        userName = "";
        privilege = false;
        instance = null;
    }

}