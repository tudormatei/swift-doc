package ro.tudormatei.backend.model;

public class UserDataHashMap {
    private UserData[] hashmap;

    public UserDataHashMap() {
        hashmap = new UserData[10];
    }

    public UserDataHashMap(int maxUsers) {
        hashmap = new UserData[maxUsers];
    }

    public void put(String email, UserData data) {
        int hashedKey = hashKey(email);
        if(occupied(hashedKey)) {
            int stopIndex = hashedKey;
            if(hashedKey == hashmap.length - 1) {
                hashedKey = 0;
            }
            else {
                hashedKey++;
            }

            while(occupied(hashedKey) && hashedKey != stopIndex) {
                hashedKey = (hashedKey + 1) % hashmap.length;
            }
        }

        if (occupied(hashedKey)) {
            System.out.println("Email already in use: " + email);
        } else {
            hashmap[hashedKey] = data;
        }
    }

    public UserData get(String email) {
        int hashedKey = findKey(email);
        if(hashedKey == -1) {
            return null;
        }

        return hashmap[hashedKey];
    }

    public UserData remove(String email) {
        int hashedKey = findKey(email);
        if(hashedKey == -1) {
            return null;
        }

        UserData userData = hashmap[hashedKey];
        hashmap[hashedKey] = null;

        UserData[] oldHashMap = hashmap;
        hashmap = new UserData[oldHashMap.length];
        for(int i = 0;i < oldHashMap.length;i++) {
            if(oldHashMap[i] != null) {
                put(oldHashMap[i].getEmail(), oldHashMap[i]);
            }
        }

        return userData;
    }

    private int findKey(String key) {
        int hashedKey = hashKey(key);
        if(hashmap[hashedKey] != null && hashmap[hashedKey].getEmail().equals(key)) {
            return hashedKey;
        }

        int stopIndex = hashedKey;
        if(hashedKey == hashmap.length - 1) {
            hashedKey = 0;
        }
        else {
            hashedKey++;
        }

        while(hashedKey != stopIndex && hashmap[hashedKey] != null && !hashmap[hashedKey].getEmail().equals(key)) {
            hashedKey = (hashedKey + 1) % hashmap.length;
        }

        if(hashmap[hashedKey] != null && hashmap[hashedKey].getEmail().equals(key)) {
            return hashedKey;
        }
        else {
            return -1;
        }
    }

    private boolean occupied(int index) {
        return hashmap[index] != null;
    }

    private int hashKey(String key) {
        return key.length() % hashmap.length;
    }
}
