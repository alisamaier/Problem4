import java.util.function.IntPredicate;

public class Main {

    private static int checkPassword(String password) {
        //If the length of the password is greater than 20, remove the characters.
        if (password.length() > 20) {
            return removeCharacters(password);
        }

        int number = numberOfElementsToAddToEliminateTheSubsequencesOfIdenticalCharacters(password);

        //If number=0 it means that the password does not contain three repeating characters in a row.
        //Verify the password to respect the conditions for all 3 cases:length<6, 6<=length<=20 and length>20.
        if (number == 0) {
            if (password.length() < 6) {
                //Add necessary characters until the length of the password is at least 6.
                int numberOfValuesNecessary = 0;
                if (!containsLowerCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsUpperCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsNumber(password)) {
                    numberOfValuesNecessary++;
                }
                if (numberOfValuesNecessary + password.length() < 6) {
                    numberOfValuesNecessary += 6 - password.length() - numberOfValuesNecessary;
                }
                return numberOfValuesNecessary;
            }
            if (password.length() > 5 && password.length() < 21) {
                //Replace characters in order to have all conditions fulfilled.
                int numberOfValuesNecessary = 0;
                if (!containsLowerCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsUpperCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsNumber(password)) {
                    numberOfValuesNecessary++;
                }
                return numberOfValuesNecessary;
            }
            if (password.length() > 20) {
                //Must have all mandatory characters and delete until the password's length is less than 20.
                int numberOfValuesNecessary = 0;
                if (!containsLowerCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsUpperCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsNumber(password)) {
                    numberOfValuesNecessary++;
                }
                numberOfValuesNecessary += password.length() - 20;
                return numberOfValuesNecessary;
            }
        }

        //If number is different than 0, it means we have at least three repeating characters in a row.
        //Verify for the 3 cases as in the previous situation.

        if (number > 0) {
            //If the length of the password is less that 6, we add characters but we keep in mind the repeating characters.
            if (password.length() < 6) {

                int numberOfValuesNecessary = 0;
                if (!containsLowerCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsUpperCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsNumber(password)) {
                    numberOfValuesNecessary++;
                }
                if (number > numberOfValuesNecessary) {
                    numberOfValuesNecessary = number;
                }
                if (6 > numberOfValuesNecessary + password.length()) {
                    numberOfValuesNecessary += 6 - numberOfValuesNecessary - password.length();
                }
                return numberOfValuesNecessary;
            }
            if (password.length() > 5 && password.length() < 21) {
                int numberOfValuesNecessary = 0;
                if (!containsLowerCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsUpperCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsNumber(password)) {
                    numberOfValuesNecessary++;
                }
                if (number > numberOfValuesNecessary) {
                    numberOfValuesNecessary = number;
                }
                return numberOfValuesNecessary;
            }
            if (password.length() > 20) {
                int numberOfValuesNecessary = 0;
                if (!containsLowerCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsUpperCase(password)) {
                    numberOfValuesNecessary++;
                }
                if (!containsNumber(password)) {
                    numberOfValuesNecessary++;
                }
                if (number > numberOfValuesNecessary) {
                    numberOfValuesNecessary = number;
                }

                numberOfValuesNecessary += numberOfValuesNecessary + password.length() - 20;

                return numberOfValuesNecessary;
            }
        }

        System.out.println();
        System.out.println();

        return 0;
    }

    private static boolean containsLowerCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isLowerCase(i));
    }

    private static boolean containsUpperCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isUpperCase(i));
    }

    private static boolean containsNumber(String value) {
        return contains(value, Character::isDigit);
    }

    private static boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }


    //The function returns the number of moves that need to made in order to not have three repeating characters in a row.
    //If the length of the password is greater that 20, we delete before the characters from the subsequence with 3 elements in a row.
    private static int numberOfElementsToAddToEliminateTheSubsequencesOfIdenticalCharacters(String password) {
        int numberOfElementsToAdd = 0;
        for (int index = 0; index < password.length() - 1; index++) {
            int copyOfIndex = index;
            int numberOfIdenticalElements = 0;
            while (copyOfIndex < password.length() - 1 && password.charAt(copyOfIndex) == password.charAt(copyOfIndex + 1)) {
                numberOfIdenticalElements++;
                copyOfIndex++;
            }
            numberOfIdenticalElements++;
            index = copyOfIndex;

            if (numberOfIdenticalElements > 2) {
                while (numberOfIdenticalElements - 2 > 0) {
                    numberOfElementsToAdd++;
                    numberOfIdenticalElements -= 2;
                }
            }
        }
        return numberOfElementsToAdd;
    }

    public static int removeCharacters(String password) {
        StringBuilder aux = new StringBuilder();
        int cnt = 0;
        for (int i = 0; i < password.length(); i++) {
            if (aux.length() == 20) {
                cnt += password.length() - i;
                break;
            }
            aux.append(password.charAt(i));
            if (aux.length() < 3) {
                continue;
            }
            int ln = aux.length();
            if (aux.charAt(ln - 1) == aux.charAt(ln - 2) && aux.charAt(ln - 2) == aux.charAt(ln - 3)) {
                cnt++;
                aux.deleteCharAt(aux.length() - 1);
            }
        }
        return cnt + checkPassword(aux.toString());
    }

    public static void main(String[] args) {

        String password1 = "abDF1";
        System.out.println(password1);
        System.out.println(checkPassword(password1));

        String password2 = "aaabbccddeeFfgg";
        System.out.println(password2);
        System.out.println(checkPassword(password2));

        String password3 = "aabbccddeeffgghhaagg";
        System.out.println(password3);
        System.out.println(checkPassword(password3));

        String password4 = "aabbccddeeffgghhaaoopp";
        System.out.println(password4);
        System.out.println(checkPassword(password4));

        String password5 = "aabbccddeeffgghH1aaoopp";
        System.out.println(password5);
        System.out.println(checkPassword(password5));

        String password6 = "aabbccddeeffgghH1aao";
        System.out.println(password6);
        System.out.println(checkPassword(password6));

        String password7 = "aAbbbcefghijklmnopqaab";
        System.out.println(password7);
        System.out.println(checkPassword(password7));

        String password8 = "Aaaaa";
        System.out.println(password8);
        System.out.println(checkPassword(password8));
    }
}
