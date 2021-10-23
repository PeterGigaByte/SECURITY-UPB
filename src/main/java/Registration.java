//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Do suboru s heslami ulozit aj sal.                           //
// Uloha2: Pouzit vytvorenu funkciu na hashovanie a ulozit heslo        //
//         v zahashovanom tvare.                                        //
//////////////////////////////////////////////////////////////////////////


import java.util.regex.Pattern;

public class Registration {
    private static final Pattern[] inputRegexes = new Pattern[1];
    static {
        inputRegexes[0] = Pattern.compile(".*[A-Z].*");
    }
    protected static Database.MyResult registracia(String meno, String heslo) throws Exception{
        if (Database.exist(meno)){
            System.out.println("Meno je uz zabrate.");
            return new Database.MyResult(false, "Meno je uz zabrate.");
        }
        if(heslo.length()<6){
            return new Database.MyResult(false, "Príliš krátke heslo!");
        }
        if(!heslo.matches(".*\\d.*")){
            return new Database.MyResult(false, "Heslo musí obsahovať minimálne jednu číslicu.");
        }
        if(!isMatchingRegex(heslo)){
            return new Database.MyResult(false, "Heslo musí obsahovať minimálne jedno veľké písmeno.");
        }

        else {
            /*
            *   SaltHashing sa obvykle uklada ako tretia polozka v tvare [meno]:[heslo]:[salt].
            */
            byte[] salt = SaltHashing.generateSalt();
            byte[] passwordSalted = SaltHashing.getHashWithSalt(heslo,salt);
            String passwordInStringSalted = SaltHashing.bytetoString(passwordSalted);
            String saltInString = SaltHashing.bytetoString(salt);
            return Database.addToH2(meno,passwordInStringSalted,saltInString);
        }
    }

    private static boolean isMatchingRegex(String input) {
        boolean inputMatches = true;
        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex.matcher(input).matches()) {
                inputMatches = false;
            }
        }
        return inputMatches;
    }
}
