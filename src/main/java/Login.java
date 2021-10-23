//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha2: Upravte funkciu na prihlasovanie tak, aby porovnavala        //
//         heslo ulozene v databaze s heslom od uzivatela po            //
//         potrebnych upravach.                                         //
// Uloha3: Vlozte do prihlasovania nejaku formu oneskorenia.            //
//////////////////////////////////////////////////////////////////////////

import java.io.IOException;
import java.util.StringTokenizer;


public class Login {
    protected static Database.MyResult prihlasovanie(String meno, String heslo) throws IOException, Exception{
        /*
        *   Delay je vhodne vytvorit este pred kontolou prihlasovacieho mena.
        */
        Thread.sleep(200);//delay
        Database.MyResult account = Database.find(meno,heslo);
        if (!account.getFirst()){
            return new Database.MyResult(false, "Nespravne meno.");
        }
        else {
           return account;
        }
    }
}
