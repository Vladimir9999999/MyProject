package utils;

import Models.AccountEntity;

public class GenerateToken {

    public static String generateToken(AccountEntity account){

        Hasher hasher = new Hasher();
        String token = hasher.generateHash(account.getId());

        return token;

    }
}
