package org.rdutta.authservice.util.filter_utils;

import java.security.Key;

public interface SecretKeyGenerator {
    Key generateKey(String secret);
}

