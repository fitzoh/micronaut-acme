/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.acme.letsencrypt;

import org.shredzone.acme4j.Account;
import org.shredzone.acme4j.AccountBuilder;
import org.shredzone.acme4j.Login;
import org.shredzone.acme4j.Session;
import org.shredzone.acme4j.exception.AcmeException;
import org.shredzone.acme4j.util.KeyPairUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyPair;

/**
 * Utility class to help deactivate a Let's Encrypt account.
 */
public class AccountDeactivator {
    /**
     * Does the account deactivating.
     * @param args
     *        [0] - keypair location on disk
     *        [1] - whether or not to use the staging server
     * @throws AcmeException Failed to deactivate the account
     * @throws IOException Failed to access keypair on disk
     */
    public static void main(String[] args) throws AcmeException, IOException {

        String keyLocationOnDisk = args[0];
        Boolean useStagingServer = Boolean.valueOf(args[1]);

        String serverUrl = useStagingServer ? "https://acme-staging-v02.api.letsencrypt.org/directory" : "https://acme-v02.api.letsencrypt.org/directory";

        File accountKeypairFile = new File(keyLocationOnDisk);
        if (accountKeypairFile.exists()) {
            System.out.println(">>> Account keys exists, using it.");
            KeyPair accountKey = KeyPairUtils.readKeyPair(new FileReader(accountKeypairFile));

            System.out.println(">>> Opening session with " + serverUrl);
            Session session = new Session(serverUrl);


            System.out.println(">>> Logging in to account...");
            Login login = new AccountBuilder().onlyExisting().useKeyPair(accountKey).createLogin(session);

            Account account = login.getAccount();
            account.deactivate();

            System.out.println(">>> Account deactivation complete. ");
            System.exit(0);
        } else {
            System.err.println(">>>> ACCOUNT KEY IS REQUIRED AND WAS NOT FOUND AT : " + keyLocationOnDisk);
            System.exit(1);
        }
    }
}
